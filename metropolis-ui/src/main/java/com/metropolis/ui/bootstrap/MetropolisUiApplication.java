package com.metropolis.ui.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

@ComponentScan(basePackages = "com.metropolis.ui")
@SpringBootApplication
public class MetropolisUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetropolisUiApplication.class, args);
    }



}
