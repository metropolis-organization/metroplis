package com.metropolis.common.redis.serialize;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.metropolis.common.encrypt.AECProcessor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Pop
 * @date 2020/2/18 22:00
 */
public class HessianRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object o) throws SerializationException {

        byte[] bytes = null;
        if(Objects.isNull(o)){return bytes;}
        HessianOutput hessianOutput = null;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(o);
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            throw new SerializationException("  Redis 序列化失败，Object->byes[]", e);
        }finally {
            if(hessianOutput!=null){
                try {
                    hessianOutput.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if (bytes == null || bytes.length == 0) {
            return result;
        }
        HessianInput hessianInput = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        ){
            hessianInput = new HessianInput(byteArrayInputStream);
            result = hessianInput.readObject();
        }catch (IOException e){
            throw new SerializationException(" 序列化失败", e);
        }finally {
            if(hessianInput!=null){
                hessianInput.close();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        User user = new User("pp");
        HessianRedisSerializer  hessianRedisSerializer = new HessianRedisSerializer();
        byte[] bytes = hessianRedisSerializer.serialize(user);
        String o = new String(bytes);
//        Base64.getEncoder().encodeToString(bytes);
        String encodeString = Base64.getEncoder().encodeToString(bytes);
        byte[] dbytes = Base64.getDecoder().decode(encodeString);
//        System.out.println(Base64.getEncoder().encodeToString(bytes));
        User user1= (User) hessianRedisSerializer.deserialize(dbytes);
        System.out.println(user);
    }

}

class User implements Serializable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
