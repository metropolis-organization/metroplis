package com.metropolis.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metropolis.authorization.dal.entitys.SysResources;

import java.util.List;

/**
 * (SysResources)表服务接口
 *
 * @author Pop
 * @since 2020-02-16 19:44:49
 */
public interface ISysResourcesService extends IService<SysResources>{

    List<SysResources> findResourcesByUsername(String username);
  
}