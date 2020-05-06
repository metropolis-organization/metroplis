package com.metropolis.authorization.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.authorization.dal.entitys.SysUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author Pop
 * @since 2020-02-16 00:01:20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findUserByName(@Param("username") String username);

    SysUser findUserByAccount(@Param("account") String account);

    void saveUser(SysUser user);

    void deleteUser(long id);

    void deleteUsers(long ...ids);

    void updateUser(SysUser user);
}