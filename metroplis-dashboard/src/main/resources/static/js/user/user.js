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
            {type:'numbers'}
            ,{type: 'checkbox'}
            ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
            ,{field:'username', title:'用户名', templet: '#usernameTpl'}
            ,{field:'city', title:'城市'}
            ,{field:'wealth', title: '财富', minWidth:120, sort: true}
            ,{field:'sex', title:'性别', width:85, templet: '#switchTpl', unresize: true}
            ,{field:'lock', title:'是否锁定', width:110, templet: '#checkboxTpl', unresize: true}
        ]]
        ,page: true
    });

    //监听锁定操作
    form.on('checkbox(checkboxLocked)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });
});