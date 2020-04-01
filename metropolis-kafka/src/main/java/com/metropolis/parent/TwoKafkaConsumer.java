package com.metropolis.parent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Pop
 * @date 2020/3/28 20:35
 */
@Component
public class TwoKafkaConsumer {

    @KafkaListener(topics = "test")
    public void listener(ConsumerRecord record){
        Optional msg=Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());}
    }
}
