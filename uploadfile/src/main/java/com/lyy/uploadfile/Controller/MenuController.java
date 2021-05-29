package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import com.lyy.uploadfile.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    MenuService menuService;

    MenuRoleService menuRoleService;

    LoginService loginService;

    @Autowired
    public MenuController(MenuService menuService, MenuRoleService menuRoleService,
                          LoginService loginService) {
        this.menuService = menuService;
        this.menuRoleService = menuRoleService;
        this.loginService = loginService;
    }

    @GetMapping("/list")
    public Result getAllMenu(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<Menu> allByPage = menuService.getAllByPage(page, limit);
        int n = menuService.countAllByPage();
        return PageData.success(allByPage, n);
    }

    @GetMapping("/list/search")
    public Result getBySearch(@RequestParam("id") long id,
                              @RequestParam("name") String name,
                              @RequestParam("url") String url,
                              @RequestParam("parentId") long parentId,
                              @RequestParam("status") int status,
                              @RequestParam("createDate") String createDate,
                              @RequestParam("page") int page,
                              @RequestParam("limit") int limit) {
        int start = (page - 1) * limit, end = page * limit;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date parse = null;
        try {
            parse = sdf.parse(createDate);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        menuService.search(id, parentId, name, status, parse, url, start, end);
        return null;
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
