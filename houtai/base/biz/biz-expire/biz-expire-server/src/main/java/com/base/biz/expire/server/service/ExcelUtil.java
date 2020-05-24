package com.base.biz.expire.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author:小M
 * @date:2020/5/24 10:48 PM
 */
public class ExcelUtil {

    public static String insertExcelAndSave(InputStream inputStream, Integer beginRowNum, String savePath, List<List<String>> rules) throws Exception{
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);

        int i = beginRowNum;
        for(List<String> cells : rules) {
            XSSFRow row = createRow(sheet, i);
            int j = 0;
            for(String cell : cells) {
                row.createCell(j++).setCellValue(cell);
            }
            i++;
        }

        // 保存到新目录
        String newFileName = UUID.randomUUID() + ".xlsx";
        String newFilePath = savePath + newFileName;
        File newFile = new File(newFilePath);
        OutputStream os = new FileOutputStream(newFile);
        wb.write(os);
        os.flush();
        os.close();
        return newFileName;
    }

    /**
     * 找到需要插入的行数，并新建一个POI的row对象
     * @param sheet
     * @param rowIndex
     * @return
     */
    private static XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {
        XSSFRow row = null;
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            sheet.shiftRows(rowIndex, lastRowNo, 1);
        }
        row = sheet.createRow(rowIndex);
        return row;
    }
}
