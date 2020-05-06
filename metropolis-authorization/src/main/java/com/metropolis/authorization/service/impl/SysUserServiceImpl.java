package com.metropolis.authorization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.dal.persistence.SysUserMapper;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.utils.PassworkHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Pop
 * @date 2020/2/16 19:26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true, rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser findUserByName(String username) {
        return this.baseMapper.findUserByName(username);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        return this.baseMapper.findUserByAccount(account);
    }

    @Transactional
    @Override
    public void saveUser(SysUser user) {
        PassworkHelper.encryptPassword(user);
        user.setCreatetime(new Date());
        user.setLastLoginTime(new Date());
        user.setLocked(Boolean.FALSE);
        user.setIsDelete(Boolean.FALSE);
        this.baseMapper.saveUser(user);
    }
}
