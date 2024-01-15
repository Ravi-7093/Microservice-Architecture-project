package com.myinventory.orderservice.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced//Adds load balancing capabilities to the client side since we had 2 instances of inventory services
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();

    }


}
