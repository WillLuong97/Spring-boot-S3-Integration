package com.example.awsimageupload.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStore {
    /**
     * upload() => this method will take an image file and
     * upload it to the S3 bucket that is connected to our application
     *
     * @param path (String) is the path on the Amazon S3 bucket where the file will be stored.
     * @param fileName (String) is the actual name of the file being uploaded. It will be used as the key when downloading the file from S3.
     * @param optionalMetaData (Optional<Map<String, String>>) map contains the details of the file i.e file type and file size.
     * @param inputStream (InputStream) contains the actual file that should be saved to Amazon S3.
     **/
    public void upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream);

    /**
     * download() -> download a file from the s3 bucket and serve it to the client based on the given input parameters
     *
     * @param path (String) is the path on the Amazon S3 bucket where the file will be stored.
     * @param key (String) is the actual name of the file being uploaded
     * **/
    public byte[] download(String path, String key);

}
