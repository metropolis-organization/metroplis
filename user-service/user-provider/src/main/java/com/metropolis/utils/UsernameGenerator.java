package com.metropolis.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Pop
 * @date 2020/5/8 21:53
 *
 * 用户名生成器
 */
public class UsernameGenerator {

    private static final String NAME = "用户";

    private static final Random random = new Random();

    private static final int COUNT = 16;

    private static final char[] ARRAYS = "qwertyuiopasdfghjklzxcvbnm".toCharArray();

    private static int arraysCount = 0;

    static{
        arraysCount = ARRAYS.length;
    }

    /**
     * 生成一个大小写混合的名字
     * @return
     */
    public static String generate(){
        return NAME +appendCharUpperLower();
    }

    /**
     *  默认开启小写或者大写
     * @param upper 开启大写
     * @param lower 开启小写
     * @return
     */
    public static String generate(boolean upper,boolean lower){
        if(upper){return NAME+appendCharUpper();}
        else if(lower){
            return NAME+appendCharLower();
        }else{
            return generate();
        }
    }

    /**
     * 为后面生成一个长度为8的随机字符
     * @return
     */
    private static String appendCharLower(){
        char[] chars = new char[COUNT];
        for(int i =0;i<COUNT;i++){
            chars[i]=ARRAYS[random.nextInt(arraysCount)];
        }
        return String.valueOf(chars);
    }

    /**
     * 大写
     * @return
     */
    private static String appendCharUpper(){
        char[] chars = new char[COUNT];
        for(int i =0;i<COUNT;i++){
            char c =ARRAYS[random.nextInt(arraysCount)]-=32;
            chars[i]=c;
        }
        return String.valueOf(chars);
    }

    /**
     * 大小写混用
     * @return
     */
    private static String appendCharUpperLower(){
        char[] chars = new char[COUNT];
        for(int i =0;i<COUNT;i++){
            char c = ARRAYS[random.nextInt(arraysCount)];
            int div = random.nextInt(COUNT);
            div = div==0?2:div;
            if(i%div==0){c-=32;}
            chars[i]=c;
        }
        return String.valueOf(chars);
    }

}
