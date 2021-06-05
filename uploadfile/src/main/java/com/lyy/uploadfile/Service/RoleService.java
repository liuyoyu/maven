package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.RoleUF;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface RoleService {

    List<RoleUF> getAll(int page, int limit);

    Message deleteRole(long roleId);

    Message insert(RoleUF roleUF);

    Message changeStatus(long id, RoleUF.STATUS status);

    List<RoleUF> getAll();

    Message getOne(Long id);
}
