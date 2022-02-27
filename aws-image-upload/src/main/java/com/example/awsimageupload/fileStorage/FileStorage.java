package com.example.awsimageupload.fileStorage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service //Service is used to marked the class as a service provider
public class FileStorage {
    private final AmazonS3 s3;

    //add a constructor:
    @Autowired //automatic dependecy injection, in this case, it would automatically injecct AWS dpenency to this package.
    public FileStorage(AmazonS3 s3){
        this.s3 = s3;
    }

    /* path: the name of the bucket and the path that to any folder contains inside an S3 buckets
    * file: the name of the file to be stored
    * optionalMetaData: a map that store the content-type and type of data to be stored.
    * inputStream: the actual data to be stored */
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream){
        //create an object metadata
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetaData.ifPresent(map -> {
            //if the metadata is not empty, so we will add each object into the map
            if(!map.isEmpty()){
                map.forEach(metadata::addUserMetadata);
            }
        });

        try{
            s3.putObject(path, fileName, inputStream, metadata);

        } catch(AmazonServiceException e){
            //error handling:
            throw new IllegalStateException("Failed to store file to S3", e);
        }
    }

}
