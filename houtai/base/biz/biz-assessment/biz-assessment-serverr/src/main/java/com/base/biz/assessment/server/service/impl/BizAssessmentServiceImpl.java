package com.base.biz.assessment.server.service.impl;

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
import com.base.common.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/9/30 4:45 PM
 */
@Service
public class BizAssessmentServiceImpl implements BizAssessmentService {

    @Autowired
    private ExpireClient expireClient;

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
     * 如果没有完成，制作三个报表，修改expire的url和name和remark
     * @param code
     */
    @Override
    public void complete(String code) {

    }

    static class ExpireRemarkJson {
        private Integer status;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
