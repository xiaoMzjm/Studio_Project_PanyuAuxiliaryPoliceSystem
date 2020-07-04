package com.base.biz.user.server.service;

import com.base.biz.user.server.model.BizUserAddParam;
import com.base.common.exception.BaseException;

/**
 * @author:小M
 * @date:2020/7/4 11:43 PM
 */
public interface BizUserAddUserCheckService {

    void check(BizUserAddParam param) throws BaseException;
}
