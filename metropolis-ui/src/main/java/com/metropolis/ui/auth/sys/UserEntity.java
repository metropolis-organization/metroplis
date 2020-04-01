package com.metropolis.ui.auth.sys;

import com.metropolis.ui.auth.entity.SysUser;
import lombok.Data;

/**
 * @author Pop
 * @date 2020/2/28 18:42
 */
@Data
public class UserEntity {
    private SysUser sysUser;
    private String serializeCode;
}
