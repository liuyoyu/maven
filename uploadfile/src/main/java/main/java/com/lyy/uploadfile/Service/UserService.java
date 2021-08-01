package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.UserUF;
import main.java.com.lyy.uploadfile.Utils.Message;
import main.java.com.lyy.uploadfile.Utils.PageData;

import java.util.List;

public interface UserService {

    Message find(Long id);

    Message update(UserUF userUF);

    Message insert(UserUF userUF);

    Message find(String account);

    Message countAccountAndEmail(String account, String email);

    PageData getAllByPage(int page, int limit);

    List<UserUF> getAllAccountAndName();

    Message search(String name, String account, String sex, String createDate, String telemail, int page, int limit);

    int deleteList(List<Long> idList);

    void delete(long id);

    Message setPwd(long id, String pwd);
}
