package com.base.biz.user.client.service;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.model.BizUserDetailVO;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
public interface BizUserService {

    Long countByCompanyCode(String companyCode);

    List<BizUserDetailVO> getByWorkCardBeginTime(Date start, Date end);
}
