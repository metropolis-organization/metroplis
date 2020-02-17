package com.metropolis.authorization.dal.entitys;

import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (SysResources)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:46
 */
@Data
@TableName("sys_resources")
public class SysResources implements Serializable {
    private static final long serialVersionUID = 841227523533198563L;

    //资源id    
    @TableId(value = "id", type = IdType.AUTO)        
    private Long id;
    

    //资源类型，是按钮还是菜单    
        @TableField("type")    
    private String type;
    

    //资源名称    
        @TableField("name")    
    private String name;
    

    //资源url    
        @TableField("url")    
    private String url;
    

    //资源父级    
        @TableField("parent_id")    
    private Long parentId;
    

    //父级集合    
        @TableField("parent_ids")    
    private String parentIds;
    

    //权限字符    
        @TableField("permission")    
    private String permission;
    

    //是否可用    
        @TableField("available")    
    private Boolean available;
    


}