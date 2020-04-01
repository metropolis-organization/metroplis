package com.metropolis.sso.auth.config;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author Pop
 * @date 2020/3/11 19:35
 */
public class ShiroImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        /**
         * @see DemoSelector
         *
         * 会将所有能够被spring扫描到的类然后取出属性
         */

        Map<String,Object> map=annotationMetadata.getAnnotationAttributes(DemoSelector.class.getName());
        /**
         * 这个map就是我们配置的属性，你可以通过这个属性来进行相对应的条件注
         * 这个取出来的是DemoSelector上注解的属性，然后你可以根据注解的内容，进行自定义化的注解
            key就是excludeClass 字符串
          */
        if(false){
            for(Map.Entry<String,Object> entry:map.entrySet()){
                // todo
                return new String[]{TestDemo.class.getName()};//符合某个条件，将这个对象给spring管理
            }
        }

        return new String[]{};//
    }
}
