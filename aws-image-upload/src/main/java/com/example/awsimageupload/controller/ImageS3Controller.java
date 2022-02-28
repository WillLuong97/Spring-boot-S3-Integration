package com.example.awsimageupload.controller;

import com.example.awsimageupload.entity.Image;
import com.example.awsimageupload.service.ImageService;
import com.example.awsimageupload.service.impl.InternalSystemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/image")
@AllArgsConstructor
@CrossOrigin("*")
public class ImageS3Controller {
    @Autowired
    private ImageService imageService;

    @Autowired
    private final InternalSystemServiceImpl internalSystemService;

    @GetMapping
    public ResponseEntity<List<Image>> getImages(){
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }

    @PostMapping(
            path = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Image> saveImage(@RequestParam("title") String title,
                                           @RequestParam("description") String description,
                                           @RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(imageService.saveImage(title,description, file), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/download")
    public byte[] downloadTodoImage(@PathVariable("id") Long id) {
        return imageService.downloadTodoImage(id);
    }
}
