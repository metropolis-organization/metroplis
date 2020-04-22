package com.metropolis.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.metropolis.common.web.dto.PageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Pop
 * @date 2020/4/22 23:08
 */
@Data
public class Pages<O> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<O> data;
    private PageDto pageDto;

    private Pages(List<O> data, PageDto pageDto) {
        this.data = data;
        this.pageDto = pageDto;
    }

    public static <O> Pages<O> pages(List<O> data, PageDto pageDto){
        return new Pages<>(data,pageDto);
    }
}
