package com.metropolis.common.entity;

import com.metropolis.common.web.dto.PageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
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
    public static <D> ViewData<D> data(List<D> data,PageDto page){
        ViewData<D> d = new ViewData<>();
        d.setData(data);
        d.setPage(page);
        return d;
    }
    public static <D> ViewData<D> data(D[] data,PageDto pageDto){
        return data(Arrays.asList(data),pageDto);
    }
    public static <D> ViewData<D> data(D[] data){
        return data(Arrays.asList(data),null);
    }
    public static <D> D[] array(D ...datas){return datas;}
    public static <D> ViewData<D> data(List<D> data){
        return data(data,null);
    }
}
