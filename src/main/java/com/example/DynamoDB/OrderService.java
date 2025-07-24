package com.example.DynamoDB;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Iterator;

public class OrderService {
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Order> orderTable;

    public OrderService() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("test", "test")
                        )
                )
                .build();

        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.orderTable = enhancedClient.table("Orders", TableSchema.fromBean(Order.class));
    }

    public void addOrder(Order order) {

        System.out.println("Customer ID: " + order.getCustomerId());
        System.out.println("Order ID: " + order.getOrderId());

        orderTable.putItem(order);
        System.out.println("Order inserted: " + order.getOrderId());
    }

    public void getOrdersByCustomer(String customerId) {
        QueryConditional query = QueryConditional.keyEqualTo(Key.builder().partitionValue(customerId).build());
        Iterator<Page<Order>> results = orderTable.query(query).iterator();

        System.out.println("Orders for CustomerID: " + customerId);
        while (results.hasNext()) {
            for (Order order : results.next().items()) {
                System.out.println("- OrderID: " + order.getOrderId() +
                        ", Date: " + order.getOrderDate() +
                        ", Amount: $" + order.getTotalAmount());
            }
        }
    }

    // 🔰 Main method for testing
    public static void main(String[] args) {
        OrderService service = new OrderService();

        Order order1 = new Order();
        order1.setCustomerId("CUST001");
        order1.setOrderId("ORD001");
        order1.setOrderDate("2025-07-24");
        order1.setTotalAmount(120.50);

        Order order2 = new Order();
        order2.setCustomerId("CUST001");
        order2.setOrderId("ORD002");
        order2.setOrderDate("2025-07-25");
        order2.setTotalAmount(300.75);

        service.addOrder(order1);
        service.addOrder(order2);

        service.getOrdersByCustomer("CUST100");
    }
}