package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.RoleUF;
import com.lyy.uploadfile.Service.RoleService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    RoleService roleService;

    UserService userService;

    @Autowired
    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public Result getAll(){
        List<RoleUF> all = roleService.getAll();
        return Result.success("获取成功", all);
    }
}
