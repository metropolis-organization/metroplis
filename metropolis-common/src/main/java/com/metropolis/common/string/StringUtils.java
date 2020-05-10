package com.metropolis.common.string;

/**
 * @author Pop
 * @date 2020/2/16 16:27
 * 字符串工具类
 */
public final class StringUtils {

    public static boolean isEmpty(String target){
        return target==null?true:"".equals(target)?true:false;
    }

    public static boolean nonEmpty(String target){return !isEmpty(target);}

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
    public static boolean equalsIgnore(String target,String src){return target.equalsIgnoreCase(src);}
    public static boolean nonEquals(String target,String src){return !equals(target,src);}
    public static boolean nonEqualsIgnore(String target,String src){return !equalsIgnore(target, src);}

}
