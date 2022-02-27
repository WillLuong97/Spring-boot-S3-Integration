package com.example.awsimageupload.bucket;

//define an enum that can be used among all the application to access the S3 buckets
public enum BucketName {

    PROFILE_NAME("toodle-client-file-upload");

    private final String bucketName;
    //the constructor will take in a value of a bucket name and use the profile name to connect to the right bucket instance
    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
