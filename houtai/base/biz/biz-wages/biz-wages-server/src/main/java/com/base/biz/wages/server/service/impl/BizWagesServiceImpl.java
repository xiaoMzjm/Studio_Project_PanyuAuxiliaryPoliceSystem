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
import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.client.BizUserClient;
import com.base.biz.wages.client.enums.WagesDetailImportTypeEnum;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import com.base.biz.wages.server.service.BizWagesService;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/8/30 11:54 AM
 */
@Service
public class BizWagesServiceImpl implements BizWagesService {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;
    @Autowired
    private BizWagesManager bizWagesManager;
    @Autowired
    private BizUserClient bizUserClient;
    @Autowired
    private ExpireClient expireClient;

    @Transactional(rollbackFor = Exception.class)

    @Override
    public void importDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception {
        Assert.notNull(webFile , "文件为空");
        Assert.hasText(diskStaticUrl, "diskStaticUrl为空");
        Assert.notNull(time, "time is null");
        Assert.notNull(type, "type is null");
        Assert.isTrue(webFile.getOriginalFilename().endsWith("xlsx"), "请上传xlsx格式的文件");

        if(WagesDetailImportTypeEnum.THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.FOUR.getValue().equals(type)) {
            this.importBaseDetail(time, webFile, targetExcel, type);
        }else {
            this.importCorrectDetail(time, webFile, targetExcel, type);
        }
    }

    private void importBaseDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{

        // 保存到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);

        // 读取excel
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator rows = sheet.rowIterator();

        // 身份证集合
        List<String> identityCodeList = Lists.newArrayList();

        // 遍历行
        int rowNum = 1;
        while(rows.hasNext()) {
            if(rowNum <= 3) {
                rows.next();
                rowNum++;
                continue;
            }
            rowNum++;
            XSSFRow row = (XSSFRow) rows.next();
            if(row != null) {
                int num = row.getLastCellNum();
                // 遍历单元格

                for (int i = 0; i < num; i++) {
                    XSSFCell cell = row.getCell(i);
                    // 身份证
                    if(cell != null && i == 5) {
                        identityCodeList.add(cell.getStringCellValue());
                    }
                }
            }
        }

        // 查询人员
        Map<String,BizUserDetailVO> code2UserMap = bizUserClient.listByCodeList(identityCodeList);

        // 重新读取excel
        sheet = wb.getSheetAt(0);
        rows = sheet.rowIterator();

        // 总计
        Double basePay = 0.0; // 基本工资
        Double allowance = 0.0; // 连续租赁岗位津贴
        Double promotionMoney = 0.0; // 符合晋升待遇
        Double wagesPayable = 0.0; // 应发工资合计
        Double departmentSocialSecurityMoney = 0.0; // 单位扣缴社保费
        Double personalSocialSecurityMoney = 0.0; // 个人扣缴社保费
        Double departmentAccumulat = 0.0; // 单位扣缴公积金费
        Double personalAccumulationFund = 0.0; // 个人扣缴公积金费
        Double personalIncomeTax = 0.0; // 应缴个税金额
        Double realWages = 0.0; // 实发工资合计

        // 遍历行
        List<List<CellDTO>> rules = Lists.newArrayList();
        List<WagesDO> wagesDOList = Lists.newArrayList();
        rowNum = 0;
        boolean isEnd = false;
        while(rows.hasNext()) {
            if(isEnd) {
                break;
            }
            if(rowNum <= 2) {
                rows.next();
                rowNum++;
                continue;
            }
            rowNum++;
            XSSFRow row = (XSSFRow) rows.next();
            if(row != null) {
                int num = row.getLastCellNum();
                WagesDO wagesDO = new WagesDO();
                wagesDO.setTime(time);
                wagesDO.setGmtCreate(new Date());
                wagesDO.setGmtModified(new Date());
                wagesDO.setType(type);


                // 遍历单元格
                List<CellDTO> cellDTOList = Lists.newArrayList();
                BizUserDetailVO bizUserDetailVO = null;
                for (int i = 0; i < num; i++) {
                    XSSFCell cell = row.getCell(i);
                    if(cell == null) {
                        continue;
                    }


                    // 序号
                    if(i == 0) {
                        try {
                            wagesDO.setSequence(new Double(cell.getNumericCellValue()).intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                        }catch (Exception e) {
                            if("合计".equals(cell.getStringCellValue())) {
                                isEnd = true;
                                break;
                            }
                            throw new BaseException("第" + rowNum + "行的'序号'格式错误，必须为数字");
                        }
                    }
                    // 单位名称
                    if(i == 1) {
                        wagesDO.setDepartmentName(cell.getStringCellValue());
                        cellDTOList.add(new CellDTO(cell.getStringCellValue()));
                    }
                    // 姓名
                    if(i == 2) {
                        wagesDO.setName(cell.getStringCellValue());
                        cellDTOList.add(new CellDTO(cell.getStringCellValue()));
                    }
                    // 性别
                    if(i == 3) {
                        wagesDO.setSex(cell.getStringCellValue());
                        cellDTOList.add(new CellDTO(cell.getStringCellValue()));
                    }
                    // 银行
                    if(i == 4) {
                        wagesDO.setBankCode(cell.getStringCellValue());
                        CellDTO cellDTO = new CellDTO(cell.getStringCellValue());
                        cellDTO.isString = true;
                        cellDTOList.add(cellDTO);
                    }
                    // 身份证
                    if(i == 5) {
                        if(StringUtils.isEmpty(cell)) {
                            throw new BaseException("第" + rowNum + "行的'身份证'为空");
                        }
                        wagesDO.setIdentityCard(cell.getStringCellValue());
                        CellDTO cellDTO = new CellDTO(cell.getStringCellValue());
                        cellDTO.isString = true;
                        bizUserDetailVO = code2UserMap.get(cell.getStringCellValue());
                        if(bizUserDetailVO == null) {
                            cellDTO.color = Integer.valueOf(Font.COLOR_RED);
                        }
                        cellDTOList.add(cellDTO);
                    }
                    // 租赁岗位连续工作时间
                    if(i == 6) {
                        try {
                            wagesDO.setContinuityWorkDay(new Double(cell.getNumericCellValue()).intValue());
                            CellDTO cellDTO = new CellDTO(String.valueOf(cell.getNumericCellValue()));
                            if(bizUserDetailVO != null) {
                                Integer systemContinuityWorkDay = this.getContinuityWorkDay(bizUserDetailVO.getRuZhiZuLinTime());
                                if(!systemContinuityWorkDay.equals(wagesDO.getContinuityWorkDay())) {
                                    cellDTO = new CellDTO(getNewValue(wagesDO.getContinuityWorkDay().toString(),String.valueOf(systemContinuityWorkDay)));
                                    cellDTO.color = Integer.valueOf(Font.COLOR_RED);
                                    wagesDO.setContinuityWorkDay(systemContinuityWorkDay);
                                }
                            }
                            cellDTOList.add(cellDTO);
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'租赁岗位连续工作时间'格式错误，必须为数字");
                        }
                    }
                    // 基本工资 * 1000 变为分（三级岗>=4000，四级岗>=4500）
                    if(i == 7) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setBasePay(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            basePay += new Double(cell.getNumericCellValue());
                            if(WagesDetailImportTypeEnum.THREE.getValue().equals(type) ||
                                WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type)) {
                                if(f < 4000000.0) {
                                    throw new BaseException("第" + rowNum + "行，基本工资 错误，三级岗位应>=4000");
                                }
                            }
                            if(WagesDetailImportTypeEnum.FOUR.getValue().equals(type) ||
                                WagesDetailImportTypeEnum.CORRECT_FOUR.getValue().equals(type)) {
                                if(f < 4500000.0) {
                                    throw new BaseException("第" + rowNum + "行，基本工资 错误，四级岗位应>=4500");
                                }
                            }
                        }catch (Exception e) {
                            if(e instanceof BaseException) {
                                throw e;
                            }
                            throw new BaseException("第" + rowNum + "行的'基本工资'格式错误，必须为数字");
                        }
                    }
                    // 连续岗位租赁津贴 * 1000 变为分
                    if(i == 8) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setAllowance(f.intValue());
                            CellDTO cellDTO = new CellDTO(String.valueOf(cell.getNumericCellValue()));
                            allowance += new Double(cell.getNumericCellValue());

                            Integer systemAllowance = (wagesDO.getContinuityWorkDay() + 1) * 100 * 1000;
                            if(!systemAllowance.equals(f.intValue())) {
                                cellDTO = new CellDTO(getNewValue(String.valueOf(f.intValue()/1000),String.valueOf(systemAllowance/1000)));
                                cellDTO.color = Integer.valueOf(Font.COLOR_RED);
                                wagesDO.setAllowance(systemAllowance);
                            }
                            cellDTOList.add(cellDTO);

                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'连续岗位租赁津贴'格式错误，必须为数字");
                        }
                    }
                    // 符合晋升待遇
                    if(i == 9) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPromotionMoney(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            promotionMoney += new Double(cell.getNumericCellValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'符合晋升待遇'格式错误，必须为数字");
                        }
                    }
                    // 应发工资合计
                    if(i == 10) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setWagesPayable(f.intValue());
                            CellDTO cellDTO = new CellDTO(String.valueOf(cell.getNumericCellValue()));

                            wagesPayable += new Double(cell.getNumericCellValue());

                            Integer systemWagesPayable = wagesDO.getBasePay() + wagesDO.getAllowance() + wagesDO.getPromotionMoney();
                            if(!systemWagesPayable.equals(f.intValue())) {
                                cellDTO = new CellDTO(getNewValue(String.valueOf(f.intValue()/1000),String.valueOf(systemWagesPayable / 1000)));
                                cellDTO.color = Integer.valueOf(Font.COLOR_RED);
                                wagesDO.setWagesPayable(systemWagesPayable);
                            }
                            cellDTOList.add(cellDTO);
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'应发工资合计'格式错误，必须为数字");
                        }
                    }
                    // 单位扣缴社保费
                    if(i == 11) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setDepartmentSocialSecurityMoney(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            departmentSocialSecurityMoney += new Double(cell.getNumericCellValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'单位扣缴社保费'格式错误，必须为数字");
                        }
                    }
                    // 个人扣缴社保费
                    if(i == 12) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalSocialSecurityMoney(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            personalSocialSecurityMoney += new Double(cell.getNumericCellValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'个人扣缴社保费'格式错误，必须为数字");
                        }
                    }
                    // 单位扣缴公积金费
                    if(i == 13) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setDepartmentAccumulat(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            departmentAccumulat += new Double(cell.getNumericCellValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'单位扣缴公积金费'格式错误，必须为数字");
                        }
                    }
                    // 个人扣缴公积金费
                    if(i == 14) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalAccumulationFund(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            personalAccumulationFund += new Double(cell.getNumericCellValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'个人扣缴公积金费'格式错误，必须为数字");
                        }
                    }
                    // 应缴个税金额
                    if(i == 15) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalIncomeTax(f.intValue());
                            cellDTOList.add(new CellDTO(String.valueOf(cell.getNumericCellValue())));
                            personalIncomeTax += new Double(cell.getNumericCellValue());
                            if(WagesDetailImportTypeEnum.THREE.getValue().equals(type) ||
                                WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type)) {
                                if(!f.equals(0.0)) {
                                    throw new BaseException("第" + rowNum + "行，应缴个税金额 错误，三级岗位应=0");
                                }
                            }
                        }catch (Exception e) {
                            if(e instanceof BaseException) {
                                throw e;
                            }
                            throw new BaseException("第" + rowNum + "行的'应缴个税金额'格式错误，必须为数字");
                        }
                    }
                    // 实发工资合计
                    if(i == 16) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setRealWages(f.intValue());
                            CellDTO cellDTO = new CellDTO(String.valueOf(cell.getNumericCellValue()));
                            realWages += new Double(cell.getNumericCellValue());

                            Integer systemRealWages = wagesDO.getWagesPayable() -
                                wagesDO.getPersonalSocialSecurityMoney() -
                                wagesDO.getPersonalAccumulationFund() -
                                wagesDO.getPersonalIncomeTax();
                            if(!systemRealWages.equals(f.intValue())) {
                                cellDTO = new CellDTO(getNewValue(String.valueOf(f.intValue()/1000.0),String.valueOf(systemRealWages / 1000.0)));
                                cellDTO.color = Integer.valueOf(Font.COLOR_RED);
                                wagesDO.setRealWages(systemRealWages);
                            }
                            cellDTOList.add(cellDTO);
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'实发工资合计'格式错误，必须为数字");
                        }
                    }
                    // 备注
                    if(i == 17) {
                        wagesDO.setRemark(cell.getStringCellValue());
                        cellDTOList.add(new CellDTO(cell.getStringCellValue()));
                    }
                    wagesDOList.add(wagesDO);
                }
                rules.add(cellDTOList);
            }
        }

        // 最终数据保存到db
        if(WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.CORRECT_FOUR.getValue().equals(type)) {
            // 删除数据
            bizWagesManager.deleteByIdentityList(identityCodeList);

            // 保存到db
            bizWagesManager.batchSave(wagesDOList);
        }


        // 保存新的excel
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("basePay" , new BigDecimal(basePay).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("allowance" , new BigDecimal(allowance).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("promotionMoney" , new BigDecimal(promotionMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("wagesPayable" , new BigDecimal(wagesPayable).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("departmentSocialSecurityMoney" , new BigDecimal(departmentSocialSecurityMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalSocialSecurityMoney" , new BigDecimal(personalSocialSecurityMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("departmentAccumulat" , new BigDecimal(departmentAccumulat).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalAccumulationFund" , new BigDecimal(personalAccumulationFund).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalIncomeTax" , new BigDecimal(personalIncomeTax).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("realWages" , new BigDecimal(realWages).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("date" , DateUtil.getCurrentDateStr("yyyy年MM月dd日"));
        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, 3, 0, savePath, rules, replaceMap);
        System.out.println(excelName);

        // 删除记录
        String code = ExpireType.Wages.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        expireClient.delete(code);

        // 保存excel到db
        String fileName1 = DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type);
        String fileName2 = DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type + 2);
        String url1 = url;
        String  url2 = savePath + excelName;
        expireClient.add(code, fileName1 + "@" + fileName2 , url1 + "@" + url2, time, "", ExpireType.Wages.getCode());

    }

    private void importCorrectDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type)
        throws Exception {

        // 保存到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 读取excel
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator rows = sheet.rowIterator();

        // 遍历行
        List<List<CellDTO>> rules = Lists.newArrayList();
        List<WagesDO> wagesDOList = Lists.newArrayList();
        int rowNum = 0;
        boolean isEnd = false;
        while(rows.hasNext()) {
            if(isEnd) {
                break;
            }
            if(rowNum <= 2) {
                rows.next();
                rowNum++;
                continue;
            }
            rowNum++;
            XSSFRow row = (XSSFRow) rows.next();
            if(row != null) {
                int num = row.getLastCellNum();
                WagesDO wagesDO = new WagesDO();
                wagesDO.setTime(time);
                wagesDO.setGmtCreate(new Date());
                wagesDO.setGmtModified(new Date());
                wagesDO.setType(type);


                // 遍历单元格
                BizUserDetailVO bizUserDetailVO = null;
                for (int i = 0; i < num; i++) {
                    XSSFCell cell = row.getCell(i);
                    if(cell == null) {
                        continue;
                    }


                    // 序号
                    if(i == 0) {
                        try {
                            wagesDO.setSequence(new Double(cell.getNumericCellValue()).intValue());
                        }catch (Exception e) {
                            if("合计".equals(cell.getStringCellValue())) {
                                isEnd = true;
                                break;
                            }
                            throw new BaseException("第" + rowNum + "行的'序号'格式错误，必须为数字");
                        }
                    }
                    // 单位名称
                    if(i == 1) {
                        wagesDO.setDepartmentName(cell.getStringCellValue());
                    }
                    // 姓名
                    if(i == 2) {
                        wagesDO.setName(cell.getStringCellValue());
                    }
                    // 性别
                    if(i == 3) {
                        wagesDO.setSex(cell.getStringCellValue());
                    }
                    // 银行
                    if(i == 4) {
                        wagesDO.setBankCode(cell.getStringCellValue());
                    }
                    // 身份证
                    if(i == 5) {
                        if(StringUtils.isEmpty(cell)) {
                            throw new BaseException("第" + rowNum + "行的'身份证'为空");
                        }
                        wagesDO.setIdentityCard(cell.getStringCellValue());
                    }
                    // 租赁岗位连续工作时间
                    if(i == 6) {
                        try {
                            wagesDO.setContinuityWorkDay(new Double(cell.getNumericCellValue()).intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'租赁岗位连续工作时间'格式错误，必须为数字");
                        }
                    }
                    // 基本工资 * 1000 变为分（三级岗>=4000，四级岗>=4500）
                    if(i == 7) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setBasePay(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'基本工资'格式错误，必须为数字");
                        }
                    }
                    // 连续岗位租赁津贴 * 1000 变为分
                    if(i == 8) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setAllowance(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'连续岗位租赁津贴'格式错误，必须为数字");
                        }
                    }
                    // 符合晋升待遇
                    if(i == 9) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPromotionMoney(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'符合晋升待遇'格式错误，必须为数字");
                        }
                    }
                    // 应发工资合计
                    if(i == 10) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setWagesPayable(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'应发工资合计'格式错误，必须为数字");
                        }
                    }
                    // 单位扣缴社保费
                    if(i == 11) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setDepartmentSocialSecurityMoney(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'单位扣缴社保费'格式错误，必须为数字");
                        }
                    }
                    // 个人扣缴社保费
                    if(i == 12) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalSocialSecurityMoney(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'个人扣缴社保费'格式错误，必须为数字");
                        }
                    }
                    // 单位扣缴公积金费
                    if(i == 13) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setDepartmentAccumulat(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'单位扣缴公积金费'格式错误，必须为数字");
                        }
                    }
                    // 个人扣缴公积金费
                    if(i == 14) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalAccumulationFund(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'个人扣缴公积金费'格式错误，必须为数字");
                        }
                    }
                    // 应缴个税金额
                    if(i == 15) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setPersonalIncomeTax(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'应缴个税金额'格式错误，必须为数字");
                        }
                    }
                    // 实发工资合计
                    if(i == 16) {
                        try {
                            Double f = new Double(cell.getNumericCellValue()) * 1000.0;
                            wagesDO.setRealWages(f.intValue());
                        }catch (Exception e) {
                            throw new BaseException("第" + rowNum + "行的'实发工资合计'格式错误，必须为数字");
                        }
                    }
                    // 备注
                    if(i == 17) {
                        wagesDO.setRemark(cell.getStringCellValue());
                    }
                    wagesDOList.add(wagesDO);
                }
            }
        }

        // 删除数据
        bizWagesManager.deleteByIdentityList(wagesDOList.stream().map(WagesDO::getIdentityCard).collect(Collectors.toList()));

        // 保存到db
        bizWagesManager.batchSave(wagesDOList);


        // 删除记录
        String code = ExpireType.Wages.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        ExpireVO expireVO = expireClient.findByCode(code);
        if(expireVO == null) {
            throw new BaseException("请先上传工资明细表");
        }

        // 保存excel到db
        String fileName = expireVO.getName() + "@" + DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type);
        String fileUrl = expireVO.getFileUrl() + "@" + url;
        expireClient.add(code, fileName, fileUrl, time, "", ExpireType.Wages.getCode());

    }

    private static String getNewValue(String oldValue, String newValue) {
        return oldValue + "(" + newValue + ")";
    }

    /**
     * 获取租赁岗位连续工作时间
     * @param ruZhiZuLinTimeStr
     * @return
     */
    private Integer getContinuityWorkDay(String ruZhiZuLinTimeStr) {
        if(StringUtils.isEmpty(ruZhiZuLinTimeStr)) {
            return 0;
        }
        Date now = new Date();
        Date ruZhiZuLinTime = DateUtil.convert2Date(ruZhiZuLinTimeStr, BizUserConstant.DateFormat);
        Integer nowYear = DateUtil.getYear(now);
        Integer ruZhiZuLinTimeYear = DateUtil.getYear(ruZhiZuLinTime);
        return nowYear - ruZhiZuLinTimeYear;
    }


}
