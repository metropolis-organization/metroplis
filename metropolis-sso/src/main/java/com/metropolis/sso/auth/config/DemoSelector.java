package com.metropolis.sso.auth.config;

/**
 * @author Pop
 * @date 2020/3/11 19:40
 */
public @interface DemoSelector {

    /**
     * 例如这里你可以写当那些class存在或者不存在的时候，你会采取什么操作
     * @return
     */
    Class[] excludeClass() default {};

    /**
     * 包含哪些类的时候会需要什么操作
     * 会有很多丰富的配置
     * @return
     */
    Class[] includeClass() default {};
}
