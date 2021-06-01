package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Configture.SystemParameters;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/parent/all")
    public Result getParentMenu(){
        List<Menu> all = menuService.getByParentId(SystemParameters.MENUPARENTID);
        return Result.success("获取菜单成功", all);
    }

    @GetMapping("/list/search")
    public Result getBySearch(@RequestParam("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("url") String url,
                              @RequestParam("parentId") String parentId,
                              @RequestParam("status") String status,
                              @RequestParam("createDate") String createDate,
                              @RequestParam("page") int page,
                              @RequestParam("limit") int limit) {
        int start = (page - 1) * limit, end = page * limit;
        Message search = menuService.search(id, parentId, name, status, createDate, url, start, end);
        return PageData.message(search);
    }

    @PostMapping("/new")
    public Result insertMenu(@RequestParam("name") String name,
                             @RequestParam("parentId") String parentId,
                             @RequestParam("url") String url,
                             @RequestParam("seq") double seq,
                             @RequestParam("status") int status) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setParentId("".equals(parentId) ? SystemParameters.MENUPARENTID : Long.valueOf(parentId));
        menu.setUrl(url);
        menu.setSeq(seq);
        menu.setStatus(status);
        menu.setCreateDate(new Date());
        Message insert = menuService.insert(menu);
        return Result.message(insert);
    }

    @DeleteMapping("/delete/list")
    public Result deleteList(@RequestParam("list[]") List<Long> list){
        int i = menuService.deleteBatch(list);
        return i == -1 ? Result.error("删除失败") : Result.success("成功删除" + i + "条记录");
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
