package com.metropolis.service;

import com.metropolis.user.IResourcesService;
import com.metropolis.user.entity.SysResources;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author Pop
 * @date 2020/3/17 17:15
 */
@Service
public class ResourcesServiceImpl implements IResourcesService {
    @Override
    public List<SysResources> getResourcesByUsername(String name) {
        return null;
    }

    @Override
    public List<SysResources> getResourcesByUser(SysUser sysUser) {
        return null;
    }

    @Override
    public List<SysResources> getResourcesByUserId(long id) {
        return null;
    }
}
