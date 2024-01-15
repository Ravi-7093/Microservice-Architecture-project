package com.myinventory.orderservice.service;


import com.myinventory.orderservice.dto.InventoryResponse;
import com.myinventory.orderservice.dto.OrderLineItemsDto;
import com.myinventory.orderservice.dto.OrderRequest;
import com.myinventory.orderservice.event.OrderPlacedEvent;
import com.myinventory.orderservice.model.Order;
import com.myinventory.orderservice.model.OrderLineItems;
import com.myinventory.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        System.out.println(orderLineItems.toArray() + "--> this is orderline items"); // we are creating streams to map objects
        order.setOrderLineItemsList(orderLineItems);

        // Call the Inventory Service and place the order if product is on Stock


        List<String> skuCodes= order.getOrderLineItemsList().stream().map(orderLineItem-> orderLineItem.getSkuCode()).toList();

        InventoryResponse[] inventoryResponseArray= webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); // --> here we added block to keep the request synchronous

        boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        }
        else{
            throw new IllegalArgumentException("Product is not in Stock , please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
