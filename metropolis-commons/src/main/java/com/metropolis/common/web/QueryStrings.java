package com.metropolis.common.web;

import com.metropolis.common.string.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pop
 * @date 2020/2/28 14:26
 *
 * 处理Url的queryString
 */
public class QueryStrings {

    private static final Pattern PATTERN = Pattern.compile("\\??[a-zA-Z0-9/:=_]+&?");
    private static final String SINGLE = "?";
    private static final String EQUALS = "=";
    private static final String AND = "&";

    public static String getParameter(String url,String key){
        Map map = parseParameterMap(url);
        return Objects.isNull(map)?null: (String) map.getOrDefault(key,"");
    }

    public static String getSimpleParameter(HttpServletRequest request,String key){
        return getSimpleParameter(request.getQueryString(),key);
    }

    public static String getSimpleParameter(String url,String key){
        Map map = getSimpleParameterMap(url);
        return Objects.isNull(map)?null: (String) map.getOrDefault(key,"");
    }

    public static Map<String,String> getParameterMap(String url){
        return parseParameterMap(url);
    }

    public static String getParameter(HttpServletRequest request,String key){
        return getParameter(request.getQueryString(), key);
    }

    public static Map<String,String> getParameterMap(HttpServletRequest request){
        return parseParameterMap(request.getQueryString());
    }

    public static Map<String,String> getSimpleParameterMap(String queryString) {
        if(StringUtils.isEmpty(queryString))return null;
        String[] andArr = queryString.split(AND);
        Map map = new HashMap();
        for (String and : andArr){
            String value=and.substring(and.indexOf(EQUALS)+1,and.length());
            and = and.substring(0,and.indexOf(EQUALS));
            map.put(and,value);
        }
        return map;
    }
    public static Map<String,String> getSimpleParameterMap(HttpServletRequest request){
        return getSimpleParameterMap(request.getQueryString());
    }


    private static Map parseParameterMap(String url){
        Map map = new HashMap();
        if(StringUtils.isEmpty(url)) return null;
        Matcher matcher=PATTERN.matcher(url);
        while (matcher.find()){
            String mat = matcher.group();
            int singleIndex = mat.indexOf(SINGLE);
            if(singleIndex==mat.indexOf(EQUALS)){ continue; }
            else{
                int andIndex = mat.indexOf(AND);
                mat = mat.substring(singleIndex==-1?0:singleIndex+1,andIndex==-1?mat.length():andIndex);
                String[] arr = mat.split(EQUALS);
                map.put(arr[0],arr[1]);
            }
        }
        return map;
    }
}
