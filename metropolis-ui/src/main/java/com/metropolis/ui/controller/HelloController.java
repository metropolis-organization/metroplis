package com.metropolis.ui.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Pop
 * @date 2020/1/30 14:54
 */
@Controller
public class HelloController {

    @GetMapping("index")
    public String hello(){
        return "index";
    }

}
