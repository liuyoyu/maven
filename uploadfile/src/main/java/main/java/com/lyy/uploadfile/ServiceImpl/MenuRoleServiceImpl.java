package main.java.com.lyy.uploadfile.ServiceImpl;

import main.java.com.lyy.uploadfile.Entry.MenuRole;
import main.java.com.lyy.uploadfile.Mapper.MenuRoleMapper;
import main.java.com.lyy.uploadfile.Service.TablePrimaryKeyService;
import main.java.com.lyy.uploadfile.Service.MenuRoleService;
import main.java.com.lyy.uploadfile.Service.MenuService;
import main.java.com.lyy.uploadfile.Service.RoleService;
import main.java.com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    TablePrimaryKeyService tablePrimaryKeyService;

    MenuRoleMapper menuRoleMapper;

    RoleService roleService;

    MenuService menuService;

    @Autowired
    public MenuRoleServiceImpl(TablePrimaryKeyService tablePrimaryKeyService,
                               MenuRoleMapper menuRoleMapper, RoleService roleService, MenuService menuService) {
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.menuRoleMapper = menuRoleMapper;
        this.roleService =roleService;
        this.menuService = menuService;
    }

    @Override
    public List<MenuRole> getAll(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        return menuRoleMapper.getByPage(start, end);
    }

    @Override
    public Message update(MenuRole menuRole) {
        MenuRole byId = menuRoleMapper.getById(menuRole.getId());
        if (byId == null) {
            return Message.fail("未找到菜单");
        }
        byId.setRoleId(menuRole.getRoleId());
        byId.setMenuId(menuRole.getMenuId());
        byId.setStatus(menuRole.getStatus());
        int update = menuRoleMapper.update(byId);
        return update == 1 ? Message.success("更新成功") : Message.fail("更新失败");
    }

    @Override
    public Message delete(long id) {
        menuRoleMapper.delete(id);
        return Message.success("删除成功");
    }

    @Override
    public Message insert(MenuRole menuRole) {
        if (!roleService.getOne(menuRole.getRoleId()).isSuccess()) {
            return Message.fail("新增失败：角色不存在");
        }
        if (!menuService.getOne(menuRole.getMenuId()).isSuccess()) {
            return Message.fail("新增失败：菜单不存在");
        }
        if (menuRoleMapper.insertCheck(menuRole.getRoleId(), menuRole.getMenuId()) > 0) {
            return Message.fail("该角色已经拥有访问该菜单权限");
        }
        Long id = tablePrimaryKeyService.get(MenuRole.class);
        menuRole.setId(id);
        int insert = menuRoleMapper.insert(menuRole);
        return insert == 1 ? Message.success("新增成功") : Message.fail("新增失败");
    }

    @Override
    public int countByPage() {
        return menuRoleMapper.countByPage();
    }

    @Override
    public List<MenuRole> getByRole(long id) {
        return menuRoleMapper.getByRoleId(id);
    }

    @Override
    public Message search(String menuId, String menuName, String roleId, String roleName, String status, int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<MenuRole> search = menuRoleMapper.search(menuId, menuName, roleId, roleName, status, start, end);
        int i = menuRoleMapper.searchCount(menuId, menuName, roleId, roleName, status);
        return Message.Page.setMsg(search, i);
    }

    @Override
    public int deleteList(List<Long> idList) {
        return menuRoleMapper.deleteBatch(idList);
    }
}
