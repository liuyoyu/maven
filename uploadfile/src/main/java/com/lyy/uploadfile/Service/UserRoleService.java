package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface UserRoleService {

    Message insert(UserRole userRole);

    List<UserRoleDTO> getAllByPage(int page, int limit);

    Message delete(long id);

    Message update(long id, long roleId);

    Message getByAccount(String account);
}
