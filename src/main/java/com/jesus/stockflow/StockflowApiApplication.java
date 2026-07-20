package com.jesus.stockflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockflowApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockflowApiApplication.class, args);
    }

}
