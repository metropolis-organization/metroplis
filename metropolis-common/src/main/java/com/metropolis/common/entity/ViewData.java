package com.metropolis.common.entity;

import com.metropolis.common.web.dto.PageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: metroplis
 * @description: 界面数据汇总
 * @author: Pop
 * @create: 2020-04-20 10:53
 **/
@Data
public class ViewData<D> implements Serializable {
    /**
     * todo 后期想法，加上不同的支持注解，既可以将这个Data
     *  变成不同的符合要求json返回
     */
    private List<D> data;
    private PageDto page;
    /**
     * 其它需要展示到页面的参数进行拼装
     */
    public static ViewData data(){return new ViewData();}
}
