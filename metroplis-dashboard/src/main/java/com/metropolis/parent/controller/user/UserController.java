package com.metropolis.parent.controller.user;

import com.metropolis.common.entity.Response;
import com.metropolis.common.entity.ViewData;
import com.metropolis.common.web.dto.PageDto;
import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @program: metroplis
 * @description: User 控制层
 * @author: Pop
 * @create: 2020-04-20 10:50
 *
 * todo redis脚本限制操作，不要太频繁请求接口
 **/
@RestController("user")
public class UserController {

    @Reference(check = false)
    private IUserService userService;

    @GetMapping(path = "list")
    public ViewData<SysUser> list(PageDto pageDto,SysUser sysUser){
        ViewData<SysUser> data=ViewData.data();
        data.setData(userService.query(pageDto, sysUser));
        data.setPage(pageDto);
        return data;
    }

    @PostMapping(path = "update")
    public Response update(SysUser user){
        try {
            userService.update(user);
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    @PostMapping(path = "delete")
    public Response delete(@RequestParam("id") String id){
        try {
            userService.delete(Long.valueOf(id));
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    @GetMapping(path = "save")
    public Response save(SysUser user){
        try {
            userService.save(user);
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }


}
