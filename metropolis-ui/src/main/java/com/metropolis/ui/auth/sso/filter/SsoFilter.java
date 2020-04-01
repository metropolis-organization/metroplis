package com.metropolis.ui.auth.sso.filter;

import com.metropolis.common.encrypt.AECProcessor;
import com.metropolis.common.sso.SsoConstant;
import com.metropolis.common.string.StringUtils;
import com.metropolis.common.web.Cookies;
import com.metropolis.common.web.HttpClients;
import com.metropolis.common.web.dto.SysUserDto;
import com.metropolis.ui.auth.entity.SysUser;
import com.metropolis.ui.auth.listener.ShiroSessionListener;
import com.metropolis.ui.auth.redis.RedisManager;
import com.metropolis.ui.properties.SsoProperties;
import com.metropolis.ui.auth.sys.SysSession;
import com.metropolis.ui.auth.sys.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Pop
 * @date 2020/2/27 17:28
 * 单点登录验证
 */

@Slf4j
public class SsoFilter extends AdviceFilter {

    @Autowired
    private SsoProperties ssoProperties;
    @Autowired
    private SysSession sysSession;
    @Autowired
    private RedisManager redisManager;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String token= servletRequest.getParameter(SsoConstant.SHIRO_TOKEN);
        String serviceUrl = ssoProperties.getServiceUrl();
        // 如果是退出命令
        String uri = servletRequest.getRequestURI();
        if(SecurityUtils.getSubject().isAuthenticated()){
            if(SsoConstant.LOGOUT.equals(uri)){
                SecurityUtils.getSubject().logout();//本系统的登出操作
                clearServiceSession();
                servletResponse.sendRedirect(serviceUrl);//弹回登录界面
                return false;
            }
            return true;
        }else{
            //还未获取token
            if(StringUtils.isEmpty(token)){
                // 尝试从本地cookie拿取,当用户勾选记住我的时候
                Cookie cookie = Cookies.getCookieByName(SsoConstant.SHIRO_COOKIE_TOKEN,servletRequest);

                if(Objects.isNull(cookie)){
                    //需要验证
//                WebUtils.issueRedirect(request,response,ssoProperties.getServiceUrl());
                    servletResponse.sendRedirect(serviceUrl);
                }else{
                    token = cookie.getValue();
                }

            }
            // 验证token
            if(StringUtils.nonEmpty(token)){
                Map<String,String> params = new HashMap();
                params.put(SsoConstant.SHIRO_TOKEN,token);
                UserEntity entity = new UserEntity();
                entity.setSerializeCode(servletRequest.getParameter(SsoConstant.U_KEY));
                sysSession.setCurrentEntity(entity);
                String result = HttpClients.doPost(ssoProperties.getValidateTokenUrl(),params);
                if(Objects.isNull(result)||StringUtils.equals(SsoConstant.SSO_ERROR,result)){
                    //验证未通过，再去前往验证
                    sysSession.clear();
                    servletResponse.sendRedirect(serviceUrl);
                }else{
                    // 进行登录操作
                    // 取出个人信息
                    UserEntity e =sysSession.getCurrentEntity();
                    SysUser sysUser = copy((SysUserDto)AECProcessor.string2Deserialize(e.getSerializeCode()));
                    e.setSysUser(sysUser);
                    sysSession.setCurrentEntity(e);
                    SecurityUtils.getSubject().login(new UsernamePasswordToken(sysUser.getUsername(),sysUser.getPassword()));//这里只为了触发realm的操作
                    // 放行，并将token缓存起来。
                    Cookies.addCookie(SsoConstant.SHIRO_COOKIE_TOKEN,token,servletResponse);
                    return super.preHandle(request, response);
                }
            }

        }
        return false;
    }

    private void clearServiceSession(){
        redisManager.del(ShiroSessionListener.sessionSets.toArray(new String[]{}));
        log.info(" 删除验证中心会话。");
    }

    private SysUser copy(SysUserDto dto){
        SysUser sysUser = new SysUser();
        sysUser.setId(dto.getId());
        sysUser.setCreatetime(dto.getCreatetime());
        sysUser.setCredentialsSalt(dto.getCredentialsSalt());
        sysUser.setIsDelete(dto.getIsDelete());
        sysUser.setLastLoginTime(dto.getLastLoginTime());
        sysUser.setPassword(dto.getPassword());
        sysUser.setUsername(dto.getUsername());
        sysUser.setLocked(dto.getLocked());
        return sysUser;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        super.postHandle(request, response);
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
    }


}
