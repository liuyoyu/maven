package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Mapper.UserMapper;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Utils.HttpUtil;
import com.lyy.uploadfile.Utils.JWTUtil;
import com.lyy.uploadfile.Utils.Message;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

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

    /**
     * 返回登录的用户名和用户账号
     * @return
     */
    @Override
    public UserUF getLoginInfo() {
        Cookie cookieByName = HttpUtil.getCookieByName(SystemParameters.COOKIE_USR_INFORMATION);
        String value = cookieByName.getValue();
        if (!JWTUtil.verify(value)) {
            return null;
        }
        String userInfo = JWTUtil.getTokenStr(value);
        //获取到用户信息
        JSONObject jsonObject = new JSONObject(userInfo);
        String name = (String)jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_2);
        String account = (String) jsonObject.get(SystemParameters.JWT_VERIDATION_NAME_1);
        return new UserUF(name, account);
    }
}
