package com.demo.mongo.service.impl;

import com.demo.mongo.document.Photo;
import com.demo.mongo.repo.PhotoRepo;
import com.demo.mongo.service.PhotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepo photoRepo;

    @Override
    public String addPhoto(String originalFilename, MultipartFile img) throws IOException {
        Photo photo = new Photo();

        photo.setTitle(originalFilename);
        photo.setPhoto(new Binary(BsonBinarySubType.BINARY, img.getBytes()));

        return photoRepo.save(photo).getId();
    }

    @Override
    public Photo downloadPhoto(String id) {
        return photoRepo.findById(id).get();
    }
}
