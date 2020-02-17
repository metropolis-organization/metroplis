package com.metropolis.authorization.realm;

import com.metropolis.authorization.dal.entitys.SysResources;
import com.metropolis.authorization.dal.entitys.SysRole;
import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.service.ISysResourcesService;
import com.metropolis.authorization.service.ISysRoleService;
import com.metropolis.authorization.service.ISysUserService;
import com.metropolis.authorization.utils.PassworkHelper;
import com.metropolis.common.string.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Pop
 * @date 2020/2/15 22:17
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysResourcesService resourcesService;

    /**
     * 获取身份权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<SysRole> roles = roleService.findRolesByUsername(username);
        Set<String> roleSet = roles.stream().map(SysRole::getRoleName).collect(Collectors.toSet());
        authorizationInfo.setRoles(roleSet);

        List<SysResources> resources = resourcesService.findResourcesByUsername(username);
        Set<String> resourceSet = resources.stream().map(SysResources::getPermission).collect(Collectors.toSet());
        authorizationInfo.setStringPermissions(resourceSet);

        return authorizationInfo;
    }

    /**
     * 获取身份信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String password = StringUtils.buildString((char[]) authenticationToken.getCredentials());

        SysUser user = userService.findUserByName(username);

        if(Objects.isNull(user)){
            throw new UnknownAccountException("账号不存在。");
        }
        if(PassworkHelper.checkPassword(user,password)){
            throw new IncorrectCredentialsException("用户名或密码错误。");
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException("账号已被锁定。");
        }

        return new SimpleAuthenticationInfo(user,password,getName());
    }
}
