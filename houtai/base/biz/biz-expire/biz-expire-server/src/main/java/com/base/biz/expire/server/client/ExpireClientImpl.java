package com.base.biz.expire.server.client;

import java.util.Date;
import java.util.List;

import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.expire.server.manager.ExpireManager;
import com.base.biz.expire.server.manager.impl.ExpireManagerImpl;
import com.base.biz.expire.server.model.ExpireConvertor;
import com.base.biz.expire.server.model.ExpireDO;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/8/2 6:17 PM
 */
@Service
public class ExpireClientImpl implements ExpireClient {

    @Autowired
    private ExpireManager expireManager;

    @Override
    public List<ExpireVO> listByTime(Date start, Date end, Integer type){

        List<ExpireDO> expireDOList = expireManager.listByTime(start, end, type);
        return ExpireConvertor.do2voList(expireDOList);
    }

    @Override
    public List<ExpireVO> listAllByType(Integer type) {
        List<ExpireDO> expireDOList = expireManager.listByTypeOrderByTimeDesc(type);
        return ExpireConvertor.do2voList(expireDOList);
    }

    @Override
    public void add(String code, String fileName, String fileUrl, Date time, String remark, int type){
        String c = UUIDUtil.get();
        if(StringUtils.isNotEmpty(code)) {
            c = code;
        }
        expireManager.add(c, fileName, fileUrl, time, remark, type);
    }

    @Override
    public Long delete(String code) {
        return expireManager.delete(code);
    }

    @Override
    public ExpireVO getByCode(String code) {
        ExpireDO expireDO = expireManager.getByCode(code);
        return ExpireConvertor.do2vo(expireDO);
    }



}
