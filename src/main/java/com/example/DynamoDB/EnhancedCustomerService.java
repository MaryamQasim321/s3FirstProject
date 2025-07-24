package com.example.DynamoDB;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class EnhancedCustomerService {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Customer> customerTable;

    public EnhancedCustomerService() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:4566")) // LocalStack
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

        this.customerTable = enhancedClient.table("Customers", TableSchema.fromBean(Customer.class));
    }

    public void createCustomer(Customer customer) {
        customerTable.putItem(customer);
        System.out.println("Customer created.");
    }

    public Customer getCustomer(String customerId) {
        return customerTable.getItem(r -> r.key(k -> k.partitionValue(customerId)));
    }

    // Main method for testing
    public static void main(String[] args) {
        EnhancedCustomerService service = new EnhancedCustomerService();

        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setCustomerId("1");
        newCustomer.setFirstName("Ali");
        newCustomer.setLastName("Khan");
        newCustomer.setEmail("ali@example.com");

        service.createCustomer(newCustomer);

        // Retrieve the same customer
        Customer retrieved = service.getCustomer("1");
        if (retrieved != null) {
            System.out.println("Retrieved Customer:");
            System.out.println("ID: " + retrieved.getCustomerId());
            System.out.println("Name: " + retrieved.getFirstName() + " " + retrieved.getLastName());
            System.out.println("Email: " + retrieved.getEmail());
        } else {
            System.out.println("Customer not found.");
        }
    }
}
