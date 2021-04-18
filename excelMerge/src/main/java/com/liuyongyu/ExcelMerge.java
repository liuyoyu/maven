package com.liuyongyu;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelMerge {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     *  获取workbook类型
     * @param in
     * @param file
     * @return
     * @throws IOException
     */
    private static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if(file.getName().endsWith(EXCEL_XLS)){	 //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){	// Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


    /**
     * 检查文件是否存在，是否为Excel文件
     * @param file
     * @throws Exception
     */
    private static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 合并两个文件，并输出
     * @param fileName1
     * @param fileName2
     * @param output
     * @param colNo 索引列，从0开始
     * @throws Exception
     */
    public static void mergeAndOutput(String fileName1, String fileName2, String output, int... colNo) throws Exception {
        Workbook wb1 = readFile(fileName1);
        Workbook wb2 = readFile(fileName2);
        Workbook workbook = mergeWorkBook(wb1, wb2, colNo);
        //文档输出
        String name = fileName1.substring(fileName1.lastIndexOf('/')+1, fileName1.lastIndexOf('.')) + "_" +
                fileName2.substring(fileName2.lastIndexOf('/')+1, fileName2.lastIndexOf('.'));
        FileOutputStream out = new FileOutputStream(output + "/" + name + ".xlsx");
        workbook.write(out);
        out.close();
    }

    /**
     * 读取文件，返回workbook
     * @param name
     * @return
     * @throws Exception
     */
    private static Workbook readFile(String name) throws Exception {
        File excelFile = new File(name);
        FileInputStream is = new FileInputStream(excelFile);
        checkExcelVaild(excelFile);
        return getWorkbok(is,excelFile);
    }

    /**
     * 合并两个workbook，colNo[i]表示第i列，当需要合并的两行具有相同colNo列的值时，将进行替换操作：替换规则是用excel2替换excel1中的值
     * @param wb1
     * @param wb2
     * @param colNo
     * @return
     * @throws Exception
     */
    private static Workbook mergeWorkBook(Workbook wb1, Workbook wb2, int...colNo) throws Exception{
        checkColNamesConsistency(wb1, wb2);

        Sheet wb1SheetAts = wb1.getSheetAt(0);
        Sheet wb2SheetAts = wb2.getSheetAt(0);

        int rowNum = wb2SheetAts.getLastRowNum();
        int wb1RowNum = wb1SheetAts.getLastRowNum();
        int addOffset = 1;

        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i=0; i<=wb1RowNum; i++){
            String s = key2String(wb1SheetAts.getRow(i), colNo);
            map.put(s, i);
        }

        for(int i=0; i<=rowNum; i++){
            Row row2 = wb2SheetAts.getRow(i), row1 = null;
            String s = key2String(row2, colNo);
            if (map.containsKey(s)) {
                Integer row_i = map.get(s);
                row1 = wb1SheetAts.getRow(row_i);
            }else{
                row1 = wb1SheetAts.createRow(wb1RowNum + addOffset);
                addOffset++;
            }
            writeRow(row2, row1);
        }
        return wb1;
    }

    /**
     *  将行数据中colNo对应的数据提出，并组成字符串
     * @param row
     * @param colNo
     * @return
     */
    private static String key2String(Row row, int[] colNo){
        StringBuilder sb = new StringBuilder();
        for(int j=0; j<colNo.length; j++){
            Cell cell = row.getCell(colNo[j]);
            CellType type = null;
            if((type = cell.getCellType()) == CellType.STRING){
                sb.append(cell.getStringCellValue());
            }else if(type == CellType.BOOLEAN){
                sb.append(cell.getBooleanCellValue());
            }else if(type == CellType.NUMERIC){
                if(DateUtil.isCellDateFormatted(cell)){
                    sb.append(cell.getDateCellValue());
                }else{
                    sb.append(cell.getNumericCellValue());
                }
            }else if(type == CellType.BLANK){
                sb.append(cell.getStringCellValue());
            }else if(type == CellType.FORMULA){
                sb.append(cell.getRichStringCellValue());
            }
            sb.append('$');
        }
        return sb.toString();
    }

    /**
     * 将行数据写入另一个表格的行中
     * @param from
     * @param to
     */
    private static void writeRow(Row from, Row to){
        int n = from.getLastCellNum();
        for(int i=0; i<n; i++){
            Cell cell = from.getCell(i);
            CellType type = null;
            if((type = cell.getCellType()) == CellType.STRING){
                to.createCell(i,cell.getCellType()).setCellValue(cell.getStringCellValue());
            }else if(type == CellType.BOOLEAN){
                to.createCell(i, cell.getCellType()).setCellValue(cell.getBooleanCellValue());
            }else if(type == CellType.NUMERIC){
                if(DateUtil.isCellDateFormatted(cell)){
                    to.createCell(i, cell.getCellType()).setCellValue(cell.getDateCellValue());
                }else{
                    to.createCell(i, cell.getCellType()).setCellValue(cell.getNumericCellValue());
                }
            }else if(type == CellType.BLANK){
                to.createCell(i, cell.getCellType()).setCellValue(cell.getStringCellValue());
            }else if(type == CellType.FORMULA){
                to.createCell(i, cell.getCellType()).setCellValue(cell.getRichStringCellValue());
            }
        }
    }

    /**
     * 判断两个workbook是否具有相同的列名
     * @param wb1
     * @param wb2
     * @throws Exception
     */
    private static void checkColNamesConsistency(Workbook wb1, Workbook wb2) throws Exception{
        String[] colName1 = getColNames(wb1);
        String[] colName2 = getColNames(wb2);
        if(colName1.length != colName2.length){
            throw new Exception("两个EXCEL表格不一致：具有不同的列头，无法进行合并");
        }

        for(int i=0; i<colName1.length; i++){
            if (colName1[i] == null || colName2[i] == null || !colName1[i].equals(colName2[i])) {
                throw new Exception("两个EXCEL表格不一致：具有不同的列头，无法进行合并");
            }
        }
    }

    /**
     * 将workbook中的列名提取出来
     * @param wb
     * @return
     */
    private static String[] getColNames(Workbook wb){
        Sheet sheetAt = wb.getSheetAt(0);
        Row row = sheetAt.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        String[] names = new String[colNum];
        for(int i=0; i<colNum; i++){
            names[i] = row.getCell(i).getStringCellValue();
        }
        return names;
    }
}
