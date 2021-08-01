package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.MenuRole;
import main.java.com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface MenuRoleService {

    List<MenuRole> getAll(int page, int limt);

    Message update(MenuRole menuRole);

    int countByPage();

    Message delete(long id);

    Message insert(MenuRole menuRole);

    List<MenuRole> getByRole(long id);

    Message search(String menuId, String menuName, String roleId, String roleName, String status, int page, int limit);

    int deleteList(List<Long> idList);
}
