package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Mapper.UserMapper;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;
    @Override
    public Message login(String account, String password) {
        UserUF user = userMapper.findByAccountAndPassword(account, password);
        if (user == null) {
            user = userMapper.getOneByAccount(account);
        }else{
            return Message.success("登录成功", user);
        }
        return user == null ? Message.fail("输入账号不存在") : Message.fail("请检查输入密码");
    }
}
