package com.metropolis.ui.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:47
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 892867728334147647L;


    private Long id;
    

    //角色名称
    private String roleName;
    

    //角色描述
    private String roleDescription;
    

    //角色是否可用
    private Boolean available;
    


}