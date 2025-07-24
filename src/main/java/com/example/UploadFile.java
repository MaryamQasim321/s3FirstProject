package com.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.URI;

public class UploadFile {


    public static void uploadFileToBucket(String bucketName, String path){

        S3Client client1 = S3Client.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")))
                .forcePathStyle(true)
                .build();
        File file=new File(path);
        String key=file.getName();
        PutObjectRequest putRequest=PutObjectRequest.builder().bucket(bucketName).key(key).build();
        client1.putObject(putRequest, RequestBody.fromFile(file));
        System.out.println("File uploaded to bucket: "+ bucketName);
        client1.close();



    }

    public static void main(String[] args) {
        String bucketName="hotelkey-bucket-1";

        String path="C:\\Users\\laptop collection\\Desktop\\test.txt.txt";
        uploadFileToBucket(bucketName,path );
    }
}
