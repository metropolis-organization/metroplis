package com.metropolis.ui.controller;


import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Pop
 * @date 2020/1/30 14:54
 */
@Controller
public class HelloController {

    @Reference(check = false)
    private IUserService userService;

    @GetMapping("index")
    public String hello(){

//        SysUser sysUser=userService.getUserByName("p");
//        System.out.println(sysUser.getUsername());
        return "index";
    }

}
