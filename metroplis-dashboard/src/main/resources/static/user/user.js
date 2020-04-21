layui.use(['element','table','form'], function(){
    var element = layui.element,
        table = layui.table,
        form = layui.form;

   // user-table 表
    table.render({
        elem: '#user-table'
        ,url:'/user/list'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,cols: [[
            {field:'id'}
            ,{field:'username', width:80, title: '用户名'}
            ,{field:'createtime', width:80, title: '创建时间'}
            ,{field:'locked', title:'是否锁定', width:110, templet: '#checkboxLocked', unresize: true}
        ]]
    });

    //监听锁定操作
    form.on('checkbox(checkboxLocked)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });
});