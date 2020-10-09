package com.base.biz.user.client.client;

import java.util.*;

import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.model.BizUserDetailVO.Assessment;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
public interface BizUserClient {

    Long countByCompanyCode(String companyCode);

    List<BizUserDetailVO> listByWorkCardBeginTime(Date start, Date end);

    List<BizUserDetailVO> listByContractEngTime(Date start, Date end);

    List<BizUserDetailVO> listByBirthDayAndSex(Date start, Date end, Integer sex);

    List<BizUserDetailVO> listByNameLike(String name);

    Map<String,BizUserDetailVO> listByCodeList(List<String> codeList) ;

    BizUserDetailVO getByUserCode(String userCode);

    /**
     * 根据时间获取奖惩记录
     * @return
     */
    List<Assessment> listByTime(Date timeStart, Date timeEnd);
}
