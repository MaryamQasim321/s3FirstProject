package com.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.net.URI;

public class ListBuckets {

    public static void main(String[] args) {
        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create("http://localhost:4566")) // For LocalStack
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")))
                .build(); // No need to specify HTTP client manually

        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        System.out.println("Buckets in my account are:");
        for (Bucket bucket : listBucketsResponse.buckets()) {
            System.out.println(bucket.name());
        }

        s3Client.close();
    }
}
