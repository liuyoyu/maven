package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/account/{account}", method = RequestMethod.GET)
    public Result getUserByAccount(@PathVariable("account") String account) {
        Message userUF = userService.find(account);
        return userUF.isSuccess()? Result.error(userUF.msg()) : Result.success(userUF.msg(), userUF.res());
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
        Message res = userService.insert(userUF);

        return res.isSuccess() ? Result.error(res.msg()) : Result.success(res.msg());
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") int page, @RequestParam("limit") int limit){
        return userService.getAllByPage(page, limit);
    }

//    @GetMapping("/search")
//    public Result search(@RequestParam(""))
}
