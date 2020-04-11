package com.base.biz.user.server.service;

import com.base.biz.user.client.service.BizUserService;
import com.base.biz.user.server.manager.BizUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/11 8:02 PM
 */
@Service
public class BizUserServiceImpl implements BizUserService {

    @Autowired
    private BizUserManager bizUserManager;

    @Override
    public Long countByCompanyCode(String companyCode) {
        return bizUserManager.countByCompanyCode(companyCode);
    }
}
