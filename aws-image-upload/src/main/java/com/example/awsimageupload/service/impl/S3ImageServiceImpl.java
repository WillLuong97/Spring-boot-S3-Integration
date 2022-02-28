package com.example.awsimageupload.service.impl;

import com.example.awsimageupload.constants.BucketName;
import com.example.awsimageupload.entity.Image;
import com.example.awsimageupload.repository.ImageRepository;
import com.example.awsimageupload.service.FileStore;
import com.example.awsimageupload.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class S3ImageServiceImpl implements ImageService {

    @Value("spring.storage.method")
    private String method;

    private final FileStore fileStore;
    @Autowired
    private final ImageRepository imageRepository;

    @Override
    public Image saveImage(String title, String description, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }

        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //Save Image in s3
        String path = String.format("%s/%s", BucketName.IMAGE_BUCKET.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        Image image = Image
                .builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();

        //save the file into db with the path leads to the S3 bucket
        imageRepository.save(image);
        return imageRepository.findByTitle(image.getTitle());

    }

    @Override
    public byte[] downloadTodoImage(Long id) {
        Image image = imageRepository.findById(id).get();
        return fileStore.download(image.getImagePath(), image.getImageFileName());
    }

    @Override
    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();
        imageRepository.findAll().forEach(images::add);
        return images;
    }
}
