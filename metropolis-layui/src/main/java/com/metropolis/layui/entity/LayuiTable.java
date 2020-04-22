package com.metropolis.layui.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Pop
 * @date 2020/4/22 21:45
 */
@Data
public class LayuiTable {
    private String code;
    private List<Object> data;
    private long count;
    private String msg;
}
