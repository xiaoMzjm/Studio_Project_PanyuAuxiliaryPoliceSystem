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
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserService;
import com.base.common.util.DateUtil;
import com.base.common.util.WordUtil.RowCellDTO;
import com.base.common.util.WordUtil.RowDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    public List<ExpireVO> getByTime(Integer year, Integer type){
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
                expireVOList.add(new ExpireVO());

            }
        }

        return expireVOList;
    }

    /**
     * 查询该时间下有没有记录，有的话，把旧的文件删除，生成新的文件，然后更新数据库
     */
    public String createEmployeeCard(Integer year, Integer month, ExpireEnums.ExpireType type, InputStream inputStream) throws Exception{

        String code = getEmployeeCode(year, month);
        ExpireDO expireDO = expireManager.getByCode(code);
        if (expireDO != null) {
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
            List<List<String>> rules = Lists.newArrayList();
            int i = 1;
            List<String> row = Lists.newArrayList();
            for(BizUserDetailVO vo : bizUserDetailVOList) {
                row.add(String.valueOf(i));
                row.add(vo.getName());
                row.add(vo.getSexStr());
                row.add(vo.getJobGradeStr());
                row.add(vo.getWorkUnitName());
                row.add(vo.getIdentityCard());
                row.add(vo.getPoliceCode());
                row.add(vo.getBirthdate());
                row.add(vo.getWorkCardBeginTime());

                String workCardBeginTimeStr = vo.getWorkCardBeginTime();
                Date workCardBeginTime = DateUtil.convert2Date(workCardBeginTimeStr, "yyyy/MM/dd");
                Date workCardBeginTimeAddThreeYear = DateUtil.addYears(workCardBeginTime, 3);
                String workCardBeginTimeAddThreeYearStr = DateUtil.convert2String(workCardBeginTimeAddThreeYear, "yyyy/MM/dd");
                row.add(workCardBeginTimeAddThreeYearStr);
                rules.add(row);
                i++;
            }
            String savePath = diskStaticUrl + "files/";
            try {
                String wordName = ExcelUtil.insertExcelAndSave(inputStream, 2, savePath, rules);
                String fileUrl = savePath + wordName;
                String fileName = DateUtil.convert2String(firstDayOfMonth, "yyyy-MM");
                expireManager.add(code, fileName, fileUrl, firstDayOfMonth, ExpireType.EmployeeCard.getCode());
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return null;
    }

    // 获取工作证code
    private String getEmployeeCode(Integer year, Integer month) {
        String code = ExpireType.EmployeeCard.getCode() + "_" + String.valueOf(year) + "_" + String.valueOf(month);
        return code;
    }




}
