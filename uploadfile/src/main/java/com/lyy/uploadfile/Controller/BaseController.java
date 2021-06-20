package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Configture.SystemBaseRoles;
import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Service.*;
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

    @Autowired
    UserRoleService userRoleService;

    public BaseController(){
        this.modelAndView = new ModelAndView();
    }

    /**
     * 负责后台用户界面跳转
     * @param view 视图 与template文件下的页面名称一一对应
     */
    protected void setPageParam(String view){
        //根据用户角色，获取左菜单列表
        List<UserRoleDTO> userRole = loginService.getLoginInfoDetail();
        UserRoleDTO user = getUsingRole(userRole);
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
        addLoginRole(user.getAccount());
    }

    protected void addLoginRole(String account){
        List<UserRoleDTO> byAccount = userRoleService.getByAccount(account);
        List<UserRoleDTO> userRoleList = new ArrayList<>();
        UserRoleDTO currRole = null;
        for (UserRoleDTO userRoleDTO : byAccount) {
            if (userRoleDTO.getStatus() == UserRole.STATUS.USING.val()) {
                currRole = userRoleDTO;
            }
            UserRoleDTO t = new UserRoleDTO();
            t.setAccount(userRoleDTO.getAccount());
            t.setUserName(userRoleDTO.getUserName());
            t.setRoleId(userRoleDTO.getRoleId());
            t.setRoleName(userRoleDTO.getRoleName());
            userRoleList.add(t);
        }
        modelAndView.addObject("curr_role", currRole.getRoleName());
        modelAndView.addObject("curr_role_id", currRole.getRoleId());
        modelAndView.addObject("user_role_list", userRoleList);
    }

    protected UserRoleDTO getUsingRole(List<UserRoleDTO> list) {
        UserRoleDTO userRoleDTO = null;
        for (UserRoleDTO roleDTO : list) {
            if (roleDTO.getStatus() == UserRole.STATUS.USING.val()) {
                return roleDTO;
            }
            if (roleDTO.getRoleId() == SystemBaseRoles.USER) {
                userRoleDTO = roleDTO;
            }
        }
        userRoleService.update(userRoleDTO.getId(), UserRole.STATUS.USING.val());
        return userRoleDTO; //没有正在使用中的角色，则返回用户角色；
    }
}
