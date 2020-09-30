package com.base.biz.wages.server.manager.impl;

import java.util.Date;
import java.util.List;

import com.base.biz.wages.server.dao.BizWagesDAO;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import com.google.common.collect.Lists;
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
        List<WagesDO> result = Lists.newArrayList();
        List<List<WagesDO>> list = Lists.partition(wagesDOList, 500);
        for(List<WagesDO> item : list) {
            result.addAll(bizWagesDAO.saveAll(item));
        }
        return result;
    }

    @Override
    public void deleteByIdentityListAndTime(List<String> identityList, Date time) {
        List<WagesDO> wagesDOList = listByIdentityListAndTime(identityList, time);
        if(CollectionUtils.isNotEmpty(wagesDOList)) {
            bizWagesDAO.deleteAll(wagesDOList);
        }
    }

    @Override
    public List<WagesDO> listByIdentityListAndTime(List<String> identityList, Date time) {
        return bizWagesDAO.findByIdentityCardInAndTime(identityList, time);
    }

    @Override
    public List<WagesDO> listByTime(Date time) {
        return bizWagesDAO.findByTime(time);
    }
}
