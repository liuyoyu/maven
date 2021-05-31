package com.lyy.uploadfile;

import com.lyy.uploadfile.Configture.SystemBaseRoles;
import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import com.lyy.uploadfile.Mapper.MenuMapper;
import com.lyy.uploadfile.Service.MenuRoleService;
import com.lyy.uploadfile.Service.MenuService;
import com.lyy.uploadfile.Utils.LocalCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class UploadfileApplicationTests {

    @Test
    void contextLoads() {
        try {
            LocalCache.set("test", "捧沙卡拉卡", 3000);
            System.out.println(LocalCache.get("test"));
            Thread.currentThread().sleep(3000);
            System.out.println("再次取出："+LocalCache.get("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    MenuService menuService;
    @Autowired
    MenuRoleService menuRoleService;
    @Test
    void menuAdd(){
        Menu menu = new Menu();
        menu.setName("菜单列表");
        menu.setCreateDate(new Date());
        menu.setParentId(3);
        menu.setSeq(0);
        menu.setStatus(Menu.STATUS.USING.value());
        menuService.insert(menu);
        menu = new Menu();
        menu.setName("菜单分配");
        menu.setCreateDate(new Date());
        menu.setParentId(3);
        menu.setSeq(0);
        menu.setStatus(Menu.STATUS.USING.value());
        menuService.insert(menu);
    }
    @Test
    void menuRoleAdd(){
        MenuRole menuRole = new MenuRole();
        menuRole.setCreateDate(new Date());
        menuRole.setRoleId(SystemBaseRoles.USER);
        menuRole.setMenuId(6);
        menuRole.setStatus(MenuRole.STATUS.USING.value());
        menuRoleService.insert(menuRole);
    }

    @Autowired
    MenuMapper menuMapper;
    @Test
    void menuTest(){
        List<Menu> search = menuMapper.search("", "", "", "0", "","", 0,  10);
        System.out.println(search.size());
    }
}
