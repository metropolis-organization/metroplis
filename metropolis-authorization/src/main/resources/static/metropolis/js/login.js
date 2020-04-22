
layui.use(['form','layer'],function(){

    var $ = layui.jquery,
        $code=$("#codeimg"),
        form = layui.form,
        layer = layui.layer;

    initCode();

    //登陆监听
    //提交登录表单
    form.on('submit(login-submit)', function (data) {
        data.field["successUrl"]=window.location.href.split('?')[1].split('=')[1];
        $.post('/login', data.field, function (r) {
            if (r.code === '100000') {
                // layer.msg(r.message);
                location.href=r.message;
            } else {
                layer.msg(r.message);
                // location.href="http://localhost:8080/index";
                // loading.hide();
                // initCode();
            }
        });
        return false;
    });


    /* 重新生成验证码*/
    $code.click(function(e){
        console.log("触发点击事件");
        initCode();
    })

    function initCode(){$code.attr("src","/captcha?time="+new Date().getTime());}

    //注册监听
    //提交注册表单
    form.on('submit(regist-submit)', function (data) {
        if (data.field.password !== data.field.passwordB) {
            layer.msg('两次密码输入不一致！');
            return;
        }
        // var loading = $(this).find('.layui-icon');
        // if (loading.is(":visible")) return;
        // loading.show();
        $.post('/regist', data.field, function (r) {
            if (r.code === 200) {
                layer.msg('注册成功，请登录');
                // loading.hide();
                // $view.find('#login-href').trigger('click');
            } else {
                layer.msg(r.message);
                // loading.hide();
            }
        });
        return false;
    });
});

