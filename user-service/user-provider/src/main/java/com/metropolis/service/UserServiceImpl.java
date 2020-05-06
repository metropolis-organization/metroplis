package com.metropolis.service;

import com.metropolis.common.encrypt.PassworkHelper;
import com.metropolis.common.entity.Pages;
import com.metropolis.common.mybatis.MyBaitsManager;
import com.metropolis.common.web.dto.PageDto;
import com.metropolis.dal.persistence.SysUserMapper;
import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Pop
 * @date 2020/3/17 17:14
 */
@Service
public class UserServiceImpl extends MyBaitsManager<SysUser> implements IUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional(readOnly = true)
    @Override
    public SysUser getUserById(long id) {
        return sysUserMapper.getUserById(id);
    }

    public SysUser getUserById(String id){
        return getUserById(Long.parseLong(id));
    }

    @Transactional(readOnly = true)
    @Override
    public SysUser getUserByName(String name) {
        return sysUserMapper.getUserByName(name);
    }

    @Override
    public SysUser getUserByAccount(String account) {
        return sysUserMapper.getUserByAccount(account);
    }

    @Transactional(readOnly = true)
    @Override
    public Pages query(PageDto page, SysUser user) {
        return super.list(sysUserMapper.query(super.page(page),user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUser user) {
        PassworkHelper.encryptPassword(user);
        user.setUsername(user.getAccount());
        user.setCreatetime(new Date());
        user.setLastLoginTime(new Date());
        user.setIsDelete(Boolean.FALSE);
        user.setLocked(Boolean.FALSE);
        sysUserMapper.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SysUser user) {
        sysUserMapper.update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(long id) {
        sysUserMapper.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(long[] ids) {
        sysUserMapper.batchDelete(ids);
    }


}
