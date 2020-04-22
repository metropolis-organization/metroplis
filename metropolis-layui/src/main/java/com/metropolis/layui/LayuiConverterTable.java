package com.metropolis.layui;

import com.metropolis.common.entity.Response;
import com.metropolis.common.entity.ViewData;
import com.metropolis.layui.entity.LayuiTable;

/**
 * @author Pop
 * @date 2020/4/22 21:47
 */
public class LayuiConverterTable implements LayuiConverter<LayuiTable> {

    @Override
    public LayuiTable convert(ViewData data) {
        LayuiTable table = new LayuiTable();
        Response response = data.getResponse();
        table.setCode((String) response.get(Response.CODE));
        table.setMsg((String) response.get(Response.MESSAGE));
        table.setData(data.getData());
        table.setCount(data.getPage().getCount());
        return table;
    }
}
