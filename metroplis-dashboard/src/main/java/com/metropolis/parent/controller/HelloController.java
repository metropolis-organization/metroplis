package com.metropolis.parent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-09 15:58
 **/
@Controller
public class HelloController {

    @GetMapping(value = "index")
    public String index(){
        return "index";
    }

}
