package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface UserRoleService {

    Message insert(UserRole userRole);

    Message getAllByPage(int page, int limit);

    Message delete(long id);

    Message update(long id, long roleId);

    Message update(long id, int status);

    Message update(UserRole userRole);

    List<UserRoleDTO> getByAccount(String account);

    Message search(String account, String roleId, String status, int page, int limit);

    int deleteList(List<Long> id);

    void changeUsingRole(String account, long roleId);
}
