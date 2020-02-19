package com.metropolis.authorization.controller;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.properties.ValidateCodeProperties;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.validate.ValidateCodeService;
import com.metropolis.common.entity.Response;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Pop
 * @date 2020/2/16 15:45
 */
@RestController
public class LoginController extends ShiroController{

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @PostMapping("login")
    public Response login(SysUser user,boolean rememberMe,String verifyCode,
                          HttpServletRequest request){

        validateCodeService.checkCode(request,verifyCode);
        //验证码 后续添加
        UsernamePasswordToken token = new UsernamePasswordToken();
        //授权
        super.login(super.getToken(user,rememberMe));

        return Response.OK;
    }


    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateCodeService.generateCode(request, response);
    }

    @PostMapping("regist")
    public Response regist(SysUser user){
        //一个添加方法
        userService.saveUser(user);
        return Response.OK;
    }

}
