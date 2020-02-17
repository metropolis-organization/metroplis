package com.metropolis.authorization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metropolis.authorization.dal.entitys.SysResources;
import com.metropolis.authorization.dal.persistence.SysResourcesMapper;
import com.metropolis.authorization.service.ISysResourcesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (SysResources)表服务实现类
 *
 * @author Pop
 * @since 2020-02-16 19:44:49
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true, rollbackFor = Exception.class)
public class SysResourcesServiceImpl extends ServiceImpl<SysResourcesMapper, SysResources> implements ISysResourcesService {


    @Override
    public List<SysResources> findResourcesByUsername(String username) {
        return this.baseMapper.findResourcesByUsername(username);
    }
}