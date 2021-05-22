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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    final LoginService loginService;

    final UserService userService;

    @Autowired
    public LoginController(LoginService loginService,
                           UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
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
            //自动登录
            if (remember) {
                jo.put("remember", true);
            }
            String userInfo = jo.toString();
            String sign = JWTUtil.sign(userInfo);
            HttpUtil.setCookie(SystemParameters.COOKIE_USR_INFORMATION, sign, 3600 * 24 * 7);
            return Result.success(login.msg());
        }
        return Result.error(login.msg());
    }

    @RequestMapping(value = "/out")
    public ModelAndView logout(){
        Cookie cookie = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
        if (cookie != null) {
            cookie.setMaxAge(0);
            HttpUtil.getResponse().addCookie(cookie);
        }
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @PostMapping("/register")
    public Result register(@Validated UserUF userUF, @RequestParam("code") int code, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                sb.append(objectError.getDefaultMessage() + '，');
            }
            String msg = sb.toString();
            return Result.error(msg.substring(0, msg.length()-1));
        }

        Message res = userService.insert(userUF);

        return res.isSuccess() ? Result.error(res.msg()) : Result.success(res.msg());

    }
}
