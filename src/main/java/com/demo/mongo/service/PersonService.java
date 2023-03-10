package com.demo.mongo.service;

import com.demo.mongo.document.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bson.Document;
import java.util.List;

public interface PersonService {
    String save(Person person);

    List<Person> getPersonStartingWith(String name);

    void delete(String id);

    List<Person> getByPersonAge(Integer minAge, Integer maxAge);

    Page<Person> searchPerson(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    List<Document> getOldestPersonByCity();

    List<Document> getPopulationByCity();
}
