package com.base.biz.wages.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.wages.client.enums.WageTypeEnum;
import com.base.biz.wages.client.enums.WagesReimbursementImportTypeEnum;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import com.base.biz.wages.server.service.BizWagesReimbursementService;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.common.util.NumberUtil;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/9/20 12:56 AM
 */
@Service
public class BizWagesReimbursementServiceImpl implements BizWagesReimbursementService {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizWagesManager bizWagesManager;

    @Autowired
    private ExpireClient expireClient;

    /**
     *
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     */
    @Override
    @Transactional
    public void importReimbursement(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{
        Assert.notNull(time, "time is null");
        Assert.notNull(webFile, "file is null");
        Assert.notNull(targetExcel, "targetExcel is null");
        Assert.notNull(type, "type is null");

        if(WagesReimbursementImportTypeEnum.REIMBURSEMENT.getType().equals(type)) {
            this.importBaseReimbursement(time, webFile, targetExcel, type);
        }else {

        }

    }

    /**
     * 导入报销封面
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     * @throws Exception
     */
    private void importBaseReimbursement(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{
        // 保存上传的文件到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 查询指定日期的工资明细
        List<WagesDO> wagesDOList = bizWagesManager.listByTime(time);
        wagesDOList = wagesDOList == null ? Lists.newArrayList() : wagesDOList;

        // 查询指定日期内的三级工资明细
        List<WagesDO> threeWagesDOList = wagesDOList.stream().filter(item -> {
            return WageTypeEnum.THREE.getType().equals(item.getType());
        }).collect(Collectors.toList());

        // 查询指定日期内的四级工资明细
        List<WagesDO> fourWagesDOList = wagesDOList.stream().filter(item -> {
            return WageTypeEnum.FOUR.getType().equals(item.getType());
        }).collect(Collectors.toList());

        // 需统计字段
        BigDecimal one = new BigDecimal(0.0);
        BigDecimal two = new BigDecimal(0.0);
        BigDecimal three = new BigDecimal(0.0);
        BigDecimal four = new BigDecimal(0.0);
        BigDecimal five = new BigDecimal(0.0);
        BigDecimal six = new BigDecimal(0.0);
        BigDecimal seven = new BigDecimal(0.0);
        BigDecimal eight = new BigDecimal(0.0);
        BigDecimal nine = new BigDecimal(0.0);
        BigDecimal ten = new BigDecimal(0.0);
        BigDecimal eleven = new BigDecimal(0.0);
        BigDecimal twelve = new BigDecimal(0.0);
        BigDecimal sum = new BigDecimal(0.0);

        // 四级工资统计
        for(WagesDO item : fourWagesDOList) {
            BigDecimal oneThousand = new BigDecimal(1000);
            one = one.add(new BigDecimal(item.getRealWages()).divide(oneThousand));
            two = two.add(new BigDecimal(item.getPersonalIncomeTax()).divide(oneThousand));
            three = three.add(new BigDecimal(item.getPersonalSocialSecurityMoney()).divide(oneThousand));
            four = four.add(new BigDecimal(item.getPersonalAccumulationFund()).divide(oneThousand));
            five = five.add(new BigDecimal(item.getDepartmentSocialSecurityMoney()).divide(oneThousand));
            six = six.add(new BigDecimal(item.getDepartmentAccumulat()).divide(oneThousand));
        }

        // 三级工资统计
        for(WagesDO item : threeWagesDOList) {
            BigDecimal oneThousand = new BigDecimal(1000);
            seven = seven.add(new BigDecimal(item.getRealWages()).divide(oneThousand));
            eight = eight.add(new BigDecimal(item.getPersonalIncomeTax()).divide(oneThousand));
            nine = nine.add(new BigDecimal(item.getPersonalSocialSecurityMoney()).divide(oneThousand));
            ten = ten.add(new BigDecimal(item.getPersonalAccumulationFund()).divide(oneThousand));
            eleven = eleven.add(new BigDecimal(item.getDepartmentSocialSecurityMoney()).divide(oneThousand));
            twelve = twelve.add(new BigDecimal(item.getDepartmentAccumulat()).divide(oneThousand));
        }

        // 总计
        sum = sum.add(one).add(two).add(three).add(four).add(five).
            add(six).add(seven).add(eight).add(nine).add(ten).
            add(eleven).add(twelve);

        // 取两位小数
        one = one.setScale(2,BigDecimal.ROUND_HALF_UP);
        String oneStr = one.toString();
        CellDTO oneCellDTO = new CellDTO(oneStr);

        two = two.setScale(2,BigDecimal.ROUND_HALF_UP);
        String twoStr = two.toString();
        CellDTO twoCellDTO = new CellDTO(twoStr);

        three = three.setScale(2,BigDecimal.ROUND_HALF_UP);
        String threeStr = three.toString();
        CellDTO threeCellDTO = new CellDTO(threeStr);

        four = four.setScale(2,BigDecimal.ROUND_HALF_UP);
        String fourStr = four.toString();
        CellDTO fourCellDTO = new CellDTO(fourStr);

        five = five.setScale(2,BigDecimal.ROUND_HALF_UP);
        String fiveStr = five.toString();
        CellDTO fiveCellDTO = new CellDTO(fiveStr);

        six = six.setScale(2,BigDecimal.ROUND_HALF_UP);
        String sixStr = six.toString();
        CellDTO sixCellDTO = new CellDTO(sixStr);

        seven = seven.setScale(2,BigDecimal.ROUND_HALF_UP);
        String sevenStr = seven.toString();
        CellDTO sevenCellDTO = new CellDTO(sevenStr);

        eight = eight.setScale(2,BigDecimal.ROUND_HALF_UP);
        String eightStr = eight.toString();
        CellDTO eightCellDTO = new CellDTO(eightStr);

        nine = nine.setScale(2,BigDecimal.ROUND_HALF_UP);
        String nineStr = nine.toString();
        CellDTO nineCellDTO = new CellDTO(nineStr);

        ten = ten.setScale(2,BigDecimal.ROUND_HALF_UP);
        String tenStr = ten.toString();
        CellDTO tenCellDTO = new CellDTO(tenStr);

        eleven = eleven.setScale(2,BigDecimal.ROUND_HALF_UP);
        String elevenStr = eleven.toString();
        CellDTO elevenCellDTO = new CellDTO(elevenStr);

        twelve = twelve.setScale(2,BigDecimal.ROUND_HALF_UP);
        String twelveStr = twelve.toString();
        CellDTO twelveCellDTO = new CellDTO(twelveStr);

        sum = sum.setScale(2,BigDecimal.ROUND_HALF_UP);
        String sum2Str = sum.toString();
        CellDTO sum2CellDTO = new CellDTO(sum2Str);

        String sum1Str = sum.toString();
        CellDTO sum1CellDTO = new CellDTO(NumberUtil.number2CNMontrayUnit(sum));

        // 读取excel，进行比较
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator rows = sheet.rowIterator();
        int rowNum = 1;
        while(rows.hasNext()) {
            if (rowNum <= 4 || rowNum >= 18) {
                rows.next();
                rowNum++;
                continue;
            }

            XSSFRow row = (XSSFRow) rows.next();
            if(row != null) {

                int num = row.getLastCellNum();
                // 遍历单元格

                for (int i = 0; i < num; i++) {

                    if(i != 3) {
                        continue;
                    }

                    XSSFCell cell = row.getCell(i);

                    // 四级租赁岗（实名制）辅警实发工资
                    if(rowNum == 5) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!one.equals(bigDecimal)) {
                            oneStr = bigDecimal.toString() + "(" + oneStr + ")";
                            oneCellDTO.text = oneStr;
                            oneCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 四级租赁岗（实名制）辅警缴纳个人所得税
                    if(rowNum == 6) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!two.equals(bigDecimal)) {
                            twoStr = bigDecimal.toString() + "(" + twoStr + ")";
                            twoCellDTO.text = twoStr;
                            twoCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 四级租赁岗（实名制）辅警个人缴交社保
                    if(rowNum == 7) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!three.equals(bigDecimal)) {
                            threeStr = bigDecimal.toString() + "(" + threeStr + ")";
                            threeCellDTO.text = threeStr;
                            threeCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 四级租赁岗（实名制）辅警个人缴交公积金
                    if(rowNum == 8) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!four.equals(bigDecimal)) {
                            fourStr = bigDecimal.toString() + "(" + fourStr + ")";
                            fourCellDTO.text = fourStr;
                            fourCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 四级租赁岗（实名制）辅警单位缴交社保
                    if(rowNum == 9) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!five.equals(bigDecimal)) {
                            fiveStr = bigDecimal.toString() + "(" + fiveStr + ")";
                            fiveCellDTO.text = fiveStr;
                            fiveCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 四级租赁岗（实名制）辅警单位缴交公积金
                    if(rowNum == 10) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!six.equals(bigDecimal)) {
                            sixStr = bigDecimal.toString() + "(" + sixStr + ")";
                            sixCellDTO.text = sixStr;
                            sixCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗实发工资
                    if(rowNum == 11) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!seven.equals(bigDecimal)) {
                            sevenStr = bigDecimal.toString() + "(" + sevenStr + ")";
                            sevenCellDTO.text = sevenStr;
                            sevenCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗缴纳个人所得税
                    if(rowNum == 12) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!eight.equals(bigDecimal)) {
                            eightStr = bigDecimal.toString() + "(" + eightStr + ")";
                            eightCellDTO.text = eightStr;
                            eightCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗个人缴交社保
                    if(rowNum == 13) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!nine.equals(bigDecimal)) {
                            nineStr = bigDecimal.toString() + "(" + nineStr + ")";
                            nineCellDTO.text = nineStr;
                            nineCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗个人缴交公积金
                    if(rowNum == 14) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!ten.equals(bigDecimal)) {
                            tenStr = bigDecimal.toString() + "(" + tenStr + ")";
                            tenCellDTO.text = tenStr;
                            tenCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗单位缴交社保
                    if(rowNum == 15) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!eleven.equals(bigDecimal)) {
                            elevenStr = bigDecimal.toString() + "(" + elevenStr + ")";
                            elevenCellDTO.text = elevenStr;
                            elevenCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 三级租赁岗单位缴交公积金
                    if(rowNum == 16) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!twelve.equals(bigDecimal)) {
                            twelveStr = bigDecimal.toString() + "(" + twelveStr + ")";
                            twelveCellDTO.text = twelveStr;
                            twelveCellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }

                    // 合计
                    if(rowNum == 17) {
                        Double value = cell.getNumericCellValue();
                        BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(!sum.equals(bigDecimal)) {
                            sum2Str = bigDecimal.toString() + "(" + sum2Str + ")";
                            sum2CellDTO.text = sum2Str;
                            sum2CellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                    }
                }
            }
            rowNum++;
        }

        // 创建excel报表
        Map<String,CellDTO> replaceMap = new HashMap<>();
        replaceMap.put("one" , oneCellDTO);
        replaceMap.put("two" , twoCellDTO);
        replaceMap.put("three" , threeCellDTO);
        replaceMap.put("four" , fourCellDTO);
        replaceMap.put("five" , fiveCellDTO);
        replaceMap.put("six" , sixCellDTO);
        replaceMap.put("seven" , sevenCellDTO);
        replaceMap.put("eight" , eightCellDTO);
        replaceMap.put("nine" , nineCellDTO);
        replaceMap.put("ten" , tenCellDTO);
        replaceMap.put("eleven" , elevenCellDTO);
        replaceMap.put("twelve" , twelveCellDTO);

        replaceMap.put("date" , new CellDTO(DateUtil.getCurrentDateStr(DateUtil.convert2String(new Date(), "yyyy年MM月"))));
        replaceMap.put("kemu" , new CellDTO(DateUtil.getCurrentDateStr(String.valueOf(DateUtil.getMonth(time)) + "月辅警工资")));
        replaceMap.put("sum1" , sum1CellDTO);
        replaceMap.put("sum2" , sum2CellDTO);

        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, 3, 0, savePath, Lists.newArrayList(), replaceMap);
        System.out.println(excelName);

        // 如果expire表存在，则删除
        String code = ExpireType.WagesReimbursement.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        expireClient.delete(code);

        // 存储到expire表中
        String fileName1 = DateUtil.convert2String(time, "yyyy年MM月") + "工资报销封面";
        String fileName2 = DateUtil.convert2String(time, "yyyy年MM月") + "工资报销封面(系统生成)";
        String url1 = url;
        String url2 = savePath + excelName;
        expireClient.add(code, fileName1 + "@" + fileName2, url1 + "@" + url2, time, "", ExpireType.WagesReimbursement.getCode());
    }
}
