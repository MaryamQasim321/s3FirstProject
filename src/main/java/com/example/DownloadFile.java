package com.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.File;
import java.net.URI;

public class DownloadFile {



    public static void uploadFileToBucket(String bucketName, String key,String dest){


        S3Client s3Client=S3Client.builder().endpointOverride(URI.create("http://localhost:4566")).credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test"))).forcePathStyle(true) .build();
        GetObjectRequest getObjectRequest=GetObjectRequest.builder().bucket(bucketName).key(key).build();
        File destFile=new File(dest);

        s3Client.getObject(getObjectRequest, ResponseTransformer.toFile(destFile));
        System.out.println("Download completed");



    }

    public static void main(String[] args) {
        String bucketName="hotelkey-bucket-1";
        String file="test.txt.txt";
        String path="C:\\Users\\laptop collection\\Desktop\\HotelKey\\test.txt.txt";
        uploadFileToBucket(bucketName,file,path);

    }
}
