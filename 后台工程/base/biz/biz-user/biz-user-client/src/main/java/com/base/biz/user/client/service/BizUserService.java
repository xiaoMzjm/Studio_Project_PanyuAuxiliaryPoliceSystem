package com.base.biz.user.client.service;

import com.base.biz.user.client.model.BizUserVO;

/**
 * @author:小M
 * @date:2020/3/30 12:02 AM
 */
public interface BizUserService {

    public BizUserVO login(String account, String password) throws Exception;
}
