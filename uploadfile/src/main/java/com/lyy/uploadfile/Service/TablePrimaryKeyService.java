package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.TablePrimaryKey;
import org.springframework.stereotype.Service;

public interface TablePrimaryKeyService {

    Long get(Class clazz);

    Long createId(Class clazz);

    Long nextId(TablePrimaryKey tablePrimaryKey);
}
