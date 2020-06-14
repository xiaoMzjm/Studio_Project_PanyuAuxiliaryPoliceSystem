package com.base.biz.user.server.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author:小M
 * @date:2020/4/19 4:20 PM
 */
public class BizUserAddExcelReader {

    /**
     * 读取excel
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<BizUserAddParam> readExcel(InputStream inputStream) throws Exception {
        Workbook workbook = getWorkbook(inputStream);
        List<BizUserAddParam> result = Lists.newArrayList();
        try {
            result = parseExcel(workbook);
        }finally {
            workbook.close();
            inputStream.close();
        }
        return result;
    }

    /**
     * https://www.cnblogs.com/Dreamer-1/p/10469430.html
     * @param inputStream
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        return workbook;
    }

    /**
     * 解析excel
     * @param workbook
     * @return
     * @throws Exception
     */
    private static List<BizUserAddParam> parseExcel(Workbook workbook) throws Exception{
        List<BizUserAddParam> result = Lists.newArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new BaseException("表格第一个sheet不存在，请检查excel格式。");
        }
        int firstRowNum = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNum);
        if (null == firstRow) {
            throw new BaseException("表格没有数据，请检查excel格式。");
        }
        int rowStart = firstRowNum + 1;
        int rowEnd = sheet.getPhysicalNumberOfRows();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (null == row) {
                continue;
            }
            BizUserAddParam bizUserAddParam = convertRowToData(row);
            if(bizUserAddParam != null && StringUtils.isNotEmpty(bizUserAddParam.identityCard)) {
                result.add(bizUserAddParam);
            }
        }

        return result;
    }

    private static BizUserAddParam convertRowToData(Row row){
        BizUserAddParam bizUserAddParam = new BizUserAddParam();
        Cell cell;
        int cellNum = 1;

        // 姓名
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.name = value;
        }
        // 出生日期
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.birthdate = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.birthdate = value;
            }

        }
        // 民族
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.nationStr = value;
        }
        // 政治面貌
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.politicalLandscapeStr = value;
        }
        // 毕业院校
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.graduateInstitutions = value;
        }
        // 警号
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.policeCode = value;
        }
        // 准驾车型
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.quasiDrivingTypeStr = value;
        }
        // 特殊人员
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.specialPeopleStr = value;
        }
        // 资格证书
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.qualification = value;
        }
        // 户籍地址
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.permanentResidenceAddress = value;
        }
        // 家庭地址
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.familyAddress = value;
        }
        // 性别
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.sexStr = value;
        }
        // 籍贯
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.nativePlace = value;
        }
        // 学历
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.educationStr = value;
        }
        // 专业
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.major = value;
        }
        // 婚姻状况
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.maritalStatusStr = value;
        }
        // 身份证
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.identityCard = value;
        }
        // 手机
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.phone = value;
        }
        // 人员类别
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.personnelTypeStr = value;
        }
        // 编制类别
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.authorizedStrengthTypeStr = value;
        }
        // 工作岗位
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.placeOfWorkStr = value;
        }
        // 职级
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.jobGradeStr = value;
        }
        // 待遇级别
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.treatmentGradeStr = value;
        }
        // 招录方式
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.enrollWayStr = value;
        }
        // 参加工作时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.beginWorkTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.beginWorkTime = value;
            }

        }
        // 合同生效时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.effectiveDateOfTheContract = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.effectiveDateOfTheContract = value;
            }

        }
        // 工作单位
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.workUnitName = value;
        }
        // 编制单位
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.organizationUnitName = value;
        }
        // 岗位类别
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.jobCategoryStr = value;
        }
        // 职务
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.duty = value;
        }
        // 社保编码
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.socialSecurityNumber = value;
        }
        // 入职公安时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.beginPoliceWorkTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.beginPoliceWorkTime = value;
            }
        }
        // 合同失效时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.contractExpirationDate = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.contractExpirationDate = value;
            }
        }
        // 离职时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.dimissionDate = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.dimissionDate = value;
            }
        }
        // 离职原因
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.dimissionReason = value;
        }
        // 任一级辅警起算时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.firstGradeTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.firstGradeTime = value;
            }
        }
        // 工作证起始日期
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.workCardBeginTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.workCardBeginTime = value;
            }

        }
        // 第一次合同生效时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.firstContractBeginTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.firstContractBeginTime = value;
            }
        }
        // 第一次合同终止时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.firstContractEngTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.firstContractEngTime = value;
            }

        }
        // 第二次合同生效时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.secondContractBeginTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.secondContractBeginTime = value;
            }

        }
        // 第二次合同终止时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.secondContractEngTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.secondContractEngTime = value;
            }

        }
        // 第三次合同生效时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.thirdContractBeginTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.thirdContractBeginTime = value;
            }

        }
        // 第三次合同终止时间
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.thirdContractEngTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.thirdContractEngTime = value;
            }

        }
        // 到期合同
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.dueContractStr = value;
        }
        // 工商银行账号
        cell = row.getCell(cellNum++);
        if(cell != null) {
            String value = cell.getStringCellValue();
            bizUserAddParam.icbcCardAccount = value;
        }
        // 入职租赁日期
        cell = row.getCell(cellNum++);
        if(cell != null) {
            try {
                Date value = cell.getDateCellValue();
                bizUserAddParam.ruZhiZuLinTime = DateUtil.convert2String(value, BizUserConstant.DateFormat);
            }catch (Exception e) {
                String value = cell.getStringCellValue();
                bizUserAddParam.ruZhiZuLinTime = value;
            }

        }
        return bizUserAddParam;
    }

}
