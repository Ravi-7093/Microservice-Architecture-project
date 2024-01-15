package com.myinventory.orderservice.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    public class SQLTest {

        public static void main(String [] args) throws Exception {
            // Class.forName( "com.mysql.jdbc.Driver" ); // do this in init
            // // edit the jdbc url
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/order-service?user=root&password=MeadowRun@190");
            // Statement st = conn.createStatement();
            // ResultSet rs = st.executeQuery( "select * from table" );

            System.out.println("Connected?");
        }
    }

