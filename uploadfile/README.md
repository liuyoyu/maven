# README

###登录页面跳转逻辑

接口/login/check接收用户发送的账号密码，当账号密码错误时，返回提示信息；账号密码正确时，会将用户的用户姓名和用户账号
封装为json格式，然后用JWT加密作为token发送给前台，token存储在cookie中，并对cookie设置一个有效时常，当cookie有效时
请求不会被拦截，而cookie失效后，请求将会重定向到登录界面。
用户若选择"记住我"后，下次访问时将直接进入主界面，不用再进行登录。
后台使用了一个PageController控制类来进行跳转处理，用户登录成功后，通过/uploadFile/mainPage接口跳转到主页面。拦截器
拦截到请求后，通过/uploadFile/loginPage接口跳转到登录界面。注销登录时，是直接调用/login接口跳转到登录界面。
template中并没有index.html页面，用户访问时是通过PageController中的"/"进行跳转到登录界面，自动登录就是在这儿进行判断的。

注销登录还有点问题