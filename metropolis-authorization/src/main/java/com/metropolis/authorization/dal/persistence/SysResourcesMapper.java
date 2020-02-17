package com.metropolis.authorization.dal.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metropolis.authorization.dal.entitys.SysResources;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysResources)表数据库访问层
 *
 * @author Pop
 * @since 2020-02-16 00:01:19
 */
public interface SysResourcesMapper extends BaseMapper<SysResources> {

    List<SysResources> findResourcesByUsername(@Param("username") String username);

}