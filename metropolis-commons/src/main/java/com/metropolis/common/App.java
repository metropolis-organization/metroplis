package com.metropolis.common;

import com.metropolis.common.redis.serialize.HessianRedisSerializer;

import java.io.Serializable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        User user = new User("pop",26);
        HessianRedisSerializer hessianRedisSerializer = new HessianRedisSerializer();
        byte[] bytes=hessianRedisSerializer.serialize(user);

        User user1 = (User) hessianRedisSerializer.deserialize(bytes);
        System.out.println(user1.getName()+" "+user1.getAge());
    }


}
class User implements Serializable {
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String name;
    private int age;
}