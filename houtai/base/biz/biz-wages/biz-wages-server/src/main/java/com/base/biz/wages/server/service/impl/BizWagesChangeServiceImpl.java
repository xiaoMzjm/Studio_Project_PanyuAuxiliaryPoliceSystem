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
import com.base.biz.wages.client.enums.WageTypeEnum;
import com.base.biz.wages.client.enums.WagesChangeTypeEnum;
import com.base.biz.wages.client.enums.WagesReimbursementImportTypeEnum;
import com.base.biz.wages.client.model.WagesChangeVO;
import com.base.biz.wages.client.model.WagesReimbursementVO;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesChangeExcelRowDataDTO;
import com.base.biz.wages.server.model.WagesDO;
import com.base.biz.wages.server.service.BizWagesChangeService;
import com.base.biz.wages.server.service.BizWagesReimbursementService;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.common.util.NumberUtil;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/9/20 12:56 AM
 */
@Service
public class BizWagesChangeServiceImpl implements BizWagesChangeService {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizWagesManager bizWagesManager;

    @Autowired
    private ExpireClient expireClient;

    /**
     * 导入
     * @param time
     * @param webFile
     * @param type
     */
    @Override
    @Transactional
    public void importFile(Date time, MultipartFile webFile,  Integer type) throws Exception{
        Assert.notNull(time, "time is null");
        Assert.notNull(webFile, "file is null");
        Assert.notNull(type, "type is null");

        if(WagesChangeTypeEnum.CHANGE.getType().equals(type)) {
            this.importBase(time, webFile);
        }else {
            this.importCorrect(time, webFile);
        }

    }

    /**
     * 查询
     * @param time
     * @return
     */
    @Override
    public List<WagesChangeVO> list(Integer time) {
        Assert.notNull(time, "time is null");
        Date timeStart = DateUtil.convert2Date(String.valueOf(time),"yyyy");
        Date timeEnd = DateUtil.addYears(timeStart, 1);
        List<ExpireVO> expireVOList = expireClient.listByTime(timeStart, timeEnd, ExpireType.WagesChange.getCode());
        Assert.notNull(expireVOList, "查不到记录");

        List<WagesChangeVO> result = Lists.newArrayList();

        for(int i = 1 ;i <= 12;) {

            WagesChangeVO wagesChangeVO = new WagesChangeVO();
            result.add(wagesChangeVO);

            ExpireVO targetExpireVO = null;
            for(ExpireVO expireVO : expireVOList) {
                Date expireVOTime = expireVO.getTime();
                Integer month = DateUtil.getMonth(expireVOTime);
                if(month == i) {
                    targetExpireVO = expireVO;
                    break;
                }
            }
            if(targetExpireVO != null) {
                String names = targetExpireVO.getName();
                String code = targetExpireVO.getCode();

                String[] nameArray = names.split("@");

                wagesChangeVO.setImportWagesChangeCode(code + "@" + WagesReimbursementImportTypeEnum.REIMBURSEMENT.getType());
                wagesChangeVO.setImportWagesChangeName(nameArray[0]);

                wagesChangeVO.setSystemWagesChangeCode(code + "@" + WagesReimbursementImportTypeEnum.SYSTEM_REIMBURSEMENT.getType());
                wagesChangeVO.setSystemWagesChangeName(nameArray[1]);

                if(nameArray.length > 2) {
                    wagesChangeVO.setCorrectWagesChangeCode(code + "@" + WagesReimbursementImportTypeEnum.COLLECT_REIMBURSEMENT.getType());
                    wagesChangeVO.setCorrectWagesChangeName(nameArray[2]);
                }
            }
            i++;
        }

        return result;

    }

    /**
     * 导入报销封面
     * @param time
     * @param webFile
     * @throws Exception
     */
    private void importBase(Date time, MultipartFile webFile) throws Exception{
        // 保存上传的文件到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 查询指定日期的工资明细
        List<WagesDO> wagesDOListNow = bizWagesManager.listByTime(time);
        List<WagesDO> wagesDOListLast = bizWagesManager.listByTime(DateUtil.addMonths(time,-1));

        // 本月工资明细
        Map<String,List<WagesDO>> nowThreeWagesMap = new HashMap<>();
        Map<String,List<WagesDO>> nowFourWagesMap = new HashMap<>();
        for(WagesDO wagesDO : wagesDOListNow) {
            String departmentName = wagesDO.getDepartmentName();
            // 三级明细
            if(WageTypeEnum.THREE.getType().equals(wagesDO.getType())) {
                List<WagesDO> wagesDOList = nowThreeWagesMap.get(departmentName);
                if(wagesDOList == null) {
                    wagesDOList = Lists.newArrayList();
                }
                wagesDOList.add(wagesDO);
                nowThreeWagesMap.put(departmentName, wagesDOList);
            } else {
                // 四级明细
                List<WagesDO> wagesDOList = nowFourWagesMap.get(departmentName);
                if(wagesDOList == null) {
                    wagesDOList = Lists.newArrayList();
                }
                wagesDOList.add(wagesDO);
                nowFourWagesMap.put(departmentName, wagesDOList);
            }

        }

        // 上个月工资明细
        Map<String,List<WagesDO>> lastThreeWagesMap = new HashMap<>();
        Map<String,List<WagesDO>> lastFourWagesMap = new HashMap<>();
        for(WagesDO wagesDO : wagesDOListLast) {
            String departmentName = wagesDO.getDepartmentName();
            // 三级明细
            if(WageTypeEnum.THREE.getType().equals(wagesDO.getType())) {
                List<WagesDO> wagesDOList = lastThreeWagesMap.get(departmentName);
                if(wagesDOList == null) {
                    wagesDOList = Lists.newArrayList();
                }
                wagesDOList.add(wagesDO);
                lastThreeWagesMap.put(departmentName, wagesDOList);
            }else {
                List<WagesDO> wagesDOList = lastFourWagesMap.get(departmentName);
                if(wagesDOList == null) {
                    wagesDOList = Lists.newArrayList();
                }
                wagesDOList.add(wagesDO);
                lastFourWagesMap.put(departmentName, wagesDOList);
            }

        }

        InputStream targetExcel = new ClassPathResource("static/file/工资变动表.xlsx").getInputStream();
        String excelName = buildExcel(file,targetExcel, 0, nowThreeWagesMap, lastThreeWagesMap, time);
        targetExcel = new FileInputStream(new File(diskStaticUrl + "files/" + excelName));
        excelName = buildExcel(file, targetExcel, 1, nowFourWagesMap, lastFourWagesMap, time);


        // 如果expire表存在，则删除
        String code = ExpireType.WagesChange.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        expireClient.delete(code);

        // 存储到expire表中
        String savePath = diskStaticUrl + "files/";
        String fileName1 = DateUtil.convert2String(time, "yyyy年MM月") + "工资变动表";
        String fileName2 = DateUtil.convert2String(time, "yyyy年MM月") + "工资变动表(系统生成)";
        String url1 = url;
        String url2 = savePath + excelName;
        expireClient.add(code, fileName1 + "@" + fileName2, url1 + "@" + url2, time, "", ExpireType.WagesChange.getCode());
    }

    private String buildExcel(File file, InputStream targetExcel, Integer excelIndex , Map<String,List<WagesDO>> nowWagesMap ,Map<String,List<WagesDO>> lastWagesMap, Date time) throws Exception{

        // 读取三级岗excel
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(excelIndex);
        Iterator rows = sheet.rowIterator();
        List<WagesChangeExcelRowDataDTO> wagesChangeExcelRowDataDTOList = Lists.newArrayList();
        WagesChangeExcelRowDataDTO lastRowDTO = null;
        int rowNum = 1;

        while(rows.hasNext()) {
            if (rowNum <= 4) {
                rows.next();
                rowNum++;
                continue;
            }
            if(lastRowDTO != null) {
                break;
            }

            XSSFRow row = (XSSFRow) rows.next();
            if(row != null) {

                int num = row.getLastCellNum();
                // 遍历单元格
                Integer sequence = null;
                String departmentName = "";
                Integer nowPeopleNum = 0;
                Integer lastPeopleNum = 0;
                BigDecimal shouldPayMoney = new BigDecimal("0");
                BigDecimal realPayMoney = new BigDecimal("0");
                String changeDetail = "";
                boolean isLastRow = false;



                for (int i = 0; i < num && i <= 6; i++) {

                    XSSFCell cell = row.getCell(i);
                    if(cell == null) {
                        break;
                    }

                    // 序号
                    if(i == 0) {
                        try {
                            sequence = new Double(cell.getNumericCellValue()).intValue();
                        }catch (Exception e) {
                            System.out.println("最后一行");
                            isLastRow = true;

                        }
                    }

                    // 单位
                    if(i == 1) {
                        departmentName = cell.getStringCellValue();
                    }

                    // 本月人数
                    if(i == 2) {
                        nowPeopleNum = new Double(cell.getNumericCellValue()).intValue();
                    }

                    // 上月人数
                    if(i == 3) {
                        lastPeopleNum = new Double(cell.getNumericCellValue()).intValue();
                    }

                    // 应发金额
                    if(i == 4) {
                        shouldPayMoney = new BigDecimal(new Double(cell.getNumericCellValue()).toString());
                    }

                    // 实发金额
                    if(i == 5) {
                        realPayMoney = new BigDecimal(new Double(cell.getNumericCellValue()).toString());
                    }

                    // 变动情况
                    if(i == 6) {
                        changeDetail = cell.getStringCellValue();
                    }
                }

                // 取完一行的记录后
                WagesChangeExcelRowDataDTO wagesChangeExcelRowDataDTO = new WagesChangeExcelRowDataDTO(
                    sequence,
                    departmentName,
                    nowPeopleNum,
                    lastPeopleNum,
                    shouldPayMoney,
                    realPayMoney,
                    changeDetail
                );
                if(isLastRow ) {
                    lastRowDTO = wagesChangeExcelRowDataDTO;
                }else {
                    wagesChangeExcelRowDataDTOList.add(wagesChangeExcelRowDataDTO);
                }
            }
            rowNum++;
        }

        List<List<ExcelUtil.CellDTO>> rules = Lists.newArrayList();

        Integer allNumPeopleNum = 0;
        Integer allLastPeopleNum = 0;
        BigDecimal allShouldPayMoneyBD = new BigDecimal(0);
        BigDecimal allRealPayMoneyBD = new BigDecimal(0);

        for(int i = 0 ; i < wagesChangeExcelRowDataDTOList.size(); i++) {
            WagesChangeExcelRowDataDTO wagesChangeExcelRowDataDTO = wagesChangeExcelRowDataDTOList.get(i);

            String department = wagesChangeExcelRowDataDTO.getDepartmentName();
            CellDTO sequenceCell = new CellDTO(String.valueOf(i+1));
            CellDTO departmentCell = new CellDTO(department);

            // 本月人数
            Integer nowPeopleNum = 0;
            List<WagesDO> nowThreeWagesDOList = nowWagesMap.get(department);
            nowThreeWagesDOList = nowThreeWagesDOList == null ? Lists.newArrayList() : nowThreeWagesDOList;
            for(WagesDO wagesDO : nowThreeWagesDOList) {
                if(department.equals(wagesDO.getDepartmentName())) {
                    nowPeopleNum++;
                }
            }
            allNumPeopleNum += nowPeopleNum;
            CellDTO nowPeopleNumCell = null;
            if(!nowPeopleNum.equals(wagesChangeExcelRowDataDTO.getNowPeopleNum())) {
                nowPeopleNumCell = new CellDTO(getNewValue(wagesChangeExcelRowDataDTO.getNowPeopleNum(),nowPeopleNum));
                nowPeopleNumCell.color = Integer.valueOf(Font.COLOR_RED);
            }else {
                nowPeopleNumCell = new CellDTO(String.valueOf(nowPeopleNum));
            }


            // 上月人数
            Integer lastPeopleNum = 0;
            List<WagesDO> lastThreeWagesDOList = lastWagesMap.get(department);
            lastThreeWagesDOList = lastThreeWagesDOList == null ? Lists.newArrayList() : lastThreeWagesDOList;
            for(WagesDO wagesDO : lastThreeWagesDOList) {
                if(department.equals(wagesDO.getDepartmentName())) {
                    lastPeopleNum++;
                }
            }
            allLastPeopleNum += lastPeopleNum;
            CellDTO lastPeopleNumCell = null;
            if(!lastPeopleNum.equals(wagesChangeExcelRowDataDTO.getLastPeopleNum())) {
                lastPeopleNumCell = new CellDTO(getNewValue(wagesChangeExcelRowDataDTO.getLastPeopleNum(), lastPeopleNum));
                lastPeopleNumCell.color = Integer.valueOf(Font.COLOR_RED);
            }else {
                lastPeopleNumCell = new CellDTO(String.valueOf(lastPeopleNum));
            }


            // 应发金额
            Integer shouldPayMoney = 0;
            for(WagesDO wagesDO : nowThreeWagesDOList) {
                if(department.equals(wagesDO.getDepartmentName())) {
                    shouldPayMoney += wagesDO.getWagesPayable();
                }
            }
            BigDecimal shouldPayMoneyBigDecimal = new BigDecimal(shouldPayMoney).divide(new BigDecimal(1000)).setScale(2,BigDecimal.ROUND_HALF_UP);
            allShouldPayMoneyBD = allShouldPayMoneyBD.add(shouldPayMoneyBigDecimal);
            CellDTO shouldPayMoneyCell = null;
            if(!shouldPayMoneyBigDecimal.equals(wagesChangeExcelRowDataDTO.getShouldPayMoney().setScale(2,BigDecimal.ROUND_HALF_UP))) {
                shouldPayMoneyCell = new CellDTO(getNewValue(wagesChangeExcelRowDataDTO.getShouldPayMoney(), shouldPayMoneyBigDecimal));
                shouldPayMoneyCell.color = Integer.valueOf(Font.COLOR_RED);
            }else {
                shouldPayMoneyCell = new CellDTO(shouldPayMoneyBigDecimal.toString());
            }

            // 实发金额
            Integer realPayMoney = 0;
            for(WagesDO wagesDO : nowThreeWagesDOList) {
                if(department.equals(wagesDO.getDepartmentName())) {
                    realPayMoney += wagesDO.getRealWages();
                }
            }
            BigDecimal realPayMoneyBigDecimal = new BigDecimal(realPayMoney).divide(new BigDecimal(1000)).setScale(2,BigDecimal.ROUND_HALF_UP);
            allRealPayMoneyBD = allRealPayMoneyBD.add(realPayMoneyBigDecimal);
            CellDTO realPayMoneyCell = null;
            if(!realPayMoneyBigDecimal.equals(wagesChangeExcelRowDataDTO.getRealPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP))) {
                realPayMoneyCell = new CellDTO(getNewValue(wagesChangeExcelRowDataDTO.getRealPayMoney(), realPayMoneyBigDecimal));
                realPayMoneyCell.color = Integer.valueOf(Font.COLOR_RED);
            }else {
                realPayMoneyCell = new CellDTO(realPayMoneyBigDecimal.toString());
            }

            List<ExcelUtil.CellDTO> row = Lists.newArrayList();

            // 变动情况
            CellDTO changeCell = null;
            Integer changePeople = nowPeopleNum - lastPeopleNum;
            String changeStr = "";
            if(changePeople.equals(0)) {
                changeStr = "本月无人员变动";
            }
            if(changePeople < 0) {
                changeStr = "本月减员" + (-changePeople) + "人";
            }
            if(changePeople > 0) {
                changeStr = "本月增员" + changePeople + "人";
            }
            if(!changeStr.equals(wagesChangeExcelRowDataDTO.getChangeDetail())) {
                changeCell = new CellDTO(getNewValue(wagesChangeExcelRowDataDTO.getChangeDetail(), changeStr));
                changeCell.color = Integer.valueOf(Font.COLOR_RED);
            }else {
                changeCell = new CellDTO(wagesChangeExcelRowDataDTO.getChangeDetail());
            }

            row.add(sequenceCell);
            row.add(departmentCell);
            row.add(nowPeopleNumCell);
            row.add(lastPeopleNumCell);
            row.add(shouldPayMoneyCell);
            row.add(realPayMoneyCell);
            row.add(changeCell);
            row.add(new CellDTO(""));

            rules.add(row);
        }

        Map<String,CellDTO> replaceMap = new HashMap<>();
        replaceMap.put("allNowPeopleNum", new CellDTO(String.valueOf(allNumPeopleNum)));
        replaceMap.put("allLastPeopleNum", new CellDTO(String.valueOf(allLastPeopleNum)));
        replaceMap.put("allShouldPayMoney", new CellDTO(allShouldPayMoneyBD.toString()));
        replaceMap.put("allRealPayMoney", new CellDTO(allRealPayMoneyBD.toString()));
        replaceMap.put("date", new CellDTO(DateUtil.convert2String(time, "yyyy年MM月份")));

        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, excelIndex, 3, 0, savePath, rules, replaceMap);
        System.out.println(excelName);
        return excelName;
    }

    private String getNewValue(String value1 , String value2) {
        return  value1.toString() + "(" + value2.toString() + ")";
    }

    private String getNewValue(BigDecimal value1 , BigDecimal value2) {
        return  value1.toString() + "(" + value2.toString() + ")";
    }

    private String getNewValue(Integer value1 , Integer value2) {
        return  value1 + "(" + value2 + ")";
    }

    /**
     * 导入正确的报销封面
     * @param time
     * @param webFile
     * @throws Exception
     */
    private void importCorrect(Date time, MultipartFile webFile) throws Exception{
        // 保存上传的文件到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 查询expire表
        String code = ExpireType.WagesChange.getCode() + "-" + DateUtil.convert2String(time, "yyyy-MM");
        ExpireVO expireVO = expireClient.getByCode(code);
        Assert.notNull(expireVO, "请先上传原始文件");

        // 修改expire表
        String oldName = expireVO.getName();
        String oldUrl = expireVO.getFileUrl();

        String[] oldNameArray = oldName.split("@");
        if(oldNameArray.length == 3) {
            oldName = oldNameArray[0] + "@" + oldNameArray[1];
        }
        String[] oldUrlArray = oldUrl.split("@");
        if(oldUrlArray.length == 3) {
            oldUrl = oldUrlArray[0] + "@" + oldUrlArray[1];
        }

        String newName = oldName + "@" + DateUtil.convert2String(time, "yyyy年MM月") + "工资变工表(正确)";
        String newUrl = oldUrl + "@" + url;


        expireClient.delete(code);
        expireClient.add(code, newName, newUrl, time, "", ExpireType.WagesChange.getCode());

    }
}
