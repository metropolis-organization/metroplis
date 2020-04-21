package com.metropolis.common.web.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: metroplis
 * @description: 页面工具
 * @author: Pop
 * @create: 2020-04-20 09:22
 **/
@Data
public class PageDto implements Serializable {
    //总共多少页
    private long count;
    //一页显示多少行
    private long limit;
    //当前多少页
    private long page;
}
