package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Utils.HttpUtil;
import com.lyy.uploadfile.Utils.JWTUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制thymeleaf页面跳转
 */
@Controller
public class PageController extends BaseController {

    LoginService loginService;

    FileService fileService;

    MenuService menuService;

    MenuRoleService menuRoleService;

    @Autowired
    public PageController(LoginService loginService,
                          FileService fileService,
                          MenuService menuService,
                          MenuRoleService menuRoleService) {
        this.loginService = loginService;
        this.fileService = fileService;
        this.menuService = menuService;
        this.menuRoleService = menuRoleService;
    }

    /**
     * 对跳转页面进行判断
     * @return
     */
    @RequestMapping("/")
    @ResponseBody
    public ModelAndView jumpTo(){
        Cookie cookieByName = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
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

    @GetMapping("/uploadFile/admin")
    @ResponseBody
    public ModelAndView jumpToAdmin(){
        UserUF user = loginService.getLoginInfo();
        modelAndView.setViewName("/admin");
        modelAndView.addObject("menu", getMenu());
        modelAndView.addObject("user_account", user.getAccount());
        modelAndView.addObject("user_name", user.getName());
        return modelAndView;
    }

    private List<Menu> getMenu(){
        UserRoleDTO user = loginService.getLoginInfoDetail();
        List<MenuRole> menuRoles = menuRoleService.getByRole(user.getId());
        List<Menu> res = new ArrayList<>();
        Map<Long, Menu> t1 = new HashMap<>();
        Map<Long, List<Menu>> t2 = new HashMap<>();
        for (int i = 0; i < menuRoles.size(); i++) {
            MenuRole m = menuRoles.get(i);
            Menu mn = new Menu(m);
            t1.put(m.getId(), mn);
            if (SystemParameters.MENUPARENTID == m.getMenuParentId()) {
                continue;
            }
            List<Menu> list = t2.getOrDefault(m.getMenuParentId(), new ArrayList<>());
            list.add(mn);
            t2.put(m.getMenuParentId(), list);
        }
        for (Map.Entry<Long, List<Menu>> entry : t2.entrySet()) {
            long key = entry.getKey();
            Menu menu = t1.get(key);
            menu.setChild(entry.getValue());
            res.add(menu);
        }
        return res;
    }
}
