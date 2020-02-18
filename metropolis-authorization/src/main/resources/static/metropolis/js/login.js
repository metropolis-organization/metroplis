
layui.use(['form','layer'],function(){

    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    //登陆监听
    //提交登录表单
    form.on('submit(login-submit)', function (data) {
        $.post('/login', data.field, function (r) {
            if (r.code === '000000') {
                layer.msg(r.message);
                location.href="http://localhost:8080/index";
            } else {
                layer.msg(r.message);
                location.href="http://localhost:8080/index";
                // loading.hide();
                // initCode();
            }
        });
        return false;
    });

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