package com.metropolis.common.utils;

import java.util.Collection;

/**
 * @author Pop
 * @date 2020/5/10 11:50
 */
public class CollectionsUtils {


    public static boolean isEmpty(Collection<?> collection){
        return collection==null;
    }

    public static boolean nonEmpty(Collection<?> collection){
        return  collection!=null;
    }

}
