package com.metropolis.ui.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysResources)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:46
 */
@Data
public class SysResources implements Serializable {
    private static final long serialVersionUID = 841227523533198563L;

    //资源id
    private Long id;
    

    //资源类型，是按钮还是菜单
    private String type;
    

    //资源名称
    private String name;
    

    //资源url
    private String url;
    

    //资源父级
    private Long parentId;
    

    //父级集合
    private String parentIds;
    

    //权限字符
    private String permission;
    

    //是否可用
    private Boolean available;
    


}