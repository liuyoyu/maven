package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Mapper.MenuMapper;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    MenuMapper menuMapper;

    TablePrimaryKeyService tablePrimaryKeyService;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, TablePrimaryKeyService tablePrimaryKeyService) {
        this.menuMapper = menuMapper;
        this.tablePrimaryKeyService = tablePrimaryKeyService;
    }

    @Override
    public List<Menu> getAllByPage(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<Menu> list = menuMapper.getAllbyPage(start, end);
        return list;
    }

    @Override
    public Message insert(Menu menu) {
        Long id = tablePrimaryKeyService.get(Menu.class);
        menu.setId(id);
        int insert = menuMapper.insert(menu);
        return insert == 1 ? Message.success("新增菜单成功") : Message.fail("新增菜单失败");
    }

    @Override
    public Message update(Menu menu) {
        int update = menuMapper.update(menu);
        return update == 1 ? Message.success("更新菜单成功") : Message.fail("更新菜单失败");
    }

    @Override
    public Message delete(long id) {
        menuMapper.delete(id);
        return Message.success("成功");
    }

    @Override
    public int countAllByPage() {
        return menuMapper.countAllByPage();
    }
}
