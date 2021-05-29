package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;

import java.util.Date;
import java.util.List;

public interface MenuService {

    List<Menu> getAllByPage(int page, int limit);

    Message insert(Menu menu);

    Message update(Menu menu);

    Message delete(long id);

    int countAllByPage();

    Message getOne(long menuId);

    int checkUrlNum(String url);

    PageData search(long id, long parentId, String name, int status, Date date, String url, int start, int end);
}
