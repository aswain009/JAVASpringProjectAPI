package com.example.lugnutsautomotive;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Lugnuts Automotive API",
                version = "v1",
                description = "REST API for customers, products, and orders backed by MySQL"
        )
)
@SpringBootApplication
public class LugnutsAutomotiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(LugnutsAutomotiveApplication.class, args);
    }

}
