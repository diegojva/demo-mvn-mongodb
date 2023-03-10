package com.demo.mongo;

import com.demo.mongo.document.Person;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.demo.mongo.repo")
@MappedTypes(Person.class)
public class DemoMvnMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMvnMongodbApplication.class, args);
    }

}
