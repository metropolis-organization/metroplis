# metroplis

2020/4/8

需求更新：

* 权限模块

要求角色的新增，授权。

当新用户注册成功，要求赋予初始权限。



总规划为，后台管理系统（提供监控流量，用户管理，权限模块等）、游戏资讯网站（发帖，留言，等级制度）



### 单点登陆的技术总结

正常的单点登陆流程

![1586836341727](.\img\1586836341727.png)

主要要点在于token的加密解密的设计，还有session的共享问题，当你登陆成功后，你的session将会被填充用户数据，由于服务器将会持有每个会话的sessionId来保证会话彼此独立，所以你只需要能找到那个用于验证成功的会话即可。

##### 解决方案

token的加解密你可以利用java现成的工具进行，需要注意的是，token的解密后应该携带例如超时时间的信息，来保证与session生命周期一样。

session的共享问题可以通过存入第三方工具来实现每个系统之间的共享，你可以存入数据库，也可以存入redis，或者其他带有持久化机制的第三方服务就行，保证其它系统可以与他通信并且拿到想要的Session

##### Shiro的单点登陆总结

Shiro的引入将会接管Web服务的Session，例如Session的开启，关闭，超时还有持久化，你需要继承，`org.apache.shiro.session.mgt.eis`下的`AbstractSessionDAO`，来实现自己的Session持久化策略，默认Shiro的sessionid生成规则为UUID，本案例中，使用Redis来接管Session的持久化。

当你的子系统向验证中心请求的时候，就已经建立一个会话，并持久化到redis中，当你登陆完成时返回子系统主页的时候，就已经发现登陆成功，并且信息都已经查询出来，原因是Shiro已经将session的管理工作交给了redis，web端已经不再管理session的生命周期，当你的子系统请求发送过来时，cookies带着sessionId进入系统，系统中的shiro会去redis查询是否有这条session记录，存在即返回，所以一个用户将会只使用一个session来和各个系统中交流。退出的时候也会走之前我们定义好的踢出session的流程。

但是在开发过程中，发现退出和重新登录的时候，总会莫名的多一个session，经过反序列化我们知道这是一个请求favicon.ico的请求，需要在页面加入。

```html
<link rel="icon" href="data:image/ico;base64,aWNv">
```

来保证不会为这条请求创造额外的session。

如果你不希望在页面中增加这个，需要在即将进入SessionDao的时候，判断是否是请求icon的请求，并且将它挡下来。