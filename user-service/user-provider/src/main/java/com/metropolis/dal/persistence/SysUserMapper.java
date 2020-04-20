package com.metropolis.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.metropolis.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;


public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserById(@Param("id") Long id);

    SysUser getUserByName(@Param("name") String name);

    IPage<SysUser> query(IPage<?> page,@Param("user") SysUser user);

    void save(@Param("user") SysUser user);

    void update(@Param("user") SysUser user);

    void delete(@Param("id") long id);

    void batchDelete(@Param("ids") long[] ids);

}
