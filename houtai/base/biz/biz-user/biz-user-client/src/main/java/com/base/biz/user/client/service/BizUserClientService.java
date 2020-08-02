package com.base.biz.user.client.service;

import java.util.*;

import com.base.biz.user.client.model.BizUserDetailVO;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
public interface BizUserClientService {

    Long countByCompanyCode(String companyCode);

    List<BizUserDetailVO> getByWorkCardBeginTime(Date start, Date end);

    List<BizUserDetailVO> getByContractEngTime(Date start, Date end);

    List<BizUserDetailVO> getByBirthDayAndSex(Date start, Date end, Integer sex);

    List<BizUserDetailVO> getByNameLike(String name);

    Map<String,BizUserDetailVO> getByCodeList(List<String> codeList) throws Exception;
}
