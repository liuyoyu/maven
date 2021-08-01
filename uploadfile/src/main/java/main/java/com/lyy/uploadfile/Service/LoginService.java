package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import main.java.com.lyy.uploadfile.Entry.UserUF;
import main.java.com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface LoginService {

    Message login(String account, String password);

    UserUF getLoginInfo();

    List<UserRoleDTO> getLoginInfoDetail();
}
