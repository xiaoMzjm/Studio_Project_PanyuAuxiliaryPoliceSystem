package com.base.biz.assessment.server.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

import com.base.biz.assessment.client.enums.AssessmentDownloadTypeEnum;
import com.base.biz.assessment.client.enums.AssessmentStatusEnum;
import com.base.biz.assessment.client.model.AssementPageListVO;
import com.base.biz.assessment.server.service.BizAssessmentService;
import com.base.biz.expire.client.common.ExpireEnums;
import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.user.client.client.BizUserClient;
import com.base.biz.user.client.common.Enums.AssessmentGradeEnum;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.model.BizUserDetailVO.Assessment;
import com.base.biz.user.client.model.BizUserDetailVO.Experience;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author:小M
 * @date:2020/9/30 4:45 PM
 */
@Service
public class BizAssessmentServiceImpl implements BizAssessmentService {

    @Autowired
    private ExpireClient expireClient;
    @Autowired
    private BizUserClient bizUserClient;
    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    private final static Integer BEGIN_YEAR = 2018;

    /**
     * 搜索所有类型为年度考核的记录，按照年份倒序
     * 组装成vo返回
     * @return
     */
    @Override
    public List<AssementPageListVO> list() {

        List<ExpireVO> expireVOList = expireClient.listAllByType(ExpireType.Assessment.getCode());
        Map<String,ExpireVO> code2ExpireVOMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(expireVOList)) {
            expireVOList.stream().collect(Collectors.toMap(ExpireVO::getCode,Function.identity(),(a,b)->b));
        }
        List<AssementPageListVO> result = Lists.newArrayList();

        Integer nowYear = DateUtil.getYear(new Date());
        for(int i = BEGIN_YEAR ; i <= nowYear; i++) {

            String code = ExpireType.Assessment.getCode() + "-" + String.valueOf(i);

            ExpireVO expireVO = code2ExpireVOMap.get(code);

            if(expireVO == null) {
                ExpireRemarkJson expireRemarkJson = new ExpireRemarkJson();
                expireRemarkJson.setStatus(AssessmentStatusEnum.NEVER.getCode());
                expireClient.add(code, "", "", DateUtil.convert2Date(String.valueOf(i), "yyyy"), JSON.toJSONString(expireRemarkJson), ExpireType.Assessment.getCode());
                expireVO = new ExpireVO();
                expireVO.setCode(code);
                expireVO.setRemark(JSON.toJSONString(expireRemarkJson));
            }

            AssementPageListVO assementPageListVO = new AssementPageListVO();

            assementPageListVO.setCode(expireVO.getCode());
            assementPageListVO.setTime(String.valueOf(i) + "年");
            ExpireRemarkJson expireRemarkJson = JSON.parseObject(expireVO.getRemark(),ExpireRemarkJson.class);
            assementPageListVO.setStatus(expireRemarkJson.getStatus());
            String urls = expireVO.getFileUrl();
            if(StringUtils.isNotEmpty(urls)) {
                assementPageListVO.setExcellentPersonnelAwardFileCode(code + "@" + AssessmentDownloadTypeEnum.ExcellentPersonnelAwardFile.getType());
                assementPageListVO.setSummaryFileCode(code + "@" + AssessmentDownloadTypeEnum.SummaryFile.getType());
                assementPageListVO.setReimbursementFileCode(code + "@" + AssessmentDownloadTypeEnum.Reimbursement.getType());
            }else {
                assementPageListVO.setExcellentPersonnelAwardFileCode("");
                assementPageListVO.setSummaryFileCode("");
                assementPageListVO.setReimbursementFileCode("");
            }

            result.add(assementPageListVO);
        }

        return result;
    }

    /**
     * 根据code搜索是否存在该expire记录，如果没有，则报错
     * 获取该记录的remark字段，读取status，判断是否已完成，如果已完成，则不用再次完成，返回成功
     * 如果没有完成，查询今年的奖惩记录，制作三个报表:
     *
     * 优秀人员奖励金报表
     *
     * 修改expire的url和name和remark
     * @param code
     */
    @Override
    public void complete(String code) {
        ExpireVO expireVO = expireClient.getByCode(code);
        Assert.notNull(expireVO, "找不到记录");

        String remark = expireVO.getRemark();
        Assert.hasText(remark, "remark字段为空");

        ExpireRemarkJson expireRemarkJson = JSON.parseObject(remark, ExpireRemarkJson.class);
        Integer status = expireRemarkJson.getStatus();
        Assert.notNull(status, "状态为空");

        if(AssessmentStatusEnum.COMPLETE.getCode().equals(status)) {
            return;
        }




    }

    /**
     * 放在remark的对象
     */
    static class ExpireRemarkJson {
        private Integer status;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    /**
     * 创建 优秀人员奖励金报表
     */
    private String createExcellentExcel(ExpireVO expireVO) throws Exception{


        Date now = expireVO.getTime();
        Date timeStart = DateUtil.getFirstDayOfYear(DateUtil.getYear(now));
        Date timeEnd = DateUtil.addYears(timeStart, 1);
        List<Assessment> assessmentList = bizUserClient.listByTime(timeStart, timeEnd);

        List<String> nowYearExcellent = Lists.newArrayList();
        List<String> threeYearExcellent = Lists.newArrayList();
        List<String> allUserCodes = Lists.newArrayList();

        Date begin = DateUtil.getFirstDayOfYear(DateUtil.getYear(expireVO.getTime()));
        Date end = DateUtil.addYears(begin,+1);

        Map<String,List<Assessment>> userCodeAndAssessmentListMap = new HashMap<>();
        for(Assessment assessment : assessmentList){
            String userCode = assessment.getUserCode();
            List<Assessment> assessments = userCodeAndAssessmentListMap.get(userCode);
            if(assessments == null) {
                assessments = Lists.newArrayList();
            }
            assessments.add(assessment);
            userCodeAndAssessmentListMap.put(userCode,assessments);
        }

        for(Map.Entry<String,List<Assessment>> entry : userCodeAndAssessmentListMap.entrySet()) {
            if(isExcellentThreeYear(expireVO.getTime(), entry.getValue())) {
                threeYearExcellent.add(entry.getKey());
            }else if(isExcellent(begin, end, entry.getValue())) {
                nowYearExcellent.add(entry.getKey());
            }
        }

        allUserCodes.addAll(nowYearExcellent);
        allUserCodes.addAll(threeYearExcellent);

        if(CollectionUtils.isEmpty(allUserCodes)) {
            return "";
        }

        Map<String,BizUserDetailVO> bizUserDetailVOMap = bizUserClient.listByCodeList(allUserCodes);
        List<List<CellDTO>> cellDTOList = Lists.newArrayList();
        int i = 1;
        int allMoney = 0;
        for(BizUserDetailVO bizUserDetailVO : bizUserDetailVOMap.values()) {
            List<CellDTO> cellDTOS = Lists.newArrayList();
            CellDTO cellDTO1 = new CellDTO(String.valueOf(i++));
            CellDTO cellDTO2 = new CellDTO(bizUserDetailVO.getWorkUnitName());
            CellDTO cellDTO3 = new CellDTO(bizUserDetailVO.getName());
            CellDTO cellDTO4 = new CellDTO(bizUserDetailVO.getIdentityCard());
            CellDTO cellDTO5 = new CellDTO(bizUserDetailVO.getIcbcCardAccount());
            Integer money = nowYearExcellent.contains(bizUserDetailVO.getIdentityCard()) ? 500 : 1000;
            allMoney += money;
            CellDTO cellDTO6 = new CellDTO(String.valueOf(money));
            String remark = money.equals(1000) ? "连续三年优秀" : "";
            CellDTO cellDTO7 = new CellDTO(String.valueOf(remark));

            cellDTOS.add(cellDTO1);
            cellDTOS.add(cellDTO2);
            cellDTOS.add(cellDTO3);
            cellDTOS.add(cellDTO4);
            cellDTOS.add(cellDTO5);
            cellDTOS.add(cellDTO6);
            cellDTOS.add(cellDTO7);

            cellDTOList.add(cellDTOS);
        }

        Map<String,CellDTO> replaceMap = new HashMap<>();
        replaceMap.put("year",new CellDTO(String.valueOf(DateUtil.getYear(expireVO.getTime()))));
        replaceMap.put("allMoney",new CellDTO(String.valueOf(allMoney)));
        replaceMap.put("date",new CellDTO(DateUtil.convert2String(new Date(),"yyyy年MM月dd日")));

        InputStream targetExcel = new ClassPathResource("static/file/工资变动表.xlsx").getInputStream();
        String savePath = diskStaticUrl + "files/";
        String  excelName = ExcelUtil.insertExcelAndSave(targetExcel, 5, 0, savePath, cellDTOList, replaceMap);
        return savePath + excelName;
    }

    /**
     * 是否当年优秀
     * @return
     */
    private boolean isExcellent(Date begin, Date end, List<Assessment> assessmentList) {
        for(Assessment assessment : assessmentList) {
            if(assessment.getTimeDate().getTime() >= begin.getTime() && assessment.getTimeDate().getTime() < end.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 是否连续三年优秀
     * @param thisYear
     */
    private boolean isExcellentThreeYear(Date thisYear, List<Assessment> assessmentList) {

        Date oneBegin = DateUtil.getFirstDayOfYear(DateUtil.getYear(thisYear));
        Date oneEnd = DateUtil.addYears(oneBegin,+1);


        Date twoBegin = DateUtil.addYears(oneBegin, -1);

        Date threeBegin = DateUtil.addYears(twoBegin, -1);

        Date fourBegin = DateUtil.addYears(threeBegin, -1);

        Date fiveBegin = DateUtil.addYears(fourBegin, -1);

        Date sixBegin = DateUtil.addYears(fiveBegin, -1);

        boolean oneResult = false;
        boolean twoResult = false;
        boolean threeResult = false;
        boolean fourResult = false;
        boolean fiveResult = false;
        boolean sixResult = false;

        for(Assessment assessment : assessmentList) {
            if(assessment.getTimeDate().getTime() >= oneBegin.getTime() && assessment.getTimeDate().getTime() < oneEnd.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    oneResult = true;
                }
            }
            if(assessment.getTimeDate().getTime() >= twoBegin.getTime() && assessment.getTimeDate().getTime() < oneBegin.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    twoResult = true;
                }
            }
            if(assessment.getTimeDate().getTime() >= threeBegin.getTime() && assessment.getTimeDate().getTime() < twoBegin.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    threeResult = true;
                }
            }
            if(assessment.getTimeDate().getTime() >= fourBegin.getTime() && assessment.getTimeDate().getTime() < threeBegin.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    fourResult = true;
                }
            }
            if(assessment.getTimeDate().getTime() >= fiveBegin.getTime() && assessment.getTimeDate().getTime() < fourBegin.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    fiveResult = true;
                }
            }
            if(assessment.getTimeDate().getTime() >= sixBegin.getTime() && assessment.getTimeDate().getTime() < fiveBegin.getTime()) {
                if(AssessmentGradeEnum.YOU_XIU.getCode().equals(assessment.getGrade())) {
                    sixResult = true;
                }
            }
        }

        // 情况1，连续三年优秀，前第4年不是优秀
        if(oneResult && twoResult && threeResult && !fourResult) {
            return true;
        }

        // 情况2，连续六年优秀
        if(oneResult && twoResult && threeResult
            && fourResult && fiveResult && sixResult) {
            return true;
        }

        return false;
    }
}
