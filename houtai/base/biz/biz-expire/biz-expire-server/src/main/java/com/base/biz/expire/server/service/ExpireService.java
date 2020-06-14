package com.base.biz.expire.server.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.biz.expire.client.common.ExpireEnums;
import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.server.dao.ExpireDAO;
import com.base.biz.expire.server.manager.ExpireManager;
import com.base.biz.expire.server.model.ExpireDO;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.biz.user.client.common.Enums;
import com.base.biz.user.client.common.Enums.SexEnum;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserService;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.WordUtil.RowCellDTO;
import com.base.common.util.WordUtil.RowDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/5/24 2:46 PM
 */
@Service
public class ExpireService {

    @Autowired
    private ExpireManager expireManager;
    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;
    @Autowired
    private BizUserService bizUserService;

    /**
     * 获取各种到期提醒列表
     */
    public List<ExpireVO> getByTime(Integer year, Integer type) throws Exception{

        Date now = new Date();
        Integer nowMonth = DateUtil.getMonth(now);
        Integer nowYear = DateUtil.getYear(now);

        if(type == ExpireType.EmployeeCard.getCode()) {
            for(int i = 1 ; i <= nowMonth ;i++) {
                ClassPathResource classPathResource = new ClassPathResource("static/file/工作证到期提醒表.xlsx");
                InputStream inputStream = classPathResource.getInputStream();
                createEmployeeCard(year, i, inputStream, false);
            }
        }
        if(type == ExpireType.Contract.getCode()) {
            for(int i = 1 ; i <= nowMonth ;i++) {
                ClassPathResource classPathResource = new ClassPathResource("static/file/合同到期提醒表.xlsx");
                InputStream inputStream = classPathResource.getInputStream();
                createContract(year, i, inputStream, false);
            }
        }
        if(type == ExpireType.Retire.getCode()) {
            for(int i = 1 ; i <= nowMonth ;i++) {
                ClassPathResource classPathResource = new ClassPathResource("static/file/退休提醒表.xlsx");
                InputStream inputStream = classPathResource.getInputStream();
                createRetire(year, i, inputStream, false);
            }
        }

        Date start = DateUtil.getFirstDayOfYear(year);
        Date end = DateUtil.addYears(start, 1);
        List<ExpireDO> expireDOList = expireManager.getByTime(start, end, type);
        List<ExpireVO> expireVOList = Lists.newArrayList();
        Integer size = 0;
        if(CollectionUtils.isNotEmpty(expireDOList)) {
            size = expireDOList.size();
        }
        int j = 0;
        for (int i = 0 ; i < 12 ; i ++) {
            Boolean isAdd = false;
            if (j <= size - 1) {
                ExpireDO expireDO = expireDOList.get(j);
                Integer month = DateUtil.getMonth(expireDO.getTime());
                if (month == i + 1) {
                    ExpireVO expireVO = new ExpireVO(expireDO.getFileName(), expireDO.getCode());
                    expireVOList.add(expireVO);
                    j++;
                    isAdd = true;
                }
            }
            if (!isAdd) {
                if(year < nowYear) {
                    expireVOList.add(new ExpireVO("无满足条件的人员"));
                } else if(year > nowYear) {
                    expireVOList.add(new ExpireVO());
                } else if(i < nowMonth) {
                    expireVOList.add(new ExpireVO("无满足条件的人员"));
                } else {
                    expireVOList.add(new ExpireVO());
                }

            }
        }

        return expireVOList;
    }

    /**
     * 创建工作证到期提醒
     */
    public String createEmployeeCard(Integer year, Integer month, InputStream inputStream, Boolean replace ) throws Exception{

        String code = getEmployeeCode(year, month);
        ExpireDO expireDO = expireManager.getByCode(code);
        if (expireDO != null) {
            if(!replace) {
                return null;
            }
            String fileUrl = expireDO.getFileUrl();
            File oldFile = new File(fileUrl);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(year,month);
        Date firstDayOfTwoMonthLater = DateUtil.addMonths(firstDayOfMonth , 2);
        Date firstDayOfThreeMonthLater = DateUtil.addMonths(firstDayOfMonth , 3);
        Date firstDayOfTwoMonthLaterOfThreeYearAgo = DateUtil.addYears(firstDayOfTwoMonthLater, -3);
        Date firstDayOfThreeMonthLaterOfThreeYearAgo = DateUtil.addYears(firstDayOfThreeMonthLater, -3);
        List<BizUserDetailVO> bizUserDetailVOList = bizUserService.getByWorkCardBeginTime(firstDayOfTwoMonthLaterOfThreeYearAgo,
            firstDayOfThreeMonthLaterOfThreeYearAgo);
        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
            List<List<CellDTO>> rules = Lists.newArrayList();
            int i = 1;

            for(BizUserDetailVO vo : bizUserDetailVOList) {
                List<CellDTO> row = Lists.newArrayList();
                row.add(new CellDTO(String.valueOf(i)));
                row.add(new CellDTO(vo.getName()));
                row.add(new CellDTO(vo.getSexStr()));
                row.add(new CellDTO(vo.getJobGradeStr()));
                row.add(new CellDTO(vo.getWorkUnitName()));
                row.add(new CellDTO(vo.getIdentityCard()));
                row.add(new CellDTO(vo.getPoliceCode()));
                row.add(new CellDTO(vo.getBirthdate()));
                row.add(new CellDTO(vo.getWorkCardBeginTime()));

                String workCardBeginTimeStr = vo.getWorkCardBeginTime();
                Date workCardBeginTime = DateUtil.convert2Date(workCardBeginTimeStr, "yyyy/MM/dd");
                Date workCardBeginTimeAddThreeYear = DateUtil.addYears(workCardBeginTime, 3);
                String workCardBeginTimeAddThreeYearStr = DateUtil.convert2String(workCardBeginTimeAddThreeYear, "yyyy/MM/dd");
                row.add(new CellDTO(workCardBeginTimeAddThreeYearStr));
                rules.add(row);
                i++;
            }
            String savePath = diskStaticUrl + "files/";
            try {
                String wordName = ExcelUtil.insertExcelAndSave(inputStream, 2, 0, savePath, rules);
                String fileUrl = savePath + wordName;
                String fileName = DateUtil.convert2String(DateUtil.getFirstDayOfMonth(year,month), "yyyy-MM");
                expireManager.add(code, fileName, fileUrl, DateUtil.getFirstDayOfMonth(year,month), ExpireType.EmployeeCard.getCode());
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
            return null;
        }
        return "没有人员工作证到期";
    }

    /**
     * 创建合同到期提醒
     * @param year
     * @param month
     * @param inputStream
     * @return
     * @throws Exception
     */
    public String createContract(Integer year, Integer month, InputStream inputStream, Boolean replace) throws Exception{
        String code = getContractCode(year, month);
        ExpireDO expireDO = expireManager.getByCode(code);
        if (expireDO != null) {
            if(!replace) {
                return null;
            }
            String fileUrl = expireDO.getFileUrl();
            File oldFile = new File(fileUrl);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        Date start = DateUtil.addMonths(DateUtil.getFirstDayOfMonth(year,month), 1);
        Date end = DateUtil.addMonths(DateUtil.getFirstDayOfMonth(year,month), 2);

        List<BizUserDetailVO> bizUserDetailVOList = bizUserService.getByContractEngTime(start,end);

        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
            List<List<CellDTO>> rules = Lists.newArrayList();
            int i = 1;

            for(BizUserDetailVO vo : bizUserDetailVOList) {
                List<CellDTO> row = Lists.newArrayList();
                row.add(new CellDTO(String.valueOf(i)));
                row.add(new CellDTO(vo.getWorkUnitName()));
                row.add(new CellDTO(vo.getName()));
                row.add(new CellDTO(vo.getSexStr()));
                row.add(new CellDTO(vo.getIdentityCard()));
                row.add(new CellDTO(vo.getPhone()));

                row.add(new CellDTO(vo.getFirstContractBeginTime()));
                row.add(new CellDTO(vo.getFirstContractEngTime()));

                row.add(new CellDTO(vo.getSecondContractBeginTime()));
                row.add(new CellDTO(vo.getSecondContractEngTime()));

                row.add(new CellDTO(vo.getThirdContractBeginTime()));
                row.add(new CellDTO(vo.getThirdContractEngTime(),Integer.valueOf(Font.COLOR_RED)));

                if(DateUtil.convert2Date(vo.getFirstContractEngTime(),"yyyy/MM/dd").getTime() >= start.getTime() &&
                    DateUtil.convert2Date(vo.getFirstContractEngTime(),"yyyy/MM/dd").getTime() < end.getTime()) {
                    row.add(new CellDTO("第一次"));
                }
                if(DateUtil.convert2Date(vo.getSecondContractEngTime(),"yyyy/MM/dd").getTime() >= start.getTime() &&
                    DateUtil.convert2Date(vo.getSecondContractEngTime(),"yyyy/MM/dd").getTime() < end.getTime()) {
                    row.add(new CellDTO("第二次"));
                }
                if(DateUtil.convert2Date(vo.getThirdContractEngTime(),"yyyy/MM/dd").getTime() >= start.getTime() &&
                    DateUtil.convert2Date(vo.getThirdContractEngTime(),"yyyy/MM/dd").getTime() < end.getTime()) {
                    row.add(new CellDTO("第三次"));
                }

                rules.add(row);
                i++;
            }
            String savePath = diskStaticUrl + "files/";
            try {
                String wordName = ExcelUtil.insertExcelAndSave(inputStream, 2, 0, savePath, rules);
                String fileUrl = savePath + wordName;
                String fileName = DateUtil.convert2String(DateUtil.getFirstDayOfMonth(year,month), "yyyy-MM");
                expireManager.add(code, fileName, fileUrl, DateUtil.getFirstDayOfMonth(year,month), ExpireType.Contract.getCode());
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
            return null;
        }
        return "没有人员合同到期";

    }

    /**
     * 创建退休期提醒
     * @param year
     * @param month
     * @param inputStream
     * @return
     * @throws Exception
     */
    public String createRetire(Integer year, Integer month, InputStream inputStream, Boolean replace) throws Exception{
        String code = getExpireCode(year, month);
        ExpireDO expireDO = expireManager.getByCode(code);
        if (expireDO != null) {
            if(!replace) {
                return null;
            }
            String fileUrl = expireDO.getFileUrl();
            File oldFile = new File(fileUrl);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        Date firstMonth = DateUtil.addMonths(DateUtil.getFirstDayOfMonth(year, month), 1);
        Date secondMonth =  DateUtil.addMonths(DateUtil.getFirstDayOfMonth(year, month), 2);

        Date start = DateUtil.addYears(firstMonth , -60);
        Date end = DateUtil.addYears(secondMonth , -60);
        List<BizUserDetailVO> allList = Lists.newArrayList();
        List<BizUserDetailVO> bizUserDetailVOList = bizUserService.getByBirthDayAndSex(start,end, SexEnum.MAN.getCode());
        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)){
            allList.addAll(bizUserDetailVOList);
        }

        start = DateUtil.addYears(firstMonth , -50);
        end = DateUtil.addYears(secondMonth , -50);
        List<BizUserDetailVO> bizUserDetailVOList2 = bizUserService.getByBirthDayAndSex(start,end, SexEnum.MALE.getCode());
        if(CollectionUtils.isNotEmpty(bizUserDetailVOList2)){
            allList.addAll(bizUserDetailVOList2);
        }


        if(CollectionUtils.isNotEmpty(allList)) {
            List<List<CellDTO>> rules = Lists.newArrayList();
            int i = 1;

            for(BizUserDetailVO vo : allList) {
                List<CellDTO> row = Lists.newArrayList();
                row.add(new CellDTO(String.valueOf(i)));
                row.add(new CellDTO(vo.getWorkUnitName()));
                row.add(new CellDTO(vo.getName()));
                row.add(new CellDTO(vo.getSexStr()));
                row.add(new CellDTO(vo.getIdentityCard()));
                row.add(new CellDTO(vo.getJobGradeStr()));

                row.add(new CellDTO(String.valueOf(vo.getAge())));

                Date birthdayAdd1Month = DateUtil.convert2Date(vo.getBirthdate(), "yyyy/MM/dd");
                if(vo.getSex() == SexEnum.MAN.getCode()) {
                    Date expire = DateUtil.addYears(birthdayAdd1Month, 60);
                    row.add(new CellDTO(DateUtil.convert2String(expire, "yyyy/MM/dd")));
                }
                if(vo.getSex() == SexEnum.MALE.getCode()) {
                    Date expire = DateUtil.addYears(birthdayAdd1Month, 50);
                    row.add(new CellDTO(DateUtil.convert2String(expire, "yyyy/MM/dd")));
                }

                rules.add(row);
                i++;
            }
            String savePath = diskStaticUrl + "files/";
            try {
                String wordName = ExcelUtil.insertExcelAndSave(inputStream, 2, 0, savePath, rules);
                String fileUrl = savePath + wordName;
                String fileName = DateUtil.convert2String(DateUtil.getFirstDayOfMonth(year,month), "yyyy-MM");
                expireManager.add(code, fileName, fileUrl, DateUtil.getFirstDayOfMonth(year,month), ExpireType.Retire.getCode());
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
            return null;
        }
        return "没有人员退休到期";

    }




    // 获取工作证code
    private String getEmployeeCode(Integer year, Integer month) {
        String code = ExpireType.EmployeeCard.getCode() + "_" + String.valueOf(year) + "_" + String.valueOf(month);
        return code;
    }

    private String getContractCode(Integer year, Integer month) {
        String code = ExpireType.Contract.getCode() + "_" + String.valueOf(year) + "_" + String.valueOf(month);
        return code;
    }

    private String getExpireCode(Integer year, Integer month) {
        String code = ExpireType.Retire.getCode() + "_" + String.valueOf(year) + "_" + String.valueOf(month);
        return code;
    }

}
