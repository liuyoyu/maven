package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Utils.Message;

public interface UserService {

    Message find(Long id);

    Message update(UserUF userUF);

    Message insert(UserUF userUF);

    Message find(String account);
}
