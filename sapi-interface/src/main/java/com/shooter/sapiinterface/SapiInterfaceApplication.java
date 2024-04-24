package com.shooter.sapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SapiInterfaceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SapiInterfaceApplication.class, args);
//        System.out.println(client);
    }

}
