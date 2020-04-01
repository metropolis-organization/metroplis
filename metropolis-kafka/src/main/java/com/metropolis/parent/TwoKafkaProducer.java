package com.metropolis.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Pop
 * @date 2020/3/28 20:32
 */
@Component
public class TwoKafkaProducer {

    @Autowired
    private KafkaTemplate<Integer,String> template;

    public void send(){
        template.send("test","1111");
    }

}
