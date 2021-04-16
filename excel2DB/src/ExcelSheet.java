import java.util.ArrayList;
import java.util.List;

/**
 * @Author liuyoyu
 * @Date:2019/8/31 10:45
 **/

public class ExcelSheet {
    private List<List<String>> data;
    private List<String> header;
    private Integer rows;
    private Integer cols;
    private String name;

    public ExcelSheet() {
    }

    public ExcelSheet(List<List<String>> data, List<String> header, Integer rows, Integer col,String name) {
        this.data = data;
        this.header = header;
        this.rows = rows;
        this.cols = col;
        this.name = name;
    }

    public ExcelSheet(List<List<String>> data, Integer rows, Integer cols, String name) {
        this(data, null, rows, cols, name);
    }

    public List<String> getDataByColName(String colName){
        if ("".equals(colName)) {
            return new ArrayList<String>();
        }
        int count = 0;
        for (String title : header) {
            if (colName.equals(title)) {
                break;
            }
            count++;
        }
        List<String> colData = new ArrayList<String>();
        for (int i=0; i<data.size(); i++) {
            List<String> row = this.data.get(i);
            for (int j=0; j<row.size(); j++) {
                if (j != count) {
                    continue;
                }
               colData.add(row.get(j));
            }
        }
        return colData;
    }




    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer col) {
        this.cols = col;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}