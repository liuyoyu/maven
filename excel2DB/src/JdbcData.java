import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liuyoyu
 * @Date:2019/8/31 16:00
 **/

public class JdbcData {
    private final static String url = "jdbc:sqlserver://; DatabaseName=;";
    private final static String user = "";
    private final static String pwd = "";
    private final static String driveName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public JdbcData() {
    }

    /**
     * ��ѯ
     * @param sql
     * @return
     */
    public static List<Map<String, String>> Get(String sql) {

        ResultSet resultSet = runSQL(sql);

        List<Map<String, String>> resultSetMap = new ArrayList<Map<String, String>>();
        List<String> columnNames = new ArrayList<String>();
        try {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (String columnName : columnNames) {
                    map.put(columnName, resultSet.getString(columnName));
                }
                resultSetMap.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetMap;
    }

    /**
     *��ѯ
     * @param sql
     * @param clazz ��ѯ���ת������POJO
     * @return
     */
    public static List<Object> Get(String sql, Class clazz) {
        List<Object> resultSet = new ArrayList<Object>();
        List<Map<String, String>> data = Get(sql);
        for (Map<String, String> map : data) {
            try {
                Object t = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int i=0; i<fields.length; i++) {
                    fields[i].setAccessible(true);
                    String name = fields[i].getName();
                    if (map.containsKey(name)) {
                        fields[i].set(t, map.get(name));
                    } else {
                        fields[i].set(t, "");
                    }
                }
                resultSet.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    /**
     * ��ȡ��¼����
     * @param sql
     * @return
     */
    public static Integer Count(String sql) {
        ResultSet resultSet = runSQL(sql);

        Integer unique = 0;

        try {
            resultSet.last();
            unique = resultSet.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unique;
    }

    /**
     * ִ���޸ġ�������ɾ������
     * @param sql
     */
    public static void execute(String sql){
        System.out.println("===============");
        System.out.println("�������peach��");
        System.out.println("===============");
        return ;
    }

    /***
     * �������ݿ�
     * @param sql
     * @return
     */
    private static ResultSet runSQL(String sql) {
        Connection conn;
        Statement statement;
        ResultSet resultSet = null;
        try {
            Class.forName(driveName);
            conn = DriverManager.getConnection(url, user, pwd);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sql);
        } catch (ClassNotFoundException e) {
            System.out.println("SQL�����Ҳ�����");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("�޷�ִ��SQL��䣡");
            e.printStackTrace();
        }
        return resultSet;
    }
}