import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author liuyoyu
 * @Date:2019/8/31 14:09
 **/

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String readExcelPath = "C:\\Users\\lyy\\Desktop\\��ʱ\\decisionTable\\input.xlsx";
        final String writeExcelPath = "C:\\Users\\lyy\\Desktop\\��ʱ\\decisionTable\\input_3.xlsx";
        ExcelData data = new ExcelData(readExcelPath, true);//true��ʾexcel���б�ͷ
        data.readExcel();
        List<String> names = data.getData().get(0).getDataByColName("����");
        String nameSet = "";
        for (String name : names) {
            nameSet += "'"+name+"',";
        }
        nameSet = nameSet.substring(0, nameSet.length()-1);
        String sql = "select name,sno,collegeID from p_student_info where name in ("+nameSet+")";
        System.out.println("��ʼ��ѯ");
        List<Map<String, String>> sqlData = JdbcData.Get(sql);
        ExcelData.writeExcel(sqlData,writeExcelPath);
        System.out.println("���");

    }
}