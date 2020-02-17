package com.metropolis.authorization.dal.entitys;

import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (SysRole)实体类
 *
 * @author Pop
 * @since 2020-02-15 23:53:47
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 892867728334147647L;

        
    @TableId(value = "id", type = IdType.AUTO)        
    private Long id;
    

    //角色名称    
        @TableField("role_name")    
    private String roleName;
    

    //角色描述    
        @TableField("role_description")    
    private String roleDescription;
    

    //角色是否可用    
        @TableField("available")    
    private Boolean available;
    


}