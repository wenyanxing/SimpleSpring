package main.java.com.systop.ExcelPackage;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelImportUtil {
    
    public static final String POINT = ".";
    
    public static SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 读取Excel文件并返回其内的数据
     * @param filePath	文件路径
     * @param sheetIndex 工作簿序号
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> readExcel(String filePath, int sheetIndex) throws IOException{
        File file = new File(filePath);
        if (file == null || !file.exists()){
            throw new ServerException("未找到文件");
        }
        String postfix = StringUtils.substringAfter(file.getName(),".");
        if (StringUtils.endsWithIgnoreCase(postfix,"xls")){
            return readXls(file, sheetIndex);
        }else if (StringUtils.endsWithIgnoreCase(postfix, "xlsx")){
            return readXlsx(file, sheetIndex);
        }else {
            throw new ServerException("文件类型错误");
        }
    }

    /**
     * 读取office2007以上格式的excel
     * @param file	excel文件
     * @param sheetIndex 工作簿序号
     * @return
     * @throws IOException
     */
    private static List<Map<String, String>> readXlsx(File file, int sheetIndex) throws IOException{
        List<Map<String, String>> datas = new ArrayList<>();
        InputStream inputStream = null;
        XSSFWorkbook workbook = null;
        try {
            int totalRows;
            int totalCells;
            inputStream = new FileInputStream(file);
            //创建excel文档
            workbook = new XSSFWorkbook(inputStream);
            //取得第一张工作薄
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet == null){
                throw new ServerException("未找到工作簿"+sheetIndex);
            }
            totalRows = sheet.getLastRowNum();
            //读取表头行
            XSSFRow titleRow = sheet.getRow(0);
            List<String> title = new ArrayList<>();
            //读取单元格的值，从第一列开始
            for (int cellNum = 0; cellNum < titleRow.getLastCellNum(); cellNum++){
                XSSFCell cell = titleRow.getCell(cellNum);
                String cellValue = getXValue(cell).toUpperCase();
                title.add(cellValue);
            }
            //读取数据行，从第二行开始(第一行为表头)
            for (int rowNum = 1; rowNum <totalRows; rowNum++){
                Map<String, String> data = new HashMap<>();
                XSSFRow row = sheet.getRow(rowNum);
                if (row != null){
                    totalCells = row.getLastCellNum();
                    for (int cellNum = 0; cellNum <totalCells; cellNum++){
                        XSSFCell cell = row.getCell(cellNum);
                        String cellTitle = title.get(cellNum);
                        String cellValue = getXValue(cell);
                        data.put(cellTitle,cellValue);
                    }
                }
                datas.add(data);
            }
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
        }
        return datas;
    }

    /**
     * 取得单元格内的值
     * @param xssfCell
     * @return
     */
    private static String getXValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == CellType.BOOLEAN){
            return StringUtils.trim( String.valueOf(xssfCell.getBooleanCellValue()) );
        }else if (xssfCell.getCellType() == CellType.NUMERIC){
            String cellValue = "";
            if (XSSFDateUtil.isCellDateFormatted(xssfCell)){
                Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = DATE_FORMATE.format(date);
            }else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String StrArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
                if (StrArr.equals("00") ){
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return  StringUtils.trim(cellValue);
        }else{
            return StringUtils.trim(xssfCell.getStringCellValue());
        }
    }


    /**
     * 读取office2003以下版本的Excel数据
     * @param file	Excel文件
     * @param sheetIndex 工作簿序号
     * @return
     * @throws IOException
     */
    private static List<Map<String, String>> readXls(File file, int sheetIndex) throws IOException {
        List<Map<String, String>> datas = new ArrayList<>();
        InputStream inputStream = null;
        HSSFWorkbook workbook = null;
        try {
            int totalRows;
            int totalCells;
            inputStream = new FileInputStream(file);
            //创建excel文档
            workbook = new HSSFWorkbook(inputStream);
            //取得第一张工作薄
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet == null){
                throw new ServerException("未找到工作簿" + sheetIndex);
            }
            totalRows = sheet.getLastRowNum();
            //读取表头行
            HSSFRow titleRow = sheet.getRow(0);
            List<String> title = new ArrayList<>();
            //读取单元格的值，从第一行开始
            for (int cellNum = 0; cellNum < titleRow.getLastCellNum(); cellNum++){
                HSSFCell cell = titleRow.getCell(cellNum);
                String cellValue = getHValue(cell).toUpperCase();
                title.add(cellValue);
            }
            //读取数据行，从第二行开始（第一行为表头）
            for (int rowNum = 0; rowNum < totalRows; rowNum++){
                Map<String, String> data = new HashMap<>();
                HSSFRow row = sheet.getRow(rowNum);
                if (row != null){
                    totalCells = row.getLastCellNum();
                    //读取单元格的值，从第一列开始
                    for (int cellNum = 0; cellNum < totalCells; cellNum++){
                        HSSFCell cell = row.getCell(cellNum);
                        String cellTitle = title.get(cellNum);
                        String cellValue = getHValue(cell);
                        data.put(cellTitle, cellValue);
                    }
                }
                datas.add(data);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return datas;
    }

    /**
     * 取得单元格内的值
     * @param hssfCell
     * @return
     */
    private static String getHValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == CellType.BOOLEAN){
            return StringUtils.trim(String.valueOf(hssfCell.getBooleanCellValue()));
        }else if (hssfCell.getCellType() == CellType.NUMERIC){
            String cellValue = "";
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)){
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = DATE_FORMATE.format(date);
            }else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")){
                    cellValue = cellValue.substring(0,cellValue.lastIndexOf(POINT));
                }
            }
            return StringUtils.trim(cellValue);
        }else {
            return  StringUtils.trim(String.valueOf(hssfCell.getStringCellValue()));
        }
    }

    public static void main(String [] args) throws IOException {
        List<Map<String, String>> datas = readExcel("D:\\gxzxjh.xls", 0);
        datas.stream().forEach( data -> System.out.println(data));
    }
    
}
