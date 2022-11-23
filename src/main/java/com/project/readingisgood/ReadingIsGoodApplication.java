package com.project.readingisgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReadingIsGoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingIsGoodApplication.class, args);
    }

}
