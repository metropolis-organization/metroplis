package com.metropolis.common.utils;

import java.util.Arrays;

/**
 * @author Pop
 * @date 2020/5/10 10:27
 *
 * 对于数组的一些转换
 */
public class ArrayUtils {

    public static long[] string2LongArray(String[] stringArray){
        return Arrays.stream(stringArray).mapToLong((s)->Long.parseLong(s)).toArray();
    }

}
