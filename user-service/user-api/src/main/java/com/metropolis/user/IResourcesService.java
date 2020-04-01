package com.metropolis.user;

import com.metropolis.user.entity.SysResources;
import com.metropolis.user.entity.SysUser;

import java.util.List;

/**
 * @author Pop
 * @date 2020/3/17 17:02
 */
public interface IResourcesService {
    
    List<SysResources> getResourcesByUsername(String name);
    List<SysResources> getResourcesByUser(SysUser sysUser);
    List<SysResources> getResourcesByUserId(long id);
}
