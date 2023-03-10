package com.demo.mongo.service.impl;

import com.demo.mongo.document.Person;
import com.demo.mongo.repo.PersonRepo;
import com.demo.mongo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepo.save(person).getId();
    }

    @Override
    public List<Person> getPersonStartingWith(String name) {
        return personRepo.findByNameStartingWith(name);
    }

    @Override
    public void delete(String id) {
        personRepo.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer minAge, Integer maxAge) {
        return personRepo.findByEdadBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> searchPerson(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if (minAge != null && maxAge != null) {
            criteria.add(Criteria.where("edad").gte(minAge).lte(maxAge));
        }
        if (city != null && !city.isEmpty()) {
            criteria.add(Criteria.where("address.city").is(city));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class),
                pageable,
                () -> mongoTemplate.count(query, Person.class));

        return people;
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        UnwindOperation unwind = Aggregation.unwind("addresses");

        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "edad");
        GroupOperation group = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwind, sort, group);

        List<Document> person
                = mongoTemplate.aggregate(aggregation, Person.class,Document.class).getMappedResults();

        return person;
    }

    @Override
    public List<Document> getPopulationByCity() {
        UnwindOperation unwind = Aggregation.unwind("addresses");

        GroupOperation group = Aggregation.group("addresses.city").count().as("popCount");

        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "popCount");

        ProjectionOperation projection = Aggregation.project().andExpression("_id")
                .as("city").andExpression("popCount").as("population").andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwind, group, sort, projection );

        List<Document> person
                = mongoTemplate.aggregate(aggregation, Person.class,Document.class).getMappedResults();

        return person;
    }
}
