package com.base.biz.wages.server.manager.impl;

import java.util.Date;
import java.util.List;

import com.base.biz.wages.server.dao.BizWagesDAO;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/8/30 11:54 AM
 */
@Component
public class BizWagesManagerImpl implements BizWagesManager {

    @Autowired
    private BizWagesDAO bizWagesDAO;

    @Override
    public List<WagesDO> batchSave(List<WagesDO> wagesDOList) {
        if(CollectionUtils.isEmpty(wagesDOList)) {
            return null;
        }
        List<WagesDO> result = bizWagesDAO.saveAll(wagesDOList);
        return result;
    }

    @Override
    public void deleteByIdentityList(List<String> identityList) {
        List<WagesDO> wagesDOList = listByIdentityList(identityList);
        if(CollectionUtils.isNotEmpty(wagesDOList)) {
            bizWagesDAO.deleteAll(wagesDOList);
        }
    }

    @Override
    public List<WagesDO> listByIdentityList(List<String> identityList) {
        return bizWagesDAO.findByIdentityCardIn(identityList);
    }

    @Override
    public List<WagesDO> listByTime(Date time) {
        return bizWagesDAO.findByTime(time);
    }
}
