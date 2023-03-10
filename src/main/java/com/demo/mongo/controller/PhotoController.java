package com.demo.mongo.controller;

import com.demo.mongo.document.Photo;
import com.demo.mongo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public String addPhoto(@RequestParam("img")MultipartFile img) throws IOException {
        return photoService.addPhoto(img.getOriginalFilename(), img);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("id") String id) {
        Photo photo = photoService.downloadPhoto(id);

        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + photo.getTitle() + "\"")
                .body(resource);
    }
}
