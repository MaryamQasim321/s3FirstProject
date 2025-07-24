package com.example.DynamoDB;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("CustomerID")
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String id) { this.customerId = id; }

    @DynamoDbAttribute("FirstName")
    public String getFirstName() { return firstName; }
    public void setFirstName(String fn) { this.firstName = fn; }

    @DynamoDbAttribute("LastName")
    public String getLastName() { return lastName; }
    public void setLastName(String ln) { this.lastName = ln; }

    @DynamoDbAttribute("Email")
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    // default constructor required
    public Customer() {}
}
