package main.java.com.lyy.uploadfile.Controller;

import main.java.com.lyy.uploadfile.Configture.SystemParameters;
import main.java.com.lyy.uploadfile.Entry.Menu;
import main.java.com.lyy.uploadfile.Entry.MenuRole;
import main.java.com.lyy.uploadfile.Service.LoginService;
import main.java.com.lyy.uploadfile.Service.MenuRoleService;
import main.java.com.lyy.uploadfile.Service.MenuService;
import main.java.com.lyy.uploadfile.Utils.PageData;
import main.java.com.lyy.uploadfile.Utils.Result;
import main.java.com.lyy.uploadfile.Service.RoleService;
import main.java.com.lyy.uploadfile.Utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    MenuService menuService;

    MenuRoleService menuRoleService;

    LoginService loginService;

    RoleService roleService;

    @Autowired
    public MenuController(MenuService menuService, MenuRoleService menuRoleService,
                          LoginService loginService, RoleService roleService) {
        this.menuService = menuService;
        this.menuRoleService = menuRoleService;
        this.loginService = loginService;
        this.roleService = roleService;
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

    @GetMapping("/all")
    public Result getAllMenu(){
        List<Menu> all = menuService.getAll();
        return Result.success("获取成功", all);
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
                             @RequestParam("seq") String seq,
                             @RequestParam("status") String status) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setParentId("".equals(parentId) ? SystemParameters.MENUPARENTID : Long.valueOf(parentId));
        menu.setUrl(url);
        menu.setSeq("".equals(seq) ? 0 : Double.valueOf(seq));
        menu.setStatus(Integer.valueOf(status));
        menu.setCreateDate(new Date());
        Message insert = menuService.insert(menu);
        return Result.message(insert);
    }

    @PostMapping("/edit")
    public Result edit(@RequestParam("id") String id,
                       @RequestParam("name") String name,
                       @RequestParam("parentId") String parentId,
                       @RequestParam("url") String url,
                       @RequestParam("seq") String seq,
                       @RequestParam("status") int status){
        Message one = menuService.getOne(Long.valueOf(id));
        if (!one.isSuccess()) {
            return Result.error("没找到要修改的菜单");
        }
        Menu menu = (Menu) one.res();
        menu.setName(name);
        menu.setParentId("".equals(parentId) ? SystemParameters.MENUPARENTID : Long.valueOf(parentId));
        menu.setUrl(url);
        menu.setSeq("".equals(seq) ? 0 : Double.valueOf(seq));
        menu.setStatus(status);
        Message update = menuService.update(menu);
        return Result.message(update);
    }

    @DeleteMapping("/delete/list")
    public Result deleteList(@RequestParam("list[]") List<Long> list){
        int i = menuService.deleteBatch(list);
        return i == -1 ? Result.error("删除失败") : Result.success("成功删除" + i + "条记录");
    }

    @DeleteMapping("/delete/this")
    public Result deleteThis(@RequestParam("id") long id) {
        Message delete = menuService.delete(id);
        return Result.message(delete);
    }

    @GetMapping("/role/list")
    public Result getAllMenuRole(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<MenuRole> all = menuRoleService.getAll(page, limit);
        int n = menuRoleService.countByPage();
        return PageData.success(all, n);
    }

    @GetMapping("/role/list/search")
    public Result searchUsrRoleList(@RequestParam("menuId") String menuId, @RequestParam("menuName") String menuName,
                                    @RequestParam("roleId") String roleId, @RequestParam("roleName") String roleName,
                                    @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        Message search = menuRoleService.search(menuId, menuName, roleId, roleName, status, page, limit);
        return PageData.message(search);
    }

    @RequestMapping("/role/new")
    public Result insertMenuRole(@RequestParam("roleId") long roleId, @RequestParam("menuId") Long menuId, @RequestParam("status") int status) {
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId(roleId);
        menuRole.setMenuId(menuId);
        menuRole.setStatus(status);
        menuRole.setCreateDate(new Date());
        Message insert = menuRoleService.insert(menuRole);
        return Result.message(insert);
    }

    @DeleteMapping("/role/delete/list")
    public Result deleteMenuRoleList(@RequestParam("list[]") List<Long> list){
        int i = menuRoleService.deleteList(list);
        return i == -1 ? Result.error("删除失败") : Result.success("成功删除" + i + "条记录");
    }

    @DeleteMapping("/role/delete/this")
    public Result deleteMenuRole(@RequestParam("id") long id) {
        Message delete = menuRoleService.delete(id);
        return Result.message(delete);
    }

    @PostMapping("/role/edit")
    public Result menuRoleEdit(@RequestParam("id") long id, @RequestParam("menuId") long menuId,
                               @RequestParam("roleId") long roleId, @RequestParam("status") int status) {
        MenuRole mr = new MenuRole();
        mr.setId(id);
        mr.setMenuId(menuId);
        mr.setRoleId(roleId);
        mr.setStatus(status);
        Message update = menuRoleService.update(mr);
        return Result.message(update);
    }
}
