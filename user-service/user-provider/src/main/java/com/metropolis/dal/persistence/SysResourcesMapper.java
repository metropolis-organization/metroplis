package com.metropolis.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.user.entity.SysResources;
import com.metropolis.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysResourcesMapper extends BaseMapper<SysResources> {
    List<SysResources> getResourcesByUsername(@Param("name") String name);
    List<SysResources> getResourcesByUser(SysUser sysUser);
    List<SysResources> getResourcesByUserId(@Param("id") long id);
}
