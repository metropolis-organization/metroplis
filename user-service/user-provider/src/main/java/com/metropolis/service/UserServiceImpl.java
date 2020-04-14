package com.metropolis.service;

import com.metropolis.dal.persistence.SysUserMapper;
import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Pop
 * @date 2020/3/17 17:14
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserById(Long id) {
        return sysUserMapper.getUserById(id);
    }

    @Override
    public SysUser getUserByName(String name) {
        return sysUserMapper.getUserByName(name);
    }
}
