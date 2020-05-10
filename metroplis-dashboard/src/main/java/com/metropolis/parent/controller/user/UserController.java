package com.metropolis.parent.controller.user;

import com.metropolis.common.entity.Response;
import com.metropolis.common.entity.ViewData;
import com.metropolis.common.utils.ArrayUtils;
import com.metropolis.common.web.dto.PageDto;
import com.metropolis.layui.annotation.LayuiTable;
import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @program: metroplis
 * @description: User 控制层
 * @author: Pop
 * @create: 2020-04-20 10:50
 *
 * todo redis脚本限制操作，不要太频繁请求接口
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference(check = false)
    private IUserService userService;

    @GetMapping(value = "/list")
    @LayuiTable
    public ViewData<SysUser> list(PageDto pageDto,SysUser sysUser){
        return ViewData.data(userService.query(pageDto, sysUser));
    }

    @GetMapping(value = "/detail/{id}")
    public ViewData<SysUser> user(@PathVariable String id){
        return ViewData.data(ViewData.array(userService.getUserById(id)));
    }

    @PostMapping(value = "/update")
    public Response update(SysUser user){
        try {
            userService.update(user);
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestParam("id") String id){
        try {
            userService.delete(Long.valueOf(id));
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    @PostMapping(value = "/batchDelete")
    public Response batchDelete(@RequestParam("ids") String ids){
        try {
            userService.batchDelete(ArrayUtils.string2LongArray(ids.split(",")));
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    public Response excelTemplate(){
        return null;
    }

    public Response excelExport(){
        return null;
    }

    public Response excelImport(){
        return null;
    }

    @PostMapping(value = "/save")
    public Response save(SysUser user){
        try {
            userService.save(user);
        }catch (Exception e){
            return Response.FAILURE;
        }
        return Response.OK;
    }

    @PostMapping(value = "/check")
    public Response check(SysUser user){
        if(userService.check(user)){//找到了
            return Response.USERNAME_ALREADY_EXISTS;
        }else{
            return Response.OK;
        }
    }


}
