package com.metropolis.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUser)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:48
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = -34540829675689426L;

    //用户主键id
    private Long id;
    
    //账号
    private String account;
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