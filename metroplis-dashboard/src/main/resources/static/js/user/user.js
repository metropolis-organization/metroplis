layui.use(['layer','element','table','form'], function(){
    var layer = layui.layer,
        $_ = layui.jquery,
        element = layui.element,
        table = layui.table,
        form = layui.form

   // user-table 表
    table.render({
        elem: '#user-table'
        ,url:'/user/list'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,toolbar: '#toolbarDemo'
        ,defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,cols: [[
            {type:'numbers'}
            ,{type: 'checkbox'}
            ,{field:'id',title:'ID', width:100, unresize: true, sort: true,hide:true}
            ,{field:'account', title:'用户名',unresize: true}
            ,{field:'username', title:'用户名',unresize: true}
            , {fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
        ]]
        ,page: true
    });

    //监听头工具栏事件
    table.on('toolbar(user-filter)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                layer.open({
                    type: 1
                    ,title: '添加一个用户'
                    ,area: ['450px', '350px']
                    ,shade: 0
                    ,maxmin: false
                    ,resize:false
                    ,offset: 'auto'
                    ,content: $_("#user-form")
                });
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    $.ajax({
                        type: "POST",
                        url: "/user/delete",
                        data: {id:data[0].id},
                        success: function(msg){
                            layer.msg(msg.msg);
                        }
                    });
                }
                break;
        };
    });

    //监听行工具事件
    table.on('tool(user-filter)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){
            layer.msg('编辑操作');
        }
    });


    //表单自己定义
    //自定义验证规则
    form.verify({
        account:function(value){
          //增加一个账号是否存在的验证
          if(""===value){
              return "账号不能为空";
          }
          if(value.length > 5&&value.length<16){
                return '标题至少得5个字符,且不能超过16个字符';
          }
        },
        pass_check: function(value){
            if(""===value){
                return "密码不能为空";
            }
            if(form.val('user-form').password!==value){
                return '密码不一致';
            }
        }
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
    });

    //监听提交
    form.on('submit(sm)', function(data){
        $.ajax({
            type: "post",
            url: "/user/save",
            data: data.field,
            success: function(msg){
                layer.msg(msg);
            }
        });
        return false;
    });

    //监听锁定操作
    form.on('checkbox(checkboxLocked)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });
});