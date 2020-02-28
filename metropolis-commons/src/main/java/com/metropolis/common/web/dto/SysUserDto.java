package com.metropolis.common.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Pop
 * @date 2020/2/28 19:12
 */
@Data
public class SysUserDto implements Serializable {
    private static final long serialVersionUID = -9042207242550256616L;
    //用户主键id
    private Long id;


    //用户名
    private String username;


    //密码
    private String password;


    //盐
    private String salt;


    //是否锁定，0不锁定，1锁定
    private Boolean locked;


    //创建时间
    private Date createtime;


    //最后登录时间
    private Date lastLoginTime;


    //是否删除
    private Boolean isDelete;


    private String credentialsSalt;
    public String getCredentialsSalt(){return username+salt;}
}
