package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Mapper.MenuRoleMapper;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    TablePrimaryKeyService tablePrimaryKeyService;

    MenuRoleMapper menuRoleMapper;

    @Autowired
    public MenuRoleServiceImpl(TablePrimaryKeyService tablePrimaryKeyService,
                               MenuRoleMapper menuRoleMapper) {
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.menuRoleMapper = menuRoleMapper;
    }

    @Override
    public List<MenuRole> getAll(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        return menuRoleMapper.getByPage(start, end);
    }

    @Override
    public Message update(MenuRole menuRole) {
        int update = menuRoleMapper.update(menuRole);
        return update == 1 ? Message.success("更新成功") : Message.fail("更新失败");
    }

    @Override
    public Message delete(long id) {
        menuRoleMapper.delete(id);
        return Message.success("删除成功");
    }

    @Override
    public Message insert(MenuRole menuRole) {
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
}
