package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Mapper.UserMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;

    final TablePrimaryKeyService tablePrimaryKeyService;

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

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
            return Message.fail("不存在该账户");
        }
        return Message.success("查询成功", oneByAccount);
    }

    @Override
    public Message update(UserUF userUF) {
        UserUF one = userMapper.getOneByAccount(userUF.getAccount());
        if (one == null) {
            return Message.fail("用户不存在");
        }
        one.setTelephone(userUF.getTelephone());
        one.setName(userUF.getName());
        one.setSex(userUF.getSex());
        one.setAccount(userUF.getAccount());
        one.setEmail(userUF.getEmail());
        int update = userMapper.update(one);
        if (update <= 0) {
            return Message.fail("更新用户失败");
        }
        return Message.success("更新用户成功", userUF);
    }

    @Override
    public Message insert(UserUF userUF) {
        UserUF oneByAccount = userMapper.getOneByAccount(userUF.getAccount());
        if (oneByAccount != null) {
            return Message.fail("该账户已经存在");
        }
        int i = userMapper.countByEmail(userUF.getEmail());
        if (i != 0) {
            return Message.fail("该邮箱已被注册");
        }
        Long id = tablePrimaryKeyService.get(UserUF.class);
        userUF.setId(id);
        userUF.setCreateDate(new Date());
        userMapper.insert(userUF);
        return Message.success("用户创建成功", userUF);
    }

    @Override
    public Message countAccountAndEmail(String account, String email) {
        int n = userMapper.countAccountOrEmail(account, email);
        return n > 0 ? Message.success("成功", n): Message.fail("失败");
    }

    @Override
    public PageData getAllByPage(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<UserUF> allByList = userMapper.getAllByList(start, end);
        int n = userMapper.countByPage();
        return PageData.success(allByList, n);
    }

    @Override
    public Message search(String name, String account, String sex, String createDate, String telemail, int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<UserUF> search = userMapper.search(account, name, sex, createDate, telemail, start, end);
        int n = userMapper.searchCount(account, name, sex, telemail, createDate);
        return Message.Page.setMsg(search, n);
    }

    @Override
    public int deleteList(List<Long> idList) {

        return userMapper.deleteBatch(idList);
    }

    @Override
    public void delete(long id) {
        userMapper.delete(id);
    }

    @Override
    public Message setPwd(long id, String pwd) {
        int i = userMapper.updatePwd(pwd, id);
        return i == 1 ? Message.success("重设密码成功") : Message.fail("重置密码失败");
    }
}
