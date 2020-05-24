package com.base.biz.user.server.service;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserService;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.model.BizUserConvertor;
import com.base.biz.user.server.model.BizUserDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
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
    @Autowired
    private BizUserInnerSerivce bizUserInnerSerivce;

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
}
