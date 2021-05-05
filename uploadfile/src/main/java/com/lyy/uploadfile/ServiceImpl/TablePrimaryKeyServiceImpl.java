package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;
import com.lyy.uploadfile.Entry.TablePrimaryKey;
import com.lyy.uploadfile.Mapper.TablePrimaryKeyMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class TablePrimaryKeyServiceImpl implements TablePrimaryKeyService {

    final TablePrimaryKeyMapper tablePrimaryKeyMapper;

    @Autowired
    public TablePrimaryKeyServiceImpl(TablePrimaryKeyMapper tablePrimaryKeyMapper) {
        this.tablePrimaryKeyMapper = tablePrimaryKeyMapper;
    }

    @Override
    public Long get(Class clazz) {
        String name = clazz.getName();
        TablePrimaryKey one = tablePrimaryKeyMapper.getOne(name);
        return one == null ? createId(clazz) : nextId(one);
    }

    @Override
    public Long createId(Class clazz) {
        //获取clazz的主键增长方式
        TablePrimaryKey.IncreaseStrategy strategy = null;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(PrimaryKey.class)) {
                PrimaryKey annotation = declaredField.getAnnotation(PrimaryKey.class);
                assert (strategy == null);//该注解在类中只能出现一次，否则将停止系统，就是想这么刚！！
                strategy = annotation.strategy();
            }
        }
        //创建记录
        assert (strategy != null);
        TablePrimaryKey one = new TablePrimaryKey();
        one.setClassReferenceName(clazz.getName());
        one.setCreateDate(new Date());
        one.setStrategy(strategy.getTypeNum());
        switch (strategy) {
            case AUTO:
                one.setCurrentTableID("1");
                break;
            case DATE:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String id = sdf.format(new Date()) + "0001";
                one.setCurrentTableID(id);
                break;
        }
        tablePrimaryKeyMapper.insert(one);
        return Long.valueOf(one.getCurrentTableID());
    }

    @Override
    public Long nextId(TablePrimaryKey tablePrimaryKey) {
        long newId = 0;
        String currId = tablePrimaryKey.getCurrentTableID();
        TablePrimaryKey.IncreaseStrategy strategy = tablePrimaryKey.getIncreaseStrategy();
        try {
            switch (strategy) {
                case AUTO:
                    newId = Long.valueOf(currId) + 1;
                    break;
                case DATE:
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date parse = sdf.parse(currId);
                    if (parse.before(new Date())) {
                        newId = Long.valueOf(sdf.format(new Date()) + "0001");
                    } else {
                        newId = Long.valueOf(currId) + 1;
                    }
            }
            tablePrimaryKeyMapper.update(tablePrimaryKey.getClassReferenceName(), String.valueOf(newId));   //更新表
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newId;
    }
}
