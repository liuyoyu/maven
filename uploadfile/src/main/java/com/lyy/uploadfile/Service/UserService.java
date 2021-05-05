package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.UserUF;

public interface UserService {

    UserUF find(Long id);

    int update(UserUF userUF);

    int insert(UserUF userUF);

    UserUF find(String account);
}
