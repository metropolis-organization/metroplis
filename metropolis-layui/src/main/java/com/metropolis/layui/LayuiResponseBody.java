package com.metropolis.layui;

import com.metropolis.common.entity.ViewData;
import com.metropolis.layui.annotation.LayuiTable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pop
 * @date 2020/4/22 21:28
 * 解析 layui 需要返回的对象的处理
 */
@ControllerAdvice
public class LayuiResponseBody implements  ResponseBodyAdvice<Object> {

    private final static Map<Class,LayuiConverter> layuiAnnotations = new HashMap<>();

    static {

        layuiAnnotations.put(LayuiTable.class,new LayuiConverterTable());

    }

    private final ThreadLocal<Class<?>> threadLocal = new ThreadLocal<>();

    /**
     *  是否支持这个拦截
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Annotation[] annotations =methodParameter.getMethodAnnotations();
        for(Annotation annotation:annotations){
            Class<?> annotationType = annotation.annotationType();
            if(layuiAnnotations.containsKey(annotationType)){
                threadLocal.set(annotationType);
                return true;
            }
        }
        return false;
    }

    /**
     * 具体的拦截操作
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(!(o instanceof  ViewData)){return o;}
        ViewData data = (ViewData) o;
        LayuiConverter converter=layuiAnnotations.get(threadLocal.get());
        return converter.convert(data);
    }
}
