package com.xmomen.generator.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class App {
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @Bean
    public MongoPageHelper mongoPageHelper() {
        return new MongoPageHelper(mongoTemplate);
    }
}
