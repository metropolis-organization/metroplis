package com.metropolis.authorization.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.metropolis.authorization")
@MapperScan(basePackages = "com.metropolis.authorization.dal.persistence")
public class AuthorizationApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthorizationApp.class);
    }
}
