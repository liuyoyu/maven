package com.lyy.Service;

import com.lyy.Configer.GenerateCodeConfiger;
import com.lyy.Entity.ColumnEntity;
import com.lyy.Entity.FieldEntity;
import com.lyy.Entity.PrimaryKey;
import com.lyy.Entity.TableEntity;
import com.lyy.Util.JDBCUtil;
import com.lyy.Util.PropertiesUtil;
import com.lyy.Util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据库信息
 * author liuyongyu
 * date 2021/8/1
 */
public class DataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseService.class);

    protected static List<TableEntity> tableList;

    public DataBaseService() {
        /*获取表信息*/
        try {
            tableList = getTablesInfo(PropertiesUtil.get(GenerateCodeConfiger.DBCAT),
                    PropertiesUtil.get(GenerateCodeConfiger.DBSCHEM));
        } catch (SQLException e) {
            logger.error("===========获取表信息时出现异常==========", e);
        }
    }

    /**
     * 连接数据库并获取表格信息
     *
     * @param catalog
     * @param schema
     * @return
     * @throws SQLException
     */
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

    /**
     * 数据类型进行转换，数据库类型 -> Java类型
     *
     * @param columnEntities
     * @return
     */
    protected List<String> transformType(List<ColumnEntity> columnEntities) {
        List<String> list = new ArrayList<>();
        for (ColumnEntity entity : columnEntities) {
            list.add(transformType(entity));
        }
        return list;
    }

    /**
     * 数据类型进行转换，数据库类型 -> Java类型
     *
     * @param columnEntity
     * @return
     */
    protected String transformType(ColumnEntity columnEntity) {
        return JDBCUtil.tranformType(columnEntity.getType());
    }

    /**
     * 获取表，返回所有field信息
     *
     * @param columnEntities
     * @param qualifier
     * @return
     */
    protected List<FieldEntity> transformField(List<ColumnEntity> columnEntities, String qualifier) {
        List<FieldEntity> list = new ArrayList<>();
        for (ColumnEntity columnEntity : columnEntities) {
            list.add(transformField(columnEntity, qualifier));
        }
        return list;
    }

    /**
     * 转换表列，获取field信息
     *
     * @param columnEntity
     * @param qualifier    限定符类型 private | public | protected
     * @return
     */
    protected FieldEntity transformField(ColumnEntity columnEntity, String qualifier) {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setType(transformType(columnEntity));
        if (columnEntity.getName().contains("_")) {
            fieldEntity.setName(StringUtil.underScoreCaseToCamelCase(columnEntity.getName()));
        } else {
            fieldEntity.setName(columnEntity.getName().toLowerCase());
        }
        fieldEntity.setQualifier(qualifier);
        fieldEntity.setRemark(columnEntity.getRemark() == null ? "" : columnEntity.getRemark());
        fieldEntity.setUpperCaseHeader(StringUtil.upperCaseHeader(fieldEntity.getName()));
        return fieldEntity;
    }
}
