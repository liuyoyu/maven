package com.lyy.Util;

import com.lyy.Configer.GenerateCodeConfiger;
import com.lyy.Entity.ColumnEntity;
import com.lyy.Entity.PrimaryKey;
import com.lyy.Entity.TableEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

    private static final Logger logger = LoggerFactory.getLogger(JDBCUtil.class);

    public static Map<String, String> TYPEMAP = null;

    //配置文件
    private static HikariConfig hikariConfig;
    private static HikariDataSource hikariDataSource;

    static {
        logger.info("==========初始化数据库连接池配置==========");
        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(PropertiesUtil.get(GenerateCodeConfiger.JDBCURL));
        hikariConfig.setDriverClassName(PropertiesUtil.get(GenerateCodeConfiger.DRIVEN));
        hikariConfig.setUsername(PropertiesUtil.get(GenerateCodeConfiger.DBUSER));
        hikariConfig.setPassword(PropertiesUtil.get(GenerateCodeConfiger.DBPWD));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(hikariConfig);
        logger.info("==========初始化连接池配置完成==========");
    }

    /**
     * 获取数据库元数据
     * @return
     */
    public static DatabaseMetaData getMetaData(){
        logger.info("==========获取数据库元数据！==========");
        Connection connection = null;
        DatabaseMetaData metaData = null;
        try {
            connection = hikariDataSource.getConnection();
            metaData = connection.getMetaData();
        } catch (SQLException e) {
            logger.error("==========获取元数据失败==========", e);
            close(connection, null);
        }
        return metaData;
    }

    /**
     * 获取数据库所有表
     * @param catalog 数据库目录 可以为空
     * @param schema 数据库名 oracle是用户名
     * @return
     */
    public static List<TableEntity> getTableList(DatabaseMetaData metaData,String catalog, String schema) throws SQLException {
        assert metaData != null;
        assert schema != null && !"".equals(schema);
        ResultSet tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
        List<TableEntity> tableEntityList = new ArrayList<>();
        while (tables.next()) {
            TableEntity table = new TableEntity();
            table.setName(tables.getString("TABLE_NAME"));
            table.setRemark(tables.getString("REMARKS"));
            table.setSchema(tables.getString("TABLE_SCHEM"));
            table.setCatalog(tables.getString("TABLE_CAT"));
            table.setColumns(getColumnProperties(metaData, catalog, schema, table.getName()));
            table.setPk(getPrimaryKey(metaData, catalog, schema, table.getName(), table.getColumns()));
            tableEntityList.add(table);
        }
        return tableEntityList;
    }

    /**
     * 获取列中的数据
     * @param metaData
     * @param tableName
     * @throws SQLException
     */
    public static List<ColumnEntity> getColumnProperties(DatabaseMetaData metaData,String catalog, String schema, String tableName) throws SQLException {
        assert metaData != null;
        ResultSet table = metaData.getColumns(catalog, schema, tableName, "%");
        List<ColumnEntity> columnNames = new ArrayList<>();
        while (table.next()) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setName(table.getString("COLUMN_NAME"));
            columnEntity.setLength(table.getInt("COLUMN_SIZE"));
            columnEntity.setType(table.getString("TYPE_NAME"));
            columnEntity.setRemark(table.getString("REMARKS"));
            columnEntity.setNull(table.getBoolean("NULLABLE"));
            columnNames.add(columnEntity);
        }
        return columnNames;
    }

    /**
     *  获取主键信息
     * @param metaData
     * @param catalog
     * @param schema
     * @param tableName
     * @param columnEntities
     * @return
     * @throws SQLException
     */
    public static List<PrimaryKey> getPrimaryKey(DatabaseMetaData metaData, String catalog, String schema, String tableName, List<ColumnEntity> columnEntities) throws SQLException {
        assert metaData != null;
        Map<String, String> columnMap = new HashMap<>();
        for (ColumnEntity columnEntity : columnEntities) {
            columnMap.put(columnEntity.getName(), columnEntity.getType());//存放列名对应的数据类型
        }
        ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);
        List<PrimaryKey> primaryKeyList = new ArrayList<>();
        while (primaryKeys.next()) {
            PrimaryKey pk = new PrimaryKey();
            pk.setColumnsName(primaryKeys.getString("COLUMN_NAME"));
            pk.setName(primaryKeys.getString("PK_NAME"));
            pk.setKeySeq(primaryKeys.getString("KEY_SEQ"));
            pk.setFieldType(columnMap.get(pk.getColumnsName()));
            primaryKeyList.add(pk);
        }
        return primaryKeyList;
    }


    /**
     * 更新
     *
     * @param sql
     * @return
     */
    public static int UPDATE(String sql) {
        assert sql != null && !"".equals(sql);
        assert sql.indexOf("update ") == 0;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = hikariDataSource.getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, statement);
        }
        return 0;
    }

    /**
     * 删除、插入
     *
     * @param sql
     * @return
     */
    public static int excuteSQL(String sql) {
        assert sql != null && !"".equals(sql);
        assert sql.indexOf("delete ") == 0 || sql.indexOf("insert into ") == 0;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = hikariDataSource.getConnection();
            statement = connection.createStatement();
            boolean execute = statement.execute(sql);
            return execute ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, statement);
        }
        return 0;
    }

    /**
     * 查询
     * todo 条件查询、分页查询
     * @param sql
     * @param targetObj
     * @param <T>
     * @return
     */
    public static <T> List<T> SELECT(String sql, Class<T> targetObj) {
        assert targetObj != null;
        assert sql != null && sql.indexOf("select ") == 0;

        //获取targetObj属性
        List<String> paramNames = new ArrayList<>();
        List<String> paramTypes = new ArrayList<>();
        Field[] fields = targetObj.getDeclaredFields();
        for (Field field : fields) {
            paramNames.add(field.getName());
            String typeName = field.getAnnotatedType().getType().getTypeName();
            paramTypes.add(typeName);
        }
        //jdbc
        List<T> res = new ArrayList();
        Connection conn = null;
        Statement statement;
        try {
            conn = hikariDataSource.getConnection();
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                T t = targetObj.newInstance();
                Class<?> clazz = t.getClass();
                for (int i = 0; i < paramNames.size(); i++) {
                    Object val = getResultSetValue(resultSet, paramNames.get(i), paramTypes.get(i));
                    Field clazzField = clazz.getDeclaredField(paramNames.get(i));
                    clazzField.setAccessible(true);
                    clazzField.set(t, val);
                }
                res.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取resultSet中的值
     *
     * @param resultSet
     * @param name
     * @param type
     * @return
     * @throws SQLException
     */
    private static Object getResultSetValue(ResultSet resultSet, String name, String type) throws SQLException {
        assert resultSet != null;
        assert name != null && !"".equals(name);
        assert type != null && !"".equals(type);

        switch (type.toLowerCase()) {
            case "int":
                return resultSet.getInt(name);
            case "byte":
                return resultSet.getByte(name);
            case "short":
                return resultSet.getShort(name);
            case "double":
                return resultSet.getDouble(name);
            case "float":
                return resultSet.getFloat(name);
            case "bigdecimal":
                return resultSet.getBigDecimal(name);
            case "string":
                return resultSet.getString(name);
            case "date":
                return resultSet.getDate(name);
            case "timestamp":
                return resultSet.getTimestamp(name);
            case "boolean":
                return resultSet.getBoolean(name);
        }
        return null;
    }

    private static void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据库的数据类型与java类型转换
     * @param type
     * @return
     */
    public static String tranformType(String type) {
        if (TYPEMAP == null) {
            TYPEMAP = new HashMap<>();
            TYPEMAP.put("varchar2", "String");
            TYPEMAP.put("char", "String");
            TYPEMAP.put("text", "String");
            TYPEMAP.put("integer unsigned", "String");
            TYPEMAP.put("tintiny unsigned", "int");
            TYPEMAP.put("smallint unsigned", "int");
            TYPEMAP.put("mediumint unsigned", "int");
            TYPEMAP.put("boolean", "boolean");
            TYPEMAP.put("float", "float");
            TYPEMAP.put("double", "double");
            TYPEMAP.put("decimal", "java.math.BigDecimal");
            TYPEMAP.put("date", "java.util.Date");
            TYPEMAP.put("time", "java.sql.Time");
            TYPEMAP.put("dateTime", "java.sql.TimeStamp");
            TYPEMAP.put("timestamp", "java.sql.TimeStamp");
            TYPEMAP.put("id", "long");
            TYPEMAP.put("number", "java.math.BigDecimal");
        }
        String s = TYPEMAP.get(type.toLowerCase());
        if (s == null) {
            logger.warn("==========出现数据库数据类型与java类型无法转换的情况，请补充TYPEMAP==========");
            return "";
        }
        return s;
    }
}
