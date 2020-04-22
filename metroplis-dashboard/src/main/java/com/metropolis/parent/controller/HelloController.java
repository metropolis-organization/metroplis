package com.metropolis.parent.controller;

import com.metropolis.parent.utils.Views;
import com.metropolis.sso.auth.controller.ShiroController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 整合一些页面跳转逻辑
 *
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-09 15:58
 **/
@Controller
public class HelloController extends ShiroController {

    @GetMapping(value = "index")
    public ModelAndView hello(){
        return Views.get("hello",Views.parmas("user",super.getCurrentUser()));
    }

    @GetMapping(value = "console")
    public ModelAndView index(){
        return Views.get("index");
    }

    @GetMapping(value = "user")
    public ModelAndView user(){
        return Views.get("user/user");
    }

    @GetMapping(value = "role")
    public ModelAndView role(){
        return Views.get("user/role");
    }
}
