package com.example.DynamoDB;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.net.URI;

public class ShowTables {

    public static void main(String[] args) {
        DynamoDbClient client = DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:4566")).region(Region.US_EAST_1).build();

        ListTablesRequest request = ListTablesRequest.builder().build();
        ListTablesResponse response = client.listTables(request);
        System.out.println("Tables in db are: ");
        System.out.println(response.tableNames());
        client.close();
    }


}
