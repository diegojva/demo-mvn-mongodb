package com.demo.mongo.repo;

import com.demo.mongo.document.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepo extends MongoRepository<Person, String> {

    List<Person> findByNameStartingWith(String name);

    //List<Person> findByEdadBetween(Integer minAge, Integer maxAge);

    @Query(value = "{ 'edad' : { $gt : ?0, $lt : ?1}}",
            fields = "{addresses:  0}")
    List<Person> findByEdadBetween(Integer min, Integer max);

    Person findById(int id);

   // Page<Person> searchPerson(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);
}
