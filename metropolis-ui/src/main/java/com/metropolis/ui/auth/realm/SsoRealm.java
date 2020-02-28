package com.metropolis.ui.auth.realm;

import com.metropolis.common.string.StringUtils;
import com.metropolis.ui.auth.entity.SysUser;
import com.metropolis.ui.sys.SysSession;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pop
 * @date 2020/2/27 21:11
 * 此域将不再做操作，只是通过shiro的验证。
 */
@Component
public class SsoRealm extends AuthorizingRealm {

    @Autowired
    private SysSession sysSession;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 会从缓存取出数据，这里无须处理。
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SysUser sysUser = sysSession.getCurrentEntity().getSysUser();
        return new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),getName());
    }
}
