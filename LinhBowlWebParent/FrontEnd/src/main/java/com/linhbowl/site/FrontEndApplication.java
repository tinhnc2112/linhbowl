package com.linhbowl.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.linhbowl.entity"})
public class FrontEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontEndApplication.class, args);
    }

}
