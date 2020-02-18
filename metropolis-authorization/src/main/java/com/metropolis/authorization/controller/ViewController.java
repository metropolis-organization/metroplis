package com.metropolis.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Pop
 * @date 2020/2/16 17:25
 */
@Controller("view")
public class ViewController {

    @GetMapping("login")
    public String login(){ return "login"; }

    @GetMapping("index")
    public String index(){return "index";}
}
