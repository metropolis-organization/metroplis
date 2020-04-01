package com.metropolis.parent;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Pop
 * @date 2020/3/28 19:19
 */
public class OneKafkaProducer extends Thread{

    @Override
    public void run() {
        int num = 0;
        String msg = "test-";
        while(num<20){
            try {
                RecordMetadata metadata=producer.send(new ProducerRecord<>(topic,msg)).get();
                producer.send(new ProducerRecord<>(topic,msg),(metadata1,expection)->{
                    //异步写法
                });
                System.out.println(metadata.offset()+" "+metadata.partition()+" "+metadata.topic());
                TimeUnit.SECONDS.sleep(2);
                ++num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    //api
    KafkaProducer<Integer,String> producer;
    private String topic;

    public OneKafkaProducer(String topic) {

        Properties properties = new Properties();
        //集群用逗号隔开
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.150.102:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"test-producer");
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,"");//批量发送
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());//序列化方式

        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    public static void main(String[] args) {
        new OneKafkaProducer("test").start();
    }
}
