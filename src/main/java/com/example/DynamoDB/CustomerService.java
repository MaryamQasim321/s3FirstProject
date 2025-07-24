package com.example.DynamoDB;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.awt.event.ActionListener;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {


    private  final DynamoDbClient dynamoDB;

    public CustomerService(){
        this.dynamoDB=DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:4566")).region(Region.US_EAST_1).build();

    }
    public void createCustomer(String id, String email, String firstName,String lastName){
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("CustomerID", AttributeValue.builder().s(id).build());
        item.put("Email", AttributeValue.builder().s(email).build());
        item.put("FirstName", AttributeValue.builder().s(firstName).build());
        item.put("LastName", AttributeValue.builder().s(lastName).build());
        PutItemRequest itemRequest = PutItemRequest.builder().tableName("Customers").item(item).build();
        dynamoDB.putItem(itemRequest);
        System.out.println("Customer created successfully" );


    }
    public void getCustomer(String id){

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("CustomerID", AttributeValue.builder().s(id).build());
        GetItemRequest itemRequest = GetItemRequest.builder().tableName("Customers").key(key).build();
        GetItemResponse response = dynamoDB.getItem(itemRequest);
        if(response.hasItem()){
            System.out.println("Customer: ");
            System.out.println(response.item());
        }
        else{
            System.out.println("No such customer found");
        }
    }
    public  void updateCustomer(String id, String email){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("CustomerID", AttributeValue.builder().s(id).build());
        Map<String, AttributeValue> values = new HashMap<>();
        values.put(":email", AttributeValue.builder().s(email).build());

        UpdateItemRequest request=UpdateItemRequest.builder().tableName("Customers").key(key).updateExpression("SET Email=:email").expressionAttributeValues(values).build();
        dynamoDB.updateItem(request);
    }

}
