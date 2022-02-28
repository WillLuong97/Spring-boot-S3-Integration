package com.example.awsimageupload.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.awsimageupload.service.FileStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FileStoreImpl implements FileStore {

    private final AmazonS3 amazonS3;

    /**
     * upload() => this method will take an image file and
     * upload it to the S3 bucket that is connected to our application
     *
     * @param path             (String) is the path on the Amazon S3 bucket where the file will be stored.
     * @param fileName         (String) is the actual name of the file being uploaded. It will be used as the key when downloading the file from S3.
     * @param optionalMetaData (Optional<Map<String, String>>) map contains the details of the file i.e file type and file size.
     * @param inputStream      (InputStream) contains the actual file that should be saved to Amazon S3.
     **/
    @Override
    public void upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //if the file metadata exist then add it into the hashmap, adding all of the file information to the S3 objectMetaData.
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }

        });
        //connecting to amazon s3 and upload the image file using put method
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

    /**
     * download() -> download a file from the s3 bucket and serve it to the client based on the given input parameters
     *
     * @param path (String) is the path on the Amazon S3 bucket where the file will be stored.
     * @param key  (String) is the actual name of the file being uploaded
     **/
    @Override
    public byte[] download(String path, String key) {
        try{
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);

        } catch (AmazonServiceException | IOException e){
            throw new IllegalStateException("Failed to download file", e);
        }
    }
}
