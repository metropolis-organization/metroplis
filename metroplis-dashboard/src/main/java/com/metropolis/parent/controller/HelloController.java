package com.metropolis.parent.controller;

import com.metropolis.common.web.view.Views;
import com.metropolis.sso.auth.controller.ShiroController;
import com.metropolis.sso.auth.sys.SysSession;
import com.metropolis.sso.auth.sys.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-09 15:58
 **/
@Controller
public class HelloController extends ShiroController {

    @GetMapping(value = "index")
    public ModelAndView index(){
        return Views.get("index",Views.parmas("user",super.getCurrentUser()));
    }

}
