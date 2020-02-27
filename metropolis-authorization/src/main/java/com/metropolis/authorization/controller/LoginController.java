package com.metropolis.authorization.controller;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.properties.ValidateCodeProperties;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.validate.ValidateCodeService;
import com.metropolis.common.encrypt.AECProcessor;
import com.metropolis.common.encrypt.TokenTime;
import com.metropolis.common.entity.Response;
import com.metropolis.common.sso.SsoConstant;
import com.metropolis.common.string.StringUtils;
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

    private String param = "?name=";

    @PostMapping("login")
    public Response login(SysUser user,boolean rememberMe,String verifyCode,
                          HttpServletRequest request,HttpServletResponse response) throws Exception {

        validateCodeService.checkCode(request,verifyCode);
        //验证码 后续添加
        UsernamePasswordToken token = new UsernamePasswordToken();
        //授权
        super.login(super.getToken(user,rememberMe));

        //取出登陆成功后跳转的位置。
        String successUrl = request.getParameter(SsoConstant.SUCCESS_URL);
        if(StringUtils.nonEmpty(successUrl)){
            //颁发token进参数和cookie
            SysUser sysUser=super.getCurrentUser();
            response.sendRedirect(getSuccessUrl(successUrl,sysUser));
        }
        return Response.OK;
    }

    private String getSuccessUrl(String url,SysUser sysUser) throws Exception{
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append(SsoConstant.U).append(AECProcessor.serialize2String(sysUser))
                .append(SsoConstant.AND);
        // 默认60 秒后超时
        stringBuilder.append(SsoConstant.SHIRO_TOKEN).append(AECProcessor.token(TokenTime.SECOND,60));
        return stringBuilder.toString();
    }

    @GetMapping("validateToken")
    public Response validateToken(HttpServletRequest request){
        String token = request.getParameter(SsoConstant.SHIRO_TOKEN);
        //一个验证方法
        if(AECProcessor.checkToken(token)){
            //验证成功后返回验证信息
            return Response.OK;
        }else{
            return Response.TOKEN_VALID_FAILED;
        }
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
