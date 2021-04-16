/**
 * @Author liuyoyu
 * @Date:2019/8/31 9:46
 **/

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ����Excel�࣬���������ȡ��������
 */
public class ExcelData {
    protected String filePath;
    protected List<ExcelSheet> data = new ArrayList<ExcelSheet>();
    protected Integer sheets;
    protected Boolean existsHeader = false;

    public ExcelData() {}

    public ExcelData(String filePath, List<ExcelSheet> data, Integer sheets, Boolean existsHeader) {
        this.filePath = filePath;
        this.data = data;
        this.sheets = sheets;
        this.existsHeader = existsHeader;
    }

    public ExcelData(String filePath, Integer sheetNumber) {
        this(filePath, new ArrayList<ExcelSheet>(), sheetNumber, false);
    }

    public ExcelData(String filePath,Boolean header) {
        this(filePath,new ArrayList<ExcelSheet>(),1,header);
    }

    public ExcelData(String filePath) {
        this(filePath, new ArrayList<ExcelSheet>(), 1, false);
    }

    /**
     * ��ȡExcel�ļ����Զ��ж�xls��xlsx����
     */
    public void readExcel(){
        try {
            File file = new File(filePath);
            Workbook wb;
            String fileName = file.getName();
            if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(file);
            } else if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(new FileInputStream(file));
            } else {
                System.out.println("�ļ���ʽ����xls��xlsx");
                return;
            }
            System.out.println("��ʼ��������");
            this.sheets = wb.getNumberOfSheets();
            for (int i=0; i<this.sheets; i++) {
                List<List<String>> sheetData = new ArrayList<List<String>>();
                Sheet sheet = wb.getSheetAt(i);
                Integer rows = sheet.getLastRowNum() - sheet.getFirstRowNum()+1;
                List<String> headers = new ArrayList<String>();
                if (existsHeader) {
                    rows--;
                }
                System.out.println(sheet.getSheetName()+"��"+ rows +"������");
                Integer cols = 0;
                for (int j=sheet.getFirstRowNum(); j<=sheet.getLastRowNum();j++) {
                    List<String> rowData = new ArrayList<String>();
                    Row row = sheet.getRow(j);
                    cols = row.getLastCellNum() - row.getFirstCellNum() > cols ? row.getLastCellNum() - row.getFirstCellNum() : cols; //�����������
                    for (int k=row.getFirstCellNum(); k<row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (existsHeader && j == sheet.getFirstRowNum()) {
                            headers.add(cell.toString());
                        } else {
                            rowData.add(cell.toString());
                        }
                    }
                    if (existsHeader && j == sheet.getFirstRowNum()) {
                        continue;
                    }
                    sheetData.add(rowData);
                }
                data.add(new ExcelSheet(sheetData, headers,rows, cols, sheet.getSheetName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * д��excel
     * @param data
     */
    public static void writeExcel(List<Map<String, String>> data, String filePath) {
        OutputStream out = null;
        if ("".equals(filePath)) {
            System.out.println("�������ļ�·����");
            return ;
        }
        try {
            // ��ȡExcel�ĵ�
            File finalXlsxFile = new File(filePath);
            Workbook wb = null;
            Sheet sheet = null;
            if (!finalXlsxFile.exists()) {
                //�ļ������ڣ�����һ��
                if (finalXlsxFile.getName().endsWith("xls")) {
                    wb = new HSSFWorkbook();
                } else if (finalXlsxFile.getName().endsWith("xlsx")) {
                    wb = new XSSFWorkbook();
                } else {
                    System.out.println("�ļ���ʽ����xls��xlsx");
                    return;
                }
                sheet = wb.createSheet();
                out =  new FileOutputStream(filePath);
                wb.write(out);
                System.out.println("����excel�ɹ�");
            } else {
                //�ļ����ڣ�ɾ������
                FileInputStream in = new FileInputStream(filePath);
                if(finalXlsxFile.getName().endsWith("xls")){     //Excel&nbsp;2003
                    wb = new HSSFWorkbook(in);
                }else if(finalXlsxFile.getName().endsWith("xlsx")){    // Excel 2007/2010
                    wb = new XSSFWorkbook(in);
                }
                sheet = wb.getSheetAt(0);
                int rowNumber = sheet.getLastRowNum();
                System.out.println("�ļ��д������ݣ���������" +(rowNumber+1));
                for (int i = 0; i <= rowNumber; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    sheet.removeRow(row);
                }
                // �����ļ��������������ӱ����������У���������sheet�������κβ�����������Ч
                out =  new FileOutputStream(filePath);
                wb.write(out);
                System.out.println("ɾ������");
            }
            //д������
            if (data.size() < 1 || data.isEmpty()) {
                System.out.println("����Ϊ�գ�");
                return;
            }
            Row header = sheet.createRow(0);//������ͷ
            int col = 0;
            for (String key : data.get(0).keySet()) {
                Cell cell = header.createCell(col++);
                cell.setCellValue(key);
            }
            System.out.println("��ʼ��������");
            for (int j = 1; j < data.size(); j++) {
                System.out.println("���ȣ�"+ Double.valueOf(j)*100/data.size()+" %");
                Row row = sheet.createRow(j);
                // �õ�Ҫ�����ÿһ����¼
                col = 0;
                Map<String,String> dataMap = data.get(j);
                for (String key : dataMap.keySet()) {
                    Cell cell = row.createCell(col++);
                    cell.setCellValue(dataMap.get(key));
                }
            }
            // �����ļ��������׼��������ӱ����������У���������sheet�������κβ�����������Ч
            out =  new FileOutputStream(filePath);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("���ݵ����ɹ�");
    }



    public List<ExcelSheet> getData() {
        return data;
    }

    public void setData(List<ExcelSheet> data) {
        this.data = data;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getSheets() {
        return sheets;
    }

    public void setSheets(Integer sheets) {
        this.sheets = sheets;
    }
}