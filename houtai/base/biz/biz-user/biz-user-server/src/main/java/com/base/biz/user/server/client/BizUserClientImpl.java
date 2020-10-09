package com.base.biz.user.server.client;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.client.BizUserClient;
import com.base.biz.user.client.model.BizUserDetailVO.Assessment;
import com.base.biz.user.server.manager.AssessmentManager;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.model.AssessmentDTO;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.service.BizUserService;
import com.base.common.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
@Service
public class BizUserClientImpl implements BizUserClient {

    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private BizUserService bizUserInnerSerivce;
    @Autowired
    private AssessmentManager assessmentManager;

    @Override
    public Long countByCompanyCode(String companyCode) {
        return bizUserManager.countByCompanyCode(companyCode);
    }

    @Override
    public List<BizUserDetailVO> listByWorkCardBeginTime(Date start, Date end) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByWorkCardBeginTime(start, end);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto,null));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> listByContractEngTime(Date start, Date end) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByContractEngTime(start, end);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto,null));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> listByBirthDayAndSex(Date start, Date end, Integer sex) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByBirthDayAndSex(start, end, sex);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto,null));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> listByNameLike(String name) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        SuperPageListParam param = new SuperPageListParam();
        param.name = name;
        List<BizUserDTO> bizUserDTOList = bizUserManager.findBySuperParam(param);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto,null));
            }
        }
        return result;
    }

    @Override
    public Map<String,BizUserDetailVO> listByCodeList(List<String> codeList){
        List<BizUserDTO> bizUserDTOList = bizUserManager.findByCodes(codeList);
        List<BizUserDetailVO> bizUserDetailVOList = Lists.newArrayList();
        Collections.sort(bizUserDetailVOList);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                bizUserDetailVOList.add(bizUserInnerSerivce.dto2vo(dto,null));
            }
        }
        Map<String,BizUserDetailVO> result = bizUserDetailVOList.stream().collect(Collectors.toMap(BizUserDetailVO::getCode, Function
            .identity()));
        return result;
    }

    @Override
    public BizUserDetailVO getByUserCode(String userCode){
        BizUserDTO bizUserDTO = bizUserManager.findByCode(userCode);
        return bizUserInnerSerivce.dto2vo(bizUserDTO,null);
    }

    @Override
    public List<Assessment> listByTime(Date timeStart, Date timeEnd) {
        List<AssessmentDTO> assessmentDTOList = assessmentManager.listByTime(timeStart, timeEnd);
        if(CollectionUtils.isEmpty(assessmentDTOList)) {
            return null;
        }
        return assessmentDTOList.stream().map(item->{
            Assessment assessment = new Assessment();
            assessment.setTimeDate(item.getTime());
            assessment.setTime(DateUtil.convert2String(item.getTime(),BizUserConstant.DateFormat));
            assessment.setGrade(Integer.valueOf(item.getGrade()));
            assessment.setRemark(item.getRemark());
            assessment.setUserCode(item.getUserCode());
            return assessment;
        }).collect(Collectors.toList());
    }

}
