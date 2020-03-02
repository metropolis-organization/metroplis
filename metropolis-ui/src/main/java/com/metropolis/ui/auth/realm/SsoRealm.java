package com.metropolis.ui.auth.realm;

import com.metropolis.common.web.dto.SysUserDto;
import com.metropolis.ui.auth.cache.RedisCache;
import com.metropolis.ui.auth.entity.SysUser;
import com.metropolis.ui.auth.redis.RedisManager;
import com.metropolis.ui.sys.SysSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Pop
 * @date 2020/2/27 21:11
 * 此域将不再做操作，只是通过shiro的验证。
 */
@Component
public class SsoRealm extends AuthorizingRealm {

    @Autowired
    private SysSession sysSession;
    @Autowired
    private RedisManager redisManager;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 会从缓存取出数据，这里无须处理。
        SysUserDto user = (SysUserDto) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo  simpleAuthorizationInfo= (SimpleAuthorizationInfo)
                redisManager.get(RedisCache.CACHE_PREFIX+user.getId());
        return Objects.isNull(simpleAuthorizationInfo) ? null : simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SysUser sysUser = sysSession.getCurrentEntity().getSysUser();
        return new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),getName());
    }

    /**
     * 清空缓存，在需要清空身份和权限缓存的地方，调用这个方法
     */
    public void clearCache(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
