package com.example.awsimageupload.service;

import com.example.awsimageupload.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image saveImage(String title, String description, MultipartFile file);

    byte[] downloadTodoImage(Long id);

    List<Image> getAllImages();
}
