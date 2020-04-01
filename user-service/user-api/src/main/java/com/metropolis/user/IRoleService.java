package com.metropolis.user;

import com.metropolis.user.entity.SysRole;

import java.util.List;

/**
 * @author Pop
 * @date 2020/3/17 17:00
 */
public interface IRoleService {
    List<SysRole> getRolesByUsername(String username);
}
