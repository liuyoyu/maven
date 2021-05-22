package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface MenuService {

    List<Menu> getAllByPage(int page, int limit);

    Message insert(Menu menu);

    Message update(Menu menu);

    Message delete(long id);
}
