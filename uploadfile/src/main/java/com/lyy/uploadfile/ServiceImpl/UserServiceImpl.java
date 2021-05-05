package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Mapper.UserMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserService;
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
    public UserUF find(Long id) {
        return userMapper.getOne(id);
    }

    @Override
    public UserUF find(String account) {
        return userMapper.getOneByAccount(account);
    }

    @Override
    public int update(UserUF userUF) {
        return userMapper.update(userUF);
    }

    @Override
    public int insert(UserUF userUF) {
        Long id = tablePrimaryKeyService.get(UserUF.class);
        userUF.setId(id);
        userUF.setCreateDate(new Date());
        return userMapper.insert(userUF);
    }
}
