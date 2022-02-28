package com.example.awsimageupload.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    //these are the name of bucket we created from AWS
    IMAGE_BUCKET("spring-amazon-storage");
    private final String bucketName;
}
