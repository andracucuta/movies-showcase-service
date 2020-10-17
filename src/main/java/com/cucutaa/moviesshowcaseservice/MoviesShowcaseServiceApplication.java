package com.cucutaa.moviesshowcaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoviesShowcaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesShowcaseServiceApplication.class, args);
    }
}
