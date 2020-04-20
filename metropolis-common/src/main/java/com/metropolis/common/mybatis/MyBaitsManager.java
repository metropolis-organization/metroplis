package com.metropolis.common.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metropolis.common.web.dto.PageDto;

import java.util.List;
import java.util.Objects;

/**
 * @program: metroplis
 * @description: 封装 MybatisPlugs的部分逻辑
 * @author: Pop
 * @create: 2020-04-20 09:16
 **/
public class MyBaitsManager<O> {

    private final ThreadLocal<PageDto> pageDtoThreadLocal = new ThreadLocal<>();

    protected IPage<O> page(PageDto pageDto){
        IPage<O> page = new Page<O>();
        page.setSize(pageDto.getSize())
                .setCurrent(pageDto.getCurrent());
        pageDtoThreadLocal.set(pageDto);
        return page;
    }

    protected List<O> list(IPage<O> page){
        PageDto pageDto = pageDtoThreadLocal.get();
        if(Objects.isNull(pageDto)){return page.getRecords();}
        pageDto.setCurrent(page.getCurrent());
        pageDto.setSize(page.getSize());
        pageDtoThreadLocal.remove();
        return page.getRecords();
    }

}


