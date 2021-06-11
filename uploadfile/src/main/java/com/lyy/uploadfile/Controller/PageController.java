package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.RoleUF;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.RoleService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.HttpUtil;
import com.lyy.uploadfile.Utils.JWTUtil;
import com.lyy.uploadfile.Utils.Message;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * 控制thymeleaf页面跳转
 */
@Controller
public class PageController extends BaseController {

    RoleService roleService;

    UserService userService;

    @Autowired
    public PageController(RoleService role, UserService userService) {
        this.roleService = role;
        this.userService = userService;
    }

    /**
     * 对跳转页面进行判断
     * @return
     */
    @RequestMapping("/")
    @ResponseBody
    public ModelAndView jumpTo(){
        Cookie cookieByName = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
        if (cookieByName == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        String token = cookieByName.getValue();
        if (JWTUtil.verify(token)) {
            //判断是否自动登录
            String info = JWTUtil.getTokenStr(token);
            JSONObject jsonObject = new JSONObject(info);
            Object remember = jsonObject.get("remember");
            if (remember != null && (Boolean) remember) {
                modelAndView.setViewName("main");
                modelAndView.addObject("user_account", jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_1));
                modelAndView.addObject("user_name", jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_2));
                return modelAndView;
            }
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/uploadFile/mainPage")
    @ResponseBody
    public ModelAndView jumpToMainPage(){
        Cookie cookieByName = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
        if (cookieByName == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        } else {
            String value = cookieByName.getValue();
            /*
            做JWT解密
             */
            if (!JWTUtil.verify(value)) {
                modelAndView.setViewName("login");
                return modelAndView;
            }
            String userInfo = JWTUtil.getTokenStr(value);
            //获取到用户信息
            JSONObject jsonObject = new JSONObject(userInfo);
            String name = (String)jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_2);
            String account = (String) jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_1);
            modelAndView.setViewName("main");
            modelAndView.addObject("user_name", name);
            modelAndView.addObject("user_account", account);
            return modelAndView;
        }
    }

    private static final String[] msg={"密码失效，请从新登录", "请先登录！"};
    @RequestMapping("/uploadFile/loginPage")
    @ResponseBody
    public ModelAndView jumpToLoginPage(@RequestParam("status") int status) {
        modelAndView.setViewName("login");
        modelAndView.addObject("message", status>=0 && status<2 ? msg[status] : "");
        return modelAndView;
    }

    @GetMapping("/uploadFile/myDownload")
    @ResponseBody
    public ModelAndView jumpToMyDownload(){
        UserUF loginInfo = loginService.getLoginInfo();
        modelAndView.setViewName("downloadList");
        modelAndView.addObject("user_account", loginInfo.getAccount());
        modelAndView.addObject("user_name", loginInfo.getName());
        return modelAndView;
    }

    @GetMapping("/uploadFile/myTask")
    @ResponseBody
    public ModelAndView jumpToMyTask(){
        UserUF loginInfo = loginService.getLoginInfo();
        modelAndView.setViewName("task");
        modelAndView.addObject("user_account", loginInfo.getAccount());
        modelAndView.addObject("user_name", loginInfo.getName());
        return modelAndView;
    }

    @GetMapping("/uploadFile/administrator")
    @ResponseBody
    public ModelAndView jumpToAdmin(){
        getMenu("admin");
        return modelAndView;
    }

    @GetMapping(value = {"/uploadFile/admin/{page}"})
    @ResponseBody
    public ModelAndView jumpToAdminPage(@PathVariable("page") String page) {
        //检查url是否存在
        int i = menuService.checkUrlNum("/uploadFile/admin/" + page);
        if (i < 1) {
            modelAndView.setViewName("error");
            return modelAndView;
        }
        getMenu(page);
        specValue(page);
        return modelAndView;
    }

    private void specValue(String page) {
        if ("userRole".equals(page)) {
            List<RoleUF> all = roleService.getAll();
            List<UserUF> allAccountAndName = userService.getAllAccountAndName();
            modelAndView.addObject("role_select", all);
            modelAndView.addObject("user_select", allAccountAndName);
        }
        if ("menuRole".equals(page)) {
            List<RoleUF> all = roleService.getAll();
            List<Menu> menus = menuService.getIdAndName();
            modelAndView.addObject("role_select", all);
            modelAndView.addObject("menu_select", menus);
        }
    }
}
