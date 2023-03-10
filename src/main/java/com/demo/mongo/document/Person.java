package com.demo.mongo.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "persons")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @Id
    private String id;
    private String name;
    private String email;
    private Integer edad;
    private List<String> hobbies;
    private List<Address> addresses;


}
