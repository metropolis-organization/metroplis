package com.metropolis.common.redis.serialize;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;
import java.util.Objects;

/**
 * @author Pop
 * @date 2020/2/17 22:51
 */
public class ObjectRedisSerializer implements RedisSerializer<Object> {

    private static final int BYTES_ARRAY_SIZE = 128;

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        byte[] result = new byte[0];
        if(Objects.isNull(o)){ return result;}
        if(!(o instanceof Serializable)){
            throw new SerializationException(" Redis 序列化失败，["+o.getClass().getName()+"] 未实现 Serializable 接口");
        }
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BYTES_ARRAY_SIZE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);){
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            result = byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            throw new SerializationException("  Redis 序列化失败，Object->byes[]", e);
        }
        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {

        Object result = null;
        if (bytes == null || bytes.length == 0) {
            return result;
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ){
                result = objectInputStream.readObject();
        }catch (IOException e){
            throw new SerializationException(" 序列化失败", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException(" 序列化失败", e);
        }
        return result;
    }

    /**
     *
     * 需要序列化的对象，transient 修饰后，需要更加简练的传输，或个人定制
     */
    private void writeObject(ObjectOutputStream outputStream){
        //  s.defaultWriteObject();
        // 默认写行为后，追加写
    }
    private void readObject(ObjectInputStream inputStream){
        // s.defaultReadObject();
        // 默认写读为后，追加读
    }
    //二者需要保持顺序一致
}
