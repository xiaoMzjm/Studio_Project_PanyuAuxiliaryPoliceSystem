package com.base.biz.user.server.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

/**
 * @author:小M
 * @date:2020/4/21 12:38 AM
 */
public class WordUtil {

    public static class TextDTO{
        private String text;
        private boolean center;

        public TextDTO(String text, boolean center) {
            this.text = text;
            this.center = center;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCenter() {
            return center;
        }

        public void setCenter(boolean center) {
            this.center = center;
        }
    }

    public static class PicDTO {
        private File file;
        private Integer width;
        private Integer height;
        private String fileName;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    public static class RowDTO {
        private List<List<RowCellDTO>> values;

        public RowDTO() {
        }

        public RowDTO(List<List<RowCellDTO>> values) {
            this.values = values;
        }

        public List<List<RowCellDTO>> getValues() {
            return values;
        }

        public void setValues(List<List<RowCellDTO>> values) {
            this.values = values;
        }
    }

    public static class RowCellDTO{
        private String text;
        private Integer width;

        public RowCellDTO(String text, Integer width) {
            this.text = text;
            this.width = width;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }
    }

    /**
     * 替换指定word文件，并保存在指定目录下，返回文件名称
     * @param file
     * @param savePath
     * @param rules
     * @return String 文件名称，拼上savePath得到完整路径
     * @throws Exception
     */
    public static String replaceWordAndSave(File file, String savePath , Map<String, Object> rules) throws Exception{
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
                            // 段落只支持字符串
                            Object value = rules.get(key);
                            if (value instanceof TextDTO) {
                                TextDTO textDTO = (TextDTO)value;
                                if(StringUtils.isEmpty(textDTO.getText())) {
                                    textDTO.setText("");
                                }
                                isSetText = true;
                                text = text.replace(key, textDTO.getText());
                            }
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

                // 遍历行
                for(int i = 0 ; i < rowsSize; i++) {
                    XWPFTableRow row = rows.get(i);
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    Integer cellsSize = tableCells.size();

                    // 遍历列
                    for(int j = 0 ; j < cellsSize; j++) {
                        XWPFTableCell cell = tableCells.get(j);
                        String text = cell.getText();
                        //System.out.println(text);
                        Iterator<String> iterator = rules.keySet().iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if(text.contains(key)) {

                                Object value = rules.get(key);

                                // 文本
                                if (value instanceof TextDTO) {
                                    TextDTO textDTO = (TextDTO)value;
                                    if(StringUtils.isEmpty(textDTO.getText())) {
                                        textDTO.setText("");
                                    }
                                    String t = text.replace(key,textDTO.getText());

                                    cell.removeParagraph(0);
                                    XWPFParagraph p = cell.addParagraph();
                                    if(textDTO.center){
                                        p.setAlignment(ParagraphAlignment.CENTER);
                                    }
                                    XWPFRun fun = p.createRun();
                                    fun.setText(t);
                                }

                                // 图片
                                if(value instanceof PicDTO) {
                                    PicDTO picDTO = (PicDTO)value;
                                    cell.removeParagraph(0);
                                    if(picDTO.getFile() == null) {
                                        continue;
                                    }

                                    XWPFParagraph p = cell.addParagraph();
                                    String fileName = picDTO.getFileName();
                                    FileInputStream fileInputStream = new FileInputStream(picDTO.getFile());
                                    p.createRun().addPicture(fileInputStream, Document.PICTURE_TYPE_PNG, fileName, Units.pixelToEMU(picDTO.getWidth()), Units.pixelToEMU(picDTO.getHeight()));
                                }

                                // 新增行
                                //if(value instanceof RowDTO) {
                                //
                                //    table.removeRow(i);
                                //    rowsSize--;
                                //    RowDTO rowDTO = (RowDTO)value;
                                //    if(CollectionUtils.isEmpty(rowDTO.getValues())) {
                                //        continue;
                                //    }
                                //    for(List<RowCellDTO> rs : rowDTO.getValues()) {
                                //        XWPFTableRow newRow = table.insertNewTableRow(i++);
                                //        rowsSize++;
                                //
                                //        for(int z = 0; z < rs.size(); z++) {
                                //            XWPFTableCell xwpfTableCell = newRow.createCell();//在新增的行上面创建cell
                                //
                                //            RowCellDTO rowCellDTO = rs.get(z);
                                //            if(rowCellDTO.getWidth() != null) {
                                //                CTTc cttc = xwpfTableCell.getCTTc();
                                //                CTTcPr cellPr = cttc.addNewTcPr();
                                //                CTTblWidth tblWidth = cellPr.isSetTcW() ? cellPr.getTcW() : cellPr.addNewTcW();
                                //                tblWidth.setW(new BigInteger(String.valueOf(rowCellDTO.getWidth())));
                                //                tblWidth.setType(STTblWidth.DXA);
                                //            }
                                //            xwpfTableCell.setText(rowCellDTO.getText());
                                //            xwpfTableCell.setVerticalAlignment(XWPFVertAlign.CENTER);
                                //        }
                                //    }
                                //}
                                if(value instanceof RowDTO) {
                                    table.removeRow(i);
                                    rowsSize--;
                                    RowDTO rowDTO = (RowDTO)value;
                                    if(CollectionUtils.isEmpty(rowDTO.getValues())) {
                                        continue;
                                    }
                                    for(List<RowCellDTO> rowCellDTOList : rowDTO.getValues()) {
                                        XWPFTableRow rowTemp = rows.get(i++);
                                        List<XWPFTableCell> tableCellsTEmp = rowTemp.getTableCells();
                                        for(int z = 0 ;z < rowCellDTOList.size(); z++) {
                                            RowCellDTO rowCellDTO = rowCellDTOList.get(z);
                                            XWPFTableCell cellTem = tableCellsTEmp.get(z);
                                            cellTem.setText(rowCellDTO.getText());
                                        }
                                    }
                                    i++;
                                    while(true) {
                                        XWPFTableRow rowTemp = rows.get(i);
                                        String textTemp = rowTemp.getTableCells().get(0).getText();
                                        if (!"${end}".equals(textTemp)) {
                                            table.removeRow(i);
                                            rowsSize--;
                                        }else {
                                            table.removeRow(i);
                                            rowsSize--;
                                            break;
                                        }
                                    }
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
