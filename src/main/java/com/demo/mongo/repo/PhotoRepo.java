package com.demo.mongo.repo;

import com.demo.mongo.document.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepo extends MongoRepository<Photo, String> {
}
