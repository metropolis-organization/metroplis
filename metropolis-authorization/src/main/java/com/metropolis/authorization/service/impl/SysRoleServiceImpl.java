package com.metropolis.authorization.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metropolis.authorization.dal.entitys.SysRole;
import com.metropolis.authorization.dal.persistence.SysRoleMapper;
import com.metropolis.authorization.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (SysRole)表服务实现类
 *
 * @author Pop
 * @since 2020-02-16 19:44:48
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true, rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    @Override
    public List<SysRole> findRolesByUsername(String username) {
        return this.baseMapper.findRolesByUsername(username);
    }
}