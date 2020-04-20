package com.base.biz.user.server.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @author:小M
 * @date:2020/4/21 12:38 AM
 */
public class WordUtil {

    /**
     * 替换指定word文件，并保存在指定目录下，返回文件名称
     * @param file
     * @param savePath
     * @param rules
     * @return String 文件名称，拼上savePath得到完整路径
     * @throws Exception
     */
    public static String replaceWordAndSave(File file, String savePath , Map<String, String> rules) throws Exception{
        if (!file.getName().endsWith(".docx")) {
            throw new RuntimeException("word文件必须是.docx格式");
        }
        InputStream is = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(is);

        // 获取所有段落
        Iterator<XWPFParagraph> itPara = doc.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = itPara.next();
            Iterator<String> iterator = rules.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        if (text.indexOf(key) != -1) {
                            isSetText = true;
                            text = text.replace(key, rules.get(key));
                        }
                        if (isSetText) {
                            //参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始,就可以把原来的文字全部替换掉了
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }

        // 表格
        Iterator<XWPFTable> tableI = doc.getTablesIterator();
        if(tableI.hasNext()) {
            XWPFTable table = tableI.next();
            List<XWPFTableRow> rows = table.getRows();
            if(rows != null && rows.size() > 0) {
                Integer rowsSize = rows.size();
                for(int i = 0 ; i < rowsSize; i++) {
                    XWPFTableRow row = rows.get(i);
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    Integer cellsSize = tableCells.size();
                    for(int j = 0 ; j < cellsSize; j++) {
                        XWPFTableCell cell = tableCells.get(j);
                        String text = cell.getText();
                        //System.out.println(text);
                        Iterator<String> iterator = rules.keySet().iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if(text.contains(key)) {
                                if(StringUtils.isNotEmpty(rules.get(key)) && rules.get(key).endsWith("png")) {
                                    cell.removeParagraph(0);
                                    XWPFParagraph p = cell.addParagraph();
                                    File picFile = new File(rules.get(key));
                                    String fileName = rules.get(key).substring(rules.get(key).lastIndexOf("/"));
                                    FileInputStream fileInputStream = new FileInputStream(picFile);
                                    p.createRun().addPicture(fileInputStream, Document.PICTURE_TYPE_PNG, fileName, Units.pixelToEMU(140), Units.pixelToEMU(200));
                                }else {
                                    String t = text.replace(key,rules.get(key));
                                    cell.removeParagraph(0);
                                    XWPFParagraph p = cell.addParagraph();
                                    p.setAlignment(ParagraphAlignment.CENTER);
                                    XWPFRun fun = p.createRun();
                                    fun.setText(t);
                                }
                            }
                        }
                    }
                }
            }
        }

        // 保存到新目录
        String newFileName = UUID.randomUUID() + ".docx";
        String newFilePath = savePath + newFileName;
        File newFile = new File(newFilePath);
        OutputStream os = new FileOutputStream(newFile);
        doc.write(os);
        os.flush();
        os.close();
        return newFileName;
    }
}
