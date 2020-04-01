package com.metropolis.parent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author Pop
 * @date 2020/3/28 20:37
 */
@SpringBootApplication
public class ParticeKafkaApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context= SpringApplication.run(ParticeKafkaApplication.class,args);
        TwoKafkaProducer producer=context.getBean(TwoKafkaProducer.class);
        for (int i = 0; i <10 ; i++) {
            producer.send();
            TimeUnit.SECONDS.sleep(2);
        }

    }
}
