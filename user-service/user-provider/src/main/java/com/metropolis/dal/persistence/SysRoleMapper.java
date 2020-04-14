package com.metropolis.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.user.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRolesByUsername(@Param("username") String username);
}
