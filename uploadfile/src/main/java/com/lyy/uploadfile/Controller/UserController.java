package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/account/{account}", method = RequestMethod.GET)
    public Result getUserByAccount(@PathVariable("account") String account) {
        UserUF userUF = userService.find(account);
        return userUF == null ? Result.error("该用户不存在") : Result.success("查询成功", userUF);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Result addNewUser(@Validated UserUF userUF, BindingResult bindingResult) {
        //后台数据校验
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                sb.append(objectError.getDefaultMessage() + '，');
            }
            String msg = sb.toString();
            return Result.error(msg.substring(0, msg.length()-1));
        }
        int res = userService.insert(userUF);

        return res == 0 ? Result.error("用户创建失败") : Result.success("用户创建成功");
    }
}
