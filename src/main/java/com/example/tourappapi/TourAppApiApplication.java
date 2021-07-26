package com.example.tourappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TourAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourAppApiApplication.class, args);
    }

}
