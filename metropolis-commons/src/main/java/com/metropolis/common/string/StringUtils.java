package com.metropolis.common.string;

import java.util.regex.Pattern;

/**
 * @author Pop
 * @date 2020/2/16 16:27
 * 字符串工具类
 */
public final class StringUtils {

    public static String buildString(char[] chars){return new String(chars);}

    /**
     * 切分
     * @param target
     * @param regx
     * @return
     */
    public static String[] split(String target, String regx){
        return target.split(regx);
    }

    /**
     *  target.equals(src)
     *  StringUtils.equals(target,src)
     * @param target 需要比较相同的类
     * @param src
     * @return
     */
    public static boolean equals(String target,String src){return target.equals(src);}

    public static boolean nonEquals(String target,String src){return !target.equals(src);}
}
