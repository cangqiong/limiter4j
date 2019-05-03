package org.chason.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Limiter4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Limiter4jApplication.class, args);
    }
}
