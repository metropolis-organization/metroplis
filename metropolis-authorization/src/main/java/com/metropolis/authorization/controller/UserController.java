package com.metropolis.authorization.controller;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.utils.PassworkHelper;
import com.metropolis.common.entity.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pop
 * @date 2020/2/16 20:28
 */
@RestController("user")
public class UserController {

    @Autowired
    private ISysUserService userService;



    @PostMapping("add")
    @RequiresPermissions("user:add")
    public Response addUser(SysUser user){

        //一个添加方法
        userService.saveUser(user);
        return Response.OK;
    }
}
