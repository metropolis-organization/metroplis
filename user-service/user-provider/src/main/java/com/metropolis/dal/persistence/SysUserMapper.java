package com.metropolis.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserById(@Param("id") Long id);

    SysUser getUserByName(@Param("name") String name);

}
