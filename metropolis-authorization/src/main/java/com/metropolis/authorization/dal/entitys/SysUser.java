package com.metropolis.authorization.dal.entitys;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (SysUser)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:48
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = -34540829675689426L;

    //用户主键id    
    @TableId(value = "id", type = IdType.AUTO)        
    private Long id;

    //账号
    @TableField("account")
    private String account;

    //用户名    
        @TableField("username")    
    private String username;
    

    //密码    
        @TableField("password")    
    private String password;
    

    //盐    
        @TableField("salt")    
    private String salt;
    

    //是否锁定，0不锁定，1锁定    
        @TableField("locked")    
    private Boolean locked;
    

    //创建时间    
        @TableField("createtime")    
    private Date createtime;
    

    //最后登录时间    
        @TableField("last_login_time")    
    private Date lastLoginTime;
    

    //是否删除    
        @TableField("is_delete")    
    private Boolean isDelete;


    private String credentialsSalt;
    public String getCredentialsSalt(){return username+salt;}

}