package main.java.com.lyy.uploadfile.Controller;

import main.java.com.lyy.uploadfile.Service.UserService;
import main.java.com.lyy.uploadfile.Configture.SystemBaseRoles;
import main.java.com.lyy.uploadfile.Entry.UserRole;
import main.java.com.lyy.uploadfile.Entry.UserUF;
import main.java.com.lyy.uploadfile.Service.UserRoleService;
import main.java.com.lyy.uploadfile.Utils.Message;
import main.java.com.lyy.uploadfile.Utils.PageData;
import main.java.com.lyy.uploadfile.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @RequestMapping(value = "/account/{account}", method = RequestMethod.GET)
    public Result getUserByAccount(@PathVariable("account") String account) {
        Message userUF = userService.find(account);
        return userUF.isSuccess()? Result.error(userUF.msg()) : Result.success(userUF.msg(), userUF.res());
    }

    @PostMapping(value = "/new")
    public Result addNewUser(UserUF userUF) {
        //后台数据校验
        userUF.setCreateDate(new Date());
        userUF.setDefaultPwd();
        Message insert = userService.insert(userUF);
        if (!insert.isSuccess()) {
            return Result.message(insert);
        }
        UserRole ur = new UserRole();
        ur.setCreateDate(new Date());
        ur.setRoleId(SystemBaseRoles.USER);
        ur.setAccount(userUF.getAccount());
        Message roleinsert = userRoleService.insert(ur);
        if (!roleinsert.isSuccess()) {
            return Result.success("新增用户成功，请为该用户添加角色");
        }
        return Result.success();
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") int page, @RequestParam("limit") int limit){
        return userService.getAllByPage(page, limit);
    }

    @GetMapping("/list/search")
    public Result search(@RequestParam("account") String account, @RequestParam("name") String name, @RequestParam("sex") String sex,
                         @RequestParam("telemail") String telemail, @RequestParam("createDate")String createDate, @RequestParam("page")int page, @RequestParam("limit")int limit) {
        Message search = userService.search(name, account, sex, createDate, telemail, page, limit);
        return PageData.message(search);
    }

    @DeleteMapping("/delete/list")
    public Result deletList(@RequestParam("list[]") List<Long> list) {
        int i = userService.deleteList(list);
        return i == -1 ? Result.error("删除失败") : Result.success("成功删除" + i + "条记录");
    }

    @DeleteMapping("/delete/this")
    public Result deleteThis(@RequestParam("id") long id) {
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result edit(@RequestParam("account")String account, @RequestParam("name")String name,
                       @RequestParam("sex") String sex, @RequestParam("telephone")String telephone, @RequestParam("email")String email) {
        UserUF userUF = new UserUF();
        userUF.setAccount(account);
        userUF.setEmail(email);
        userUF.setSex(sex);
        userUF.setName(name);
        userUF.setTelephone(telephone);
        Message update = userService.update(userUF);
        return Result.message(update);
    }

    @PostMapping("/pwd")
    public Result setPwd(@RequestParam("id") long id, @RequestParam("password") String password) {
        Message message = userService.setPwd(id, password);
        return Result.message(message);
    }

    @GetMapping("/role/list")
    public Result getUserRoleList(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Message allByPage = userRoleService.getAllByPage(page, limit);
        return PageData.message(allByPage);
    }

    @GetMapping("/role/list/search")
    public Result search(@RequestParam("account") String account,
                         @RequestParam("roleId") String roleId,
                         @RequestParam("status") String status,
                         @RequestParam("page")int page, @RequestParam("limit")int limit) {
        Message search = userRoleService.search(account, roleId, status, page, limit);
        return PageData.message(search);
    }

    @PostMapping("/role/new")
    public Result insertUserRole(@RequestParam("account") String account, @RequestParam("roleId") long roleId,
                                 @RequestParam("status") int status) {
        UserRole userRole = new UserRole();
        userRole.setAccount(account);
        userRole.setRoleId(roleId);
        userRole.setCreateDate(new Date());
        userRole.setStatus(status);
        Message insert = userRoleService.insert(userRole);
        return Result.message(insert);
    }

    @DeleteMapping("/role/delete/list")
    public Result deleteList(@RequestParam("list[]") List<Long> idList) {
        int n = userRoleService.deleteList(idList);
        return Result.success("成功删除"+n+"个用户");
    }

    @DeleteMapping("/role/delete/this")
    public Result deleteUserRoleThis(@RequestParam("id") long id) {
        Message delete = userRoleService.delete(id);
        return Result.message(delete);
    }

    @PostMapping("/role/edit")
    public Result userRoleEdit(@RequestParam("id") String id, @RequestParam("account") String account,
                               @RequestParam("roleId") String roleId, @RequestParam("status") int status) {
        UserRole userRole = new UserRole();
        userRole.setStatus(status);
        userRole.setRoleId(Long.valueOf(roleId));
        userRole.setId(Long.valueOf(id));
        userRole.setAccount(account);
        Message update = userRoleService.update(userRole);
        return Result.message(update);
    }
}
