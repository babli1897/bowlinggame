package com.target;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.target"})
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class,args);
    }
}
