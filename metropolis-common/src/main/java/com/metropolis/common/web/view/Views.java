package com.metropolis.common.web.view;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-13 09:54
 **/
public class Views {

    public static ModelAndView get(String viewName){
        return get(viewName,null);
    }

    public static ModelAndView get(String viewName, Map params){
        return new ModelAndView(viewName,params);
    }

    public static Map<String,Object> parmas(String key,Object value){
        Map<String,Object> map = new HashMap<>();
        map.putIfAbsent(key,value);
        return map;
    }
}
