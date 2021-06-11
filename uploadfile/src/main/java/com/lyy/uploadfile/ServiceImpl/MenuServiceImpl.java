package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Mapper.MenuMapper;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public Message getOne(long menuId) {
        Menu one = menuMapper.getOne(menuId);
        return one == null ? Message.fail("获取菜单失败") : Message.success("获取菜单成功", one);
    }

    @Override
    public int checkUrlNum(String url) {
        return menuMapper.checkUrlDelipute(url);
    }

    @Override
    public Message search(String id, String parentId, String name, String status, String date, String url, int start, int end) {

        List<Menu> search = menuMapper.search(id, parentId, name, status, date, url, start, end);
        int co = menuMapper.searchCount(id, parentId, name, status, date, url);
        return Message.Page.setMsg(search, co);
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> all = menuMapper.getAll();
        return all;
    }

    @Override
    public List<Menu> getIdAndName() {
        return menuMapper.getIdAndMenu();
    }

    @Override
    public List<Menu> getByParentId(long parentId) {
        return menuMapper.getParentMenu(parentId);
    }

    @Override
    public int deleteBatch(List<Long> idList) {
        return menuMapper.deleteBatch(idList);
    }

}
