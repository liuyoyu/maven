package main.java.com.lyy.uploadfile.ServiceImpl;

import main.java.com.lyy.uploadfile.Configture.Interface.PrimaryKey;
import main.java.com.lyy.uploadfile.Service.TablePrimaryKeyService;
import main.java.com.lyy.uploadfile.Entry.TablePrimaryKey;
import main.java.com.lyy.uploadfile.Mapper.TablePrimaryKeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class TablePrimaryKeyServiceImpl implements TablePrimaryKeyService {

    private static final Logger logger = LoggerFactory.getLogger(TablePrimaryKeyServiceImpl.class);

    final TablePrimaryKeyMapper tablePrimaryKeyMapper;

    private final ReentrantLock lock;

    @Autowired
    public TablePrimaryKeyServiceImpl(TablePrimaryKeyMapper tablePrimaryKeyMapper) {
        this.lock = new ReentrantLock();
        this.tablePrimaryKeyMapper = tablePrimaryKeyMapper;
    }

    /**
     * 主键增长时加锁，防止多个线程调用出现主键冲突
     * @param clazz
     * @return
     */
    @Override
    public Long get(Class clazz) {
        lock.lock();
        try {
            String name = clazz.getName();
            TablePrimaryKey one = tablePrimaryKeyMapper.getOne(name);
            return one == null ? createId(clazz) : nextId(one);
        }finally {
            lock.unlock();
        }
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
            int res = tablePrimaryKeyMapper.update(String.valueOf(newId), tablePrimaryKey.getClassReferenceName());   //更新表
            if (res == 0) {
                logger.error("表主键更新异常，DTO类：{}", tablePrimaryKey.getClassReferenceName());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newId;
    }
}
