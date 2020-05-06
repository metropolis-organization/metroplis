package com.metropolis.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.metropolis.common.entity.Pages;
import com.metropolis.common.web.dto.PageDto;
import com.metropolis.user.entity.SysUser;

import java.util.List;

/**
 * @author Pop
 * @date 2020/3/17 16:58
 */
public interface IUserService  {

    SysUser getUserById(long id);

    SysUser getUserById(String id);

    SysUser getUserByName(String name);

    SysUser getUserByAccount(String account);

    Pages query(PageDto pageDto, SysUser user);

    void save(SysUser user);

    void update(SysUser user);

    void delete(long id);

    void batchDelete(long[] ids);
}
