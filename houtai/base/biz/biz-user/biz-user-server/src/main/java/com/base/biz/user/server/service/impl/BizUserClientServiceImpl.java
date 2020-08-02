package com.base.biz.user.server.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserClientService;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.service.BizUserInnerService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
@Service
public class BizUserClientServiceImpl implements BizUserClientService {

    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private BizUserInnerService bizUserInnerSerivce;

    @Override
    public Long countByCompanyCode(String companyCode) {
        return bizUserManager.countByCompanyCode(companyCode);
    }

    @Override
    public List<BizUserDetailVO> getByWorkCardBeginTime(Date start, Date end) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByWorkCardBeginTime(start, end);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> getByContractEngTime(Date start, Date end) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByContractEngTime(start, end);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> getByBirthDayAndSex(Date start, Date end, Integer sex) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        List<BizUserDTO> bizUserDTOList = bizUserManager.getByBirthDayAndSex(start, end, sex);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto));
            }
        }
        return result;
    }

    @Override
    public List<BizUserDetailVO> getByNameLike(String name) {
        List<BizUserDetailVO> result = Lists.newArrayList();
        SuperPageListParam param = new SuperPageListParam();
        param.name = name;
        List<BizUserDTO> bizUserDTOList = bizUserManager.findBySuperParam(param);
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                result.add(bizUserInnerSerivce.dto2vo(dto));
            }
        }
        return result;
    }

    @Override
    public Map<String,BizUserDetailVO> getByCodeList(List<String> codeList) throws Exception{
        List<BizUserDTO> bizUserDTOList = bizUserManager.findByCodes(codeList);
        List<BizUserDetailVO> bizUserDetailVOList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(bizUserDTOList)) {
            for (BizUserDTO dto : bizUserDTOList) {
                bizUserDetailVOList.add(bizUserInnerSerivce.dto2vo(dto));
            }
        }
        Map<String,BizUserDetailVO> result = bizUserDetailVOList.stream().collect(Collectors.toMap(BizUserDetailVO::getCode, Function
            .identity()));
        return result;
    }

}
