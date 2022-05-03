package com.devh.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevhCommonApplication {

    public static void main(String[] args) {
    	/* Class loader issue */
    	System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DevhCommonApplication.class, args);
    }

}
