package com.base.biz.user.client.service;

import java.util.*;

import com.base.biz.user.client.model.BizUserDetailVO;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
public interface BizUserClientService {

    Long countByCompanyCode(String companyCode);

    List<BizUserDetailVO> listByWorkCardBeginTime(Date start, Date end);

    List<BizUserDetailVO> listByContractEngTime(Date start, Date end);

    List<BizUserDetailVO> listByBirthDayAndSex(Date start, Date end, Integer sex);

    List<BizUserDetailVO> listByNameLike(String name);

    Map<String,BizUserDetailVO> listByCodeList(List<String> codeList) ;

    BizUserDetailVO getByUserCode(String userCode);
}
