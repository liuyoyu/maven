package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.Menu;
import main.java.com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface MenuService {

    List<Menu> getAllByPage(int page, int limit);

    Message insert(Menu menu);

    Message update(Menu menu);

    Message delete(long id);

    int countAllByPage();

    Message getOne(long menuId);

    int checkUrlNum(String url);

    Message search(String id, String parentId, String name, String status, String date, String url, int start, int end);

    List<Menu> getAll();

    List<Menu> getIdAndName();

    List<Menu> getByParentId(long id);

    int deleteBatch(List<Long> idList);
}
