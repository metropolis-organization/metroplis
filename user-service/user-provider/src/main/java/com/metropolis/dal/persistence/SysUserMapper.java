package com.metropolis.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.metropolis.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserById(@Param("id") Long id);

    SysUser getUserByName(@Param("name") String name);

    SysUser getUserByAccount(@Param("account") String name);

    IPage<SysUser> query(IPage<?> page,@Param("user") SysUser user);

    /**
     * 此方法，判断数据是否存在
     * @param page
     * @param user
     * @return false 表示不存在， true表示存在
     */
    IPage<SysUser> check(IPage<?> page,@Param("user") SysUser user);

    void save(@Param("user") SysUser user);

    void update(@Param("user") SysUser user);

    void delete(@Param("id") long id);

    void batchDelete(@Param("ids") long[] ids);

    void batchSave(@Param("users") List<SysUser> users);


}
