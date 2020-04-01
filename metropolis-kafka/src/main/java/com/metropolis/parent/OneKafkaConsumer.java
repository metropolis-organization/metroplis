package com.metropolis.parent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author Pop
 * @date 2020/3/28 19:19
 */
public class OneKafkaConsumer extends Thread{

    KafkaConsumer<Integer,String> consumer;
    private String topic;

    public OneKafkaConsumer(String topic) {

        Properties properties = new Properties();
        //集群用逗号隔开
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.150.102:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"test-consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test-sid");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");//超时时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");//自动提交间隔时间
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());//序列化方式
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");//例如新创建组，可以重新消费历史消息（如果没被淘汰掉）
        this.consumer = new KafkaConsumer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(this.topic));
        while(true){

            ConsumerRecords<Integer,String> consumerRecord = consumer.poll(Duration.ofSeconds(5));
            consumerRecord.forEach(record->{
                System.out.println( record.key() +" ->"+record.value()+" ->"+record.offset());
            });
        }
    }


    public static void main(String[] args) {
        new OneKafkaConsumer("test").start();
    }

}


