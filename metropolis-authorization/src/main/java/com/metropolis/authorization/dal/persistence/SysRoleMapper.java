package com.metropolis.authorization.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.authorization.dal.entitys.SysRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysRole)表数据库访问层
 *
 * @author Pop
 * @since 2020-02-16 00:01:20
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findRolesByUsername(@Param("username") String username);

}