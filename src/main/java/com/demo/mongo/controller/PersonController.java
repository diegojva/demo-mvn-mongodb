package com.demo.mongo.controller;


import com.demo.mongo.document.Person;
import com.demo.mongo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @GetMapping
    public List<Person> getPersonStartingWith(@RequestParam("name") String name) {
        return personService.getPersonStartingWith(name);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        personService.delete(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(@RequestParam("minAge") Integer minAge, @RequestParam("maxAge") Integer maxAge){
        return personService.getByPersonAge(minAge, maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        return personService.searchPerson(name, minAge, maxAge, city, pageable);
    }

    @GetMapping("/oldest")
    public List<Document> getOldestPerson(){
        return personService.getOldestPersonByCity();
    }

    @GetMapping("/population")
    public List<Document> getPopulationByCity(){
        return personService.getPopulationByCity();
    }

}
