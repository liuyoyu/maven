package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Component
public class BaseController {
    protected ModelAndView modelAndView;

    @Autowired
    LoginService loginService;

    @Autowired
    FileService fileService;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRoleService menuRoleService;

    public BaseController(){
        this.modelAndView = new ModelAndView();
    }

    /**
     * 负责后台用户界面跳转
     * @param view 视图 与template文件下的页面名称一一对应
     */
    protected void getMenu(String view){
        //根据用户角色，获取左菜单列表
        UserRoleDTO user = loginService.getLoginInfoDetail();
        List<MenuRole> menuRoles = menuRoleService.getByRole(user.getRoleId());
        List<Menu> res = new ArrayList<>();
        Map<Long, Menu> t1 = new HashMap<>();
        Map<Long, List<Menu>> t2 = new HashMap<>();
        for (int i = 0; i < menuRoles.size(); i++) {
            MenuRole m = menuRoles.get(i);
            Menu mn = new Menu(m);
            t1.put(mn.getId(), mn);
            if (SystemParameters.MENUPARENTID == m.getMenuParentId()) {
                continue;
            }
            List<Menu> list = t2.getOrDefault(mn.getParentId(), new ArrayList<>());
            list.add(mn);
            t2.put(mn.getParentId(), list);
        }
        for (Map.Entry<Long, List<Menu>> entry : t2.entrySet()) {
            long key = entry.getKey();
            Menu menu = t1.get(key);
            menu.setChild(entry.getValue());
            res.add(menu);
        }
        //控制跳转
        if (view != null && !"".equals(view)){
            modelAndView.setViewName(view);
        }else{
            modelAndView.setViewName("error");
        }
        modelAndView.addObject("menu", res);
        modelAndView.addObject("user_account", user.getAccount());
        modelAndView.addObject("user_name", user.getUserName());
    }
}
