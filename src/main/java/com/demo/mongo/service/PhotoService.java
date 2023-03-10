package com.demo.mongo.service;

import com.demo.mongo.document.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String addPhoto(String originalFilename, MultipartFile img) throws IOException;

    Photo downloadPhoto(String id);
}
