package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import com.lyy.uploadfile.Utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    MenuService menuService;

    MenuRoleService menuRoleService;

    public MenuController(MenuService menuService, MenuRoleService menuRoleService) {
        this.menuService = menuService;
        this.menuRoleService = menuRoleService;
    }

    @GetMapping("/list")
    public Result getAllMenu(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<Menu> allByPage = menuService.getAllByPage(page, limit);
        int n = menuService.countAllByPage();
        return PageData.success(allByPage, n);
    }

    @GetMapping("/role/list")
    public Result getAllMenuRole(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<MenuRole> all = menuRoleService.getAll(page, limit);
        int n = menuRoleService.countByPage();
        return PageData.success(all, n);
    }

    @GetMapping("/role/delete")
    public Result deleteMenuRole(@RequestParam("id") long id) {
        Message delete = menuRoleService.delete(id);
        return Result.message(delete);
    }
}
