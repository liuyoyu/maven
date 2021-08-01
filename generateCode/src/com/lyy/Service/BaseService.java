package com.lyy.Service;

import com.lyy.Configer.GenerateCodeConfiger;
import com.lyy.Entity.ColumnEntity;
import com.lyy.Entity.FieldEntity;
import com.lyy.Entity.TableEntity;
import com.lyy.Util.JDBCUtil;
import com.lyy.Util.PropertiesUtil;
import com.lyy.Util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * author liuyongyu
 * date 2021/8/1
 */
public class BaseService {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected static List<TableEntity> tableList;

    protected List<TableEntity> getTablesInfo(String catalog, String schema) throws SQLException {
        logger.info("==========获取数据库中表信息==========");
        DatabaseMetaData metaData = JDBCUtil.getMetaData();
        logger.info("=====当前用户:{}", metaData.getUserName());
        logger.info("==========开始加载表信息==========");
        List<TableEntity> tableList = JDBCUtil.getTableList(metaData, catalog, schema);
        logger.info("==========加载完成==========");
        logger.info("=====一共{}张表", tableList.size());
        return tableList;
    }

    protected List<String> transformType(List<ColumnEntity> columnEntities) {
        List<String> list = new ArrayList<>();
        for (ColumnEntity entity : columnEntities) {
            list.add(transformType(entity));
        }
        return list;
    }

    protected String transformType(ColumnEntity columnEntity) {
        return JDBCUtil.tranformType(columnEntity.getType());
    }

    protected List<FieldEntity> transformField(List<ColumnEntity> columnEntities, String qualifier) {
        List<FieldEntity> list = new ArrayList<>();
        for (ColumnEntity columnEntity : columnEntities) {
            list.add(transformField(columnEntity, qualifier));
        }
        return list;
    }

    /**
     * 转换表列，获取field信息
     * @param columnEntity
     * @param qualifier  限定符类型 private | public | protected
     * @return
     */
    protected FieldEntity transformField(ColumnEntity columnEntity, String qualifier) {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setType(transformType(columnEntity));
        fieldEntity.setName(columnEntity.getName().toLowerCase());
        fieldEntity.setQualifier(qualifier);
        fieldEntity.setRemark(columnEntity.getRemark() == null ? "" : columnEntity.getRemark());
        fieldEntity.setUpperCaseHeader(StringUtil.upperCaseHeader(fieldEntity.getName()));
        return fieldEntity;
    }

    /**
     * 写入文件
     * @param filePath 文件名称
     * @return
     */
    protected Writer getWriter(String filePath) {
        FileWriter fileWriter = null;
        File file = null;
        logger.info("输出文件：{}", filePath);
        try {
            file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, false);
        } catch (IOException e) {
            logger.error("==========设置输出时出现异常==========");
            e.printStackTrace();
        }
        return fileWriter;
    }
}
