package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.TablePrimaryKey;

public interface TablePrimaryKeyService {

    Long get(Class clazz);

    Long createId(Class clazz);

    Long nextId(TablePrimaryKey tablePrimaryKey);
}
