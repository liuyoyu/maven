package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.SystemBaseRoles;
import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.EmailStore;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.EmailsStoreService;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Service.UserRoleService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    final LoginService loginService;

    final UserService userService;

    final UserRoleService userRoleService;

    final EmailsStoreService emailsStoreService;

    @Value("${spring.mail.username}")
    String froEmailAddr;

    @Autowired
    public LoginController(LoginService loginService,
                           UserService userService,
                           EmailsStoreService emailsStoreService,
                           UserRoleService userRoleService) {
        this.loginService = loginService;
        this.userService = userService;
        this.emailsStoreService = emailsStoreService;
        this.userRoleService = userRoleService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Result loginPage(@RequestParam("account") String account,
                            @RequestParam("password") String password,
                            @RequestParam("remember") Boolean remember) {
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
    public ModelAndView logout() {
        Cookie cookie = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
        if (cookie != null) {
            cookie.setMaxAge(0);
            HttpUtil.getResponse().addCookie(cookie);
        }
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @PostMapping("/new/usr")
    public Result register(@Validated UserUF userUF, @RequestParam("code") String code, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                sb.append(objectError.getDefaultMessage() + '，');
            }
            String msg = sb.toString();
            return Result.error(msg.substring(0, msg.length() - 1));
        }
        if (code == null || "".equals(code)) {
            return Result.error("请输入验证码");
        }
        //验证信息
        String vcode = (String) LocalCache.get(SystemParameters.EMAIL_CODE + userUF.getAccount());
        if (vcode == null || "".equals(vcode)) {
            return Result.error("验证码已失效");
        }
        if (!vcode.equals(code)) {
            return Result.error("验证码错误");
        }

        //todo 密码加密
        userUF.setCreateDate(new Date());
        userService.insert(userUF);
        //创建用户角色
        UserRole userRole = new UserRole();
        userRole.setAccount(userUF.getAccount());
        userRole.setRoleId(SystemBaseRoles.USER);//注册的用户默认为 普通用户
        userRole.setCreateDate(new Date());
        userRoleService.insert(userRole);
        return Result.success("注册成功");
    }

    /**
     * 发送邮件验证码，有效时间60s
     *
     * @param account
     * @param email
     * @return
     */
    @GetMapping("/code")
    public Result code(@RequestParam("account") String account, @RequestParam("email") String email) {
        if ("".equals(account)) {
            return Result.error("请输入账号");
        }
        if ("".equals(email)) {
            return Result.error("请输入邮箱");
        }

        EmailStore emailStore = new EmailStore();
        emailStore.setAccount(account);
        emailStore.setFroEmailAddr(froEmailAddr);
        emailStore.setToEmailAddr(email);
        emailStore.setContext("1234");  //todo 随机生存验证码

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String msg = "<h1>Hi,</h1><p>您的验证码是: <strong style=\"font-size: 30px\">" + emailStore.getContext() + "</strong> ,一分钟后失效</p>" +
                "<hr><p>该消息由系统发出，请勿回复</p><br>" + sdf.format(new Date());
        emailStore.setContextHTML(msg);
        emailStore.setCreateDate(new Date());
        Message res = emailsStoreService.insert(emailStore);
        LocalCache.set(SystemParameters.EMAIL_CODE + account, emailStore.getContext(), 60000); //一分钟有效
        return Result.message(res);
    }
}
