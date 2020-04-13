package com.metropolis.authorization.controller;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.properties.ValidateCodeProperties;
import com.metropolis.authorization.redis.RedisManager;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.session.RedisSessionDao;
import com.metropolis.authorization.validate.ValidateCodeService;
import com.metropolis.common.constants.SysCodeConstants;
import com.metropolis.common.encrypt.AECProcessor;
import com.metropolis.common.encrypt.TokenTime;
import com.metropolis.common.entity.Response;
import com.metropolis.common.sso.SsoConstant;
import com.metropolis.common.string.StringUtils;
import com.metropolis.common.web.HttpClients;
import com.metropolis.common.web.QueryStrings;
import com.metropolis.common.web.dto.SysUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pop
 * @date 2020/2/16 15:45
 */
@RestController
@Slf4j
public class LoginController extends ShiroController{

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @PostMapping("login")
    public Response login(SysUser user, boolean rememberMe, String verifyCode,
                          HttpServletRequest request, HttpSession session) throws Exception {

        validateCodeService.checkCode(request,verifyCode);
        //授权
        super.login(super.getToken(user,rememberMe));
        //取出登陆成功后跳转的位置。
        String successUrl = request.getParameter(SsoConstant.SUCCESS_URL);
        if(StringUtils.nonEmpty(successUrl)){
            //颁发token进参数和cookie
            SysUserDto sysUser=super.getCurrentUser();
            sendAuth(successUrl,sysUser);
            return new Response(SysCodeConstants.SUCCESS.getCode(),successUrl);
        }
        return Response.OK;
    }

    private void sendAuth(String url,SysUserDto sysUser) throws Exception{
        Map<String,String> map = new HashMap<>();
        map.put(SsoConstant.U_KEY,AECProcessor.serialize2String(sysUser));
        map.put(SsoConstant.SHIRO_TOKEN,AECProcessor.token(TokenTime.HOUR,1));
        HttpClients.doPost(url,map);
    }



    @PostMapping("validateToken")
    public String validateToken(HttpServletRequest request){
        String token = request.getParameter(SsoConstant.SHIRO_TOKEN);
        log.info("token 的值"+token);
        //一个验证方法
        if(StringUtils.nonEmpty(token)&&AECProcessor.checkToken(token)){
            //验证成功后返回验证信息
            return SsoConstant.SSO_OK;
        }else{
            return SsoConstant.SSO_ERROR;
        }
    }

    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateCodeService.generateCode(request, response);
    }

    @GetMapping("logout")
    public void logout(){
        super.logout();
    }

    @PostMapping("regist")
    public Response regist(SysUser user){
        //一个添加方法
        userService.saveUser(user);
        return Response.OK;
    }

}
