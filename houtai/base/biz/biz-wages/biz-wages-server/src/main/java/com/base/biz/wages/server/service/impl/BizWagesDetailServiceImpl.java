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

import com.alibaba.fastjson.JSON;

import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.client.BizUserClient;
import com.base.biz.wages.client.enums.WageTypeEnum;
import com.base.biz.wages.client.enums.WagesDetailImportTypeEnum;
import com.base.biz.wages.client.model.WageListVO;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import com.base.biz.wages.server.service.BizWagesDetailService;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
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
public class BizWagesDetailServiceImpl implements BizWagesDetailService {

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

    @Override
    public List<WageListVO> list(Integer year, Integer type) {
        Assert.notNull(year, "year is null");
        Assert.isTrue(WageTypeEnum.THREE.getType().equals(type) ||
            WageTypeEnum.FOUR.getType().equals(type), "type error");


        // 年初时间
        Date begin = DateUtil.getFirstDayOfYear(year);
        // 下一年初时间
        Date end = DateUtil.addYears(begin, 1);
        // 查询整年的记录
        if(WageTypeEnum.THREE.getType().equals(type)) {
            type = ExpireType.WagesThree.getCode();
        }else {
            type = ExpireType.WagesFour.getCode();
        }
        List<ExpireVO> expireVOList = expireClient.selectByTime(begin, end, type);

        List<WageListVO> result = Lists.newArrayList();
        for(int i = 1 ; i <= 12 ; i++) {
            WageListVO wageListVO = new WageListVO();

            Date monthBegin = DateUtil.getFirstDayOfMonth(year, i);
            Date nextMonthBegin = DateUtil.addMonths(monthBegin, 1);

            if(CollectionUtils.isNotEmpty(expireVOList)) {
                for(ExpireVO expireVO : expireVOList) {
                    if(monthBegin.getTime() <= expireVO.getTime().getTime() &&
                        expireVO.getTime().getTime() < nextMonthBegin.getTime()) {
                        String nameArrayStr = expireVO.getName();
                        String urlArrayStr = expireVO.getFileUrl();
                        String[] nameArray = nameArrayStr.split("@");
                        String[] urlArray = urlArrayStr.split("@");
                        Assert.isTrue(nameArray.length >= 2, "nameArray length error");
                        Assert.isTrue(urlArray.length >= 2, "urlArray length error");
                        wageListVO.setImportReportName(nameArray[0]);
                        wageListVO.setSystemReportName(nameArray[1]);
                        if(nameArray.length > 2 && urlArray.length > 2) {
                            wageListVO.setCorrectReportName(nameArray[2]);
                        }
                        wageListVO.setCode(expireVO.getCode());
                    }
                }
            }
            result.add(wageListVO);
        }

        return result;
    }

    /**
     * 上传初始的工资明细
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     * @throws Exception
     */
    private void importBaseDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{

        Assert.isTrue(WagesDetailImportTypeEnum.THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.FOUR.getValue().equals(type), "type error");

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

        // 遍历行，获取全部的身份证，然后一次查询所有身份信息，供后面使用
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

        // 根据身份证集合，查询人员集合
        Map<String,BizUserDetailVO> code2UserMap = bizUserClient.listByCodeList(identityCodeList);

        // 重新读取excel
        sheet = wb.getSheetAt(0);
        rows = sheet.rowIterator();

        // 初始化统计字段
        BigDecimal basePay = new BigDecimal(0.0); // 基本工资
        BigDecimal allowance = new BigDecimal(0.0); // 连续租赁岗位津贴
        BigDecimal promotionMoney = new BigDecimal(0.0); // 符合晋升待遇
        BigDecimal wagesPayable = new BigDecimal(0.0); // 应发工资合计
        BigDecimal departmentSocialSecurityMoney = new BigDecimal(0.0); // 单位扣缴社保费
        BigDecimal personalSocialSecurityMoney = new BigDecimal(0.0); // 个人扣缴社保费
        BigDecimal departmentAccumulat = new BigDecimal(0.0); // 单位扣缴公积金费
        BigDecimal personalAccumulationFund = new BigDecimal(0.0); // 个人扣缴公积金费
        BigDecimal personalIncomeTax = new BigDecimal(0.0); // 应缴个税金额
        BigDecimal realWages = new BigDecimal(0.0); // 实发工资合计

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
                if(WagesDetailImportTypeEnum.THREE.getValue().equals(type)) {
                    wagesDO.setType(WageTypeEnum.THREE.getType());
                }else {
                    wagesDO.setType(WageTypeEnum.FOUR.getType());
                }

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
                            basePay = basePay.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            allowance = allowance.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            promotionMoney = promotionMoney.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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

                            wagesPayable = wagesPayable.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));

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
                            departmentSocialSecurityMoney = departmentSocialSecurityMoney.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            personalSocialSecurityMoney = personalSocialSecurityMoney.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            departmentAccumulat = departmentAccumulat.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            personalAccumulationFund = personalAccumulationFund.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            personalIncomeTax = personalIncomeTax.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
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
                            realWages = realWages.add(new BigDecimal(String.valueOf(cell.getNumericCellValue())));

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

                }
                if(!StringUtils.isEmpty(wagesDO.getName())) {
                    wagesDOList.add(wagesDO);
                    rules.add(cellDTOList);
                }
            }
        }

        // 删除工资数据
        bizWagesManager.deleteByIdentityList(identityCodeList);

        // 保存工资到db
        bizWagesManager.batchSave(wagesDOList);

        // 保存新的excel
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("basePay" , basePay.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("allowance" , allowance.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("promotionMoney" , promotionMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("wagesPayable" , wagesPayable.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("departmentSocialSecurityMoney" , departmentSocialSecurityMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalSocialSecurityMoney" , personalSocialSecurityMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("departmentAccumulat" , departmentAccumulat.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalAccumulationFund" , personalAccumulationFund.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("personalIncomeTax" , personalIncomeTax.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("realWages" , realWages.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("date" , DateUtil.getCurrentDateStr("yyyy年MM月dd日"));
        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, 3, 0, savePath, rules, replaceMap);

        // 删除expire记录
        String code = "";
        if(WagesDetailImportTypeEnum.THREE.getValue().equals(type)) {
            code = ExpireType.WagesThree.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        }else {
            code = ExpireType.WagesFour.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        }
        expireClient.delete(code);

        // 重新保存expire记录
        String fileName1 = DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type);
        String fileName2 = DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type + 2);
        String url1 = url;
        String  url2 = savePath + excelName;
        if(WagesDetailImportTypeEnum.THREE.getValue().equals(type)) {
            expireClient.add(code, fileName1 + "@" + fileName2 , url1 + "@" + url2, time, "", ExpireType.WagesThree.getCode());
        }else {
            expireClient.add(code, fileName1 + "@" + fileName2 , url1 + "@" + url2, time, "", ExpireType.WagesFour.getCode());
        }

    }

    /**
     * 上传正确的工资明细
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     * @throws Exception
     */
    private void importCorrectDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type)
        throws Exception {

        Assert.isTrue(WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.CORRECT_FOUR.getValue().equals(type), "type error");

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
                if(WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type)) {
                    wagesDO.setType(WageTypeEnum.THREE.getType());
                }else {
                    wagesDO.setType(WageTypeEnum.FOUR.getType());
                }


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

        // 删除wages数据
        bizWagesManager.deleteByIdentityList(wagesDOList.stream().map(WagesDO::getIdentityCard).collect(Collectors.toList()));

        // 保存到db
        bizWagesManager.batchSave(wagesDOList);

        String code = "";
        if(WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type)) {
            code = ExpireType.WagesThree.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        }else {
            code = ExpireType.WagesFour.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        }
        ExpireVO expireVO = expireClient.findByCode(code);
        if(expireVO == null) {
            throw new BaseException("请先上传工资明细表");
        }

        // 保存excel到db
        String[] nameArray = expireVO.getName().split("@");
        String[] fileUrlArray = expireVO.getFileUrl().split("@");
        String fileName = "";
        String fileUrl = "";
        if(nameArray.length == 2) {
            fileName = expireVO.getName() + "@" + DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type);
            fileUrl = expireVO.getFileUrl() + "@" + url;
        }else {
            fileName = nameArray[0] + "@" + nameArray[1] + DateUtil.convert2String(time , "yyyy年MM月") + WagesDetailImportTypeEnum.getName(type);
            fileUrl = fileUrlArray[0] + "@" + fileUrlArray[1] + url;
        }

        // 保存expire
        if(WagesDetailImportTypeEnum.CORRECT_THREE.getValue().equals(type)) {
            expireClient.add(code, fileName, fileUrl, time, "", ExpireType.WagesThree.getCode());
        }else {
            expireClient.add(code, fileName, fileUrl, time, "", ExpireType.WagesFour.getCode());
        }

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
