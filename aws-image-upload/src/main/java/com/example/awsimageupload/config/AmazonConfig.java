package com.example.awsimageupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    //Connecting to an S3 clients:
    public AmazonS3 s3(){
        //getting the necessary credentials to connect to an S3 buckets
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "ACCESS_KEY",
                "SECRET_KEY"
        );
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

}
