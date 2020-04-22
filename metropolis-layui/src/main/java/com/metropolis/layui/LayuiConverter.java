package com.metropolis.layui;

import com.metropolis.common.entity.ViewData;

/**
 * @author Pop
 * @date 2020/4/22 21:42
 */
public interface LayuiConverter<Y> {

    /**
     * 转换成另一个对象
     * @param data
     * @return
     */
     Y convert(ViewData data);

}
