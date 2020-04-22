package com.metropolis.layui.annotation;

import java.lang.annotation.*;

/**
 * @author Pop
 * @date 2020/4/22 21:34
 *
 * layui 动态表格的解析
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LayuiTable {
}
