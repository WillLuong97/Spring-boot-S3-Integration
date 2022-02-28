package com.example.awsimageupload.repository;

import com.example.awsimageupload.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findByTitle(String title);
}
