package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Mapper.UserMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;

    final TablePrimaryKeyService tablePrimaryKeyService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, TablePrimaryKeyService tablePrimaryKeyService) {
        this.userMapper = userMapper;
        this.tablePrimaryKeyService = tablePrimaryKeyService;
    }

    @Override
    public Message find(Long id) {
        UserUF one = userMapper.getOne(id);
        if (one == null) {
            return Message.fail("不存在该ID", null);
        }
        return Message.success("查询成功", one);
    }

    @Override
    public Message find(String account) {
        UserUF oneByAccount = userMapper.getOneByAccount(account);
        if (oneByAccount == null) {
            return Message.fail("不存在该账户", null);
        }
        return Message.success("查询成功", oneByAccount);
    }

    @Override
    public Message update(UserUF userUF) {
        int update = userMapper.update(userUF);
        if (update <= 0) {
            return Message.fail("更新用户失败", null);
        }
        return Message.success("更新用户成功", userUF);
    }

    @Override
    public Message insert(UserUF userUF) {
        UserUF oneByAccount = userMapper.getOneByAccount(userUF.getAccount());
        if (oneByAccount != null) {
            return Message.fail("该账户已经存在", null);
        }
        Long id = tablePrimaryKeyService.get(UserUF.class);
        userUF.setId(id);
        userUF.setCreateDate(new Date());
        int insert = userMapper.insert(userUF);
        return Message.success("用户创建成功", userUF);
    }
}
