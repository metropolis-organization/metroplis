package com.metropolis.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metropolis.authorization.dal.entitys.SysRole;

import java.util.List;

/**
 * (SysRole)表服务接口
 *
 * @author Pop
 * @since 2020-02-16 19:44:47
 */
public interface ISysRoleService extends IService<SysRole>{

    List<SysRole> findRolesByUsername(String username);
}