package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface MenuRoleService {

    List<MenuRole> getAll(int page, int limt);

    Message update(MenuRole menuRole);

    int countByPage();

    Message delete(long id);

    Message insert(MenuRole menuRole);
}