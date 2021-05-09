package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.HttpUtil;
import com.lyy.uploadfile.Utils.JWTUtil;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Result loginPage(@RequestParam("account") String account,
                            @RequestParam("password") String password,
                            @RequestParam("remember") Boolean remember){
        if ("".equals(account) || "".equals(password)) {
            return Result.error("请输入用户名和密码");
        }
        Message login = loginService.login(account, password);
        if (login.isSuccess()) {
            UserUF user = (UserUF) login.res();
            /*
            做JWT加密
             */
            JSONObject jo = new JSONObject();
            jo.put(SystemParameters.JWT_VERIDATION_NAME_1, user.getAccount());
            jo.put(SystemParameters.JWT_VERIDATION_NAME_2, user.getName());
            String userInfo = jo.toString();
            String sign = JWTUtil.sign(userInfo);

            if (remember) {
                //七天内自动登录，无需输入
                HttpUtil.setCookie(SystemParameters.COOKIE_USR_INFORMATION, sign, 3600 * 24 * 7);
            }else{
                HttpUtil.setCookie(SystemParameters.COOKIE_USR_INFORMATION, sign, 3600 * 6);
            }
            return Result.success(login.msg());
        }
        return Result.error(login.msg());
    }

    @RequestMapping(value = "/out")
    public Result logout(){
        Cookie[] cookies = HttpUtil.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if ("userInfo".equals(cookie.getName())) {
                cookie.setMaxAge(0);
            }
        }
        return Result.success("退出登录");
    }
}
