package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;

import java.util.List;

public interface UserService {

    Message find(Long id);

    Message update(UserUF userUF);

    Message insert(UserUF userUF);

    Message find(String account);

    Message countAccountAndEmail(String account, String email);

    PageData getAllByPage(int page, int limit);
}
