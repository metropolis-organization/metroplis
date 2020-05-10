layui.use(['layer','element','table','form'], function(){
    var layer = layui.layer,
        $_ = layui.$,
        addForm = document.getElementById("user-form"),
        accountInput = $("#user-form input[name=account]"),
        lastAccount = "",//上一次的填写的名字，避免重复查询
        repeatAccount = false,
        element = layui.element,
        table = layui.table,
        form = layui.form;

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
                    //赋值
                    form.val('user-form-update',obj.data);
                    layer.open({
                        type: 1
                        ,title: '修改用户'
                        ,area: ['450px', '350px']
                        ,shade: 0
                        ,maxmin: false
                        ,resize:false
                        ,offset: 'auto'
                        ,content: $_("#user-form-update")
                    });
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除这'+data.length+"条吗?", function(index){
                        var ids=data.map(
                            function(value,index,array){
                                return value.id;
                            }
                        );
                        $.ajax({
                            type: "POST",
                            url: "/user/batchDelete",
                            data: {ids:ids.toString()},
                            success: function(msg){
                                data.del();
                                layer.msg(msg.message);
                                layer.close(index);
                                table.reload();
                            }
                        });
                    });
                }
                break;
        }
    });

    //监听行工具事件
    table.on('tool(user-filter)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            form.val('user-form-view',obj.data);
            layer.open({
                type: 1
                ,title: '查看'
                ,area: ['450px', '350px']
                ,shade: 0
                ,maxmin: false
                ,resize:false
                ,offset: 'auto'
                ,content: $_("#user-form-view")
            });
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    type: "POST",
                    url: "/user/delete",
                    data: {id:data.id},
                    success: function(msg){
                        obj.del();
                        layer.msg(msg.message);
                        table.reload();
                        layer.close(index);
                    }
                });
            });
        } else if(layEvent === 'edit'){
            form.val('user-form-update',obj.data);
            layer.open({
                type: 1
                ,title: '修改用户'
                ,area: ['450px', '350px']
                ,shade: 0
                ,maxmin: false
                ,resize:false
                ,offset: 'auto'
                ,content: $_("#user-form-update")
            });
        }
    });


    //检测用户名是否重复
    accountInput.focusout(function(e) {
        var value = accountInput.val();
        if(""===value){return;}
        if(lastAccount===value){return}
        else{lastAccount=value;}
        $.ajax({
            type: "post",
            url: "/user/check",
            data: {account:value},
            async:false,
            success: function(msg){
                if("000000"!==msg.code){
                    repeatAccount = true;
                    layer.msg(msg.message);
                }else{
                    repeatAccount = false;
                }
            }
        });
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
        if(repeatAccount){
            $.ajax({
                type: "post",
                url: "/user/save",
                data: data.field,
                success: function(msg){
                    layer.msg(msg.message);
                    table.reload();
                    layer.closeAll();
                }
            });
        }else{
            layer.msg("重复的账号");
        }
        return false;
    });

    form.on('submit(sm-again)', function(data){
        if(repeatAccount){
            $.ajax({
                type: "post",
                url: "/user/save",
                data: data.field,
                success: function(msg){
                    layer.msg(msg.message);
                    addForm.reset();
                    table.reload();
                }
            });
        }else{
            layer.msg("重复的账号");
        }
        return false;
    });

    //监听更新
    form.on('submit(sm-update)', function(data){
        $.ajax({
            type: "post",
            url: "/user/update",
            data: data.field,
            success: function(msg){
                layer.msg(msg.message);
                table.reload();
                layer.closeAll();
            }
        });
        return false;
    });

    //监听锁定操作
    form.on('checkbox(checkboxLocked)', function(obj){
        layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });
});