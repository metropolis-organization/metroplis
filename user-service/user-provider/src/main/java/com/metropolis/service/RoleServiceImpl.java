package com.metropolis.service;

import com.metropolis.user.IRoleService;
import com.metropolis.user.entity.SysRole;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author Pop
 * @date 2020/3/17 17:15
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return null;
    }
}
