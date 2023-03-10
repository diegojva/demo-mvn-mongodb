package com.demo.mongo.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "photo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo {

    private String id;
    private String title;
    private Binary photo;
}
