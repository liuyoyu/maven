package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Utils.Message;

public interface LoginService {

    Message login(String account, String password);

    UserUF getLoginInfo();

    UserRoleDTO getLoginInfoDetail();
}
