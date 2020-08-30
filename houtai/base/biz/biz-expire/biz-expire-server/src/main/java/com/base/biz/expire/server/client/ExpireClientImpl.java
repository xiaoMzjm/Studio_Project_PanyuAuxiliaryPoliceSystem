package com.base.biz.expire.server.client;

import java.util.Date;
import java.util.List;

import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.expire.server.manager.ExpireManager;
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
    public List<ExpireVO> selectByTime(Date start, Date end, Integer type) throws Exception{

        List<ExpireDO> expireDOList = expireManager.getByTime(start, end, type);
        if(CollectionUtils.isEmpty(expireDOList)) {
            return Lists.newArrayList();
        }
        List<ExpireVO> result = Lists.newArrayList();
        for(ExpireDO expireDO : expireDOList) {
            ExpireVO expireVO = new ExpireVO();
            expireVO.setCode(expireDO.getCode());
            expireVO.setName(expireDO.getFileName());
            expireVO.setTime(expireDO.getTime());
            expireVO.setFileUrl(expireDO.getFileUrl());
            expireVO.setRemark(expireDO.getRemark());
            result.add(expireVO);
        }
        return result;
    }

    @Override
    public void add(String code, String fileName, String fileUrl, Date time, String remark, int type) throws Exception {
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
    public ExpireVO findByCode(String code) throws Exception {
        ExpireDO expireDO = expireManager.getByCode(code);
        if(expireDO == null) {
            return null;
        }
        ExpireVO expireVO = new ExpireVO();
        expireVO.setCode(expireDO.getCode());
        expireVO.setName(expireDO.getFileName());
        expireVO.setTime(expireDO.getTime());
        expireVO.setFileUrl(expireDO.getFileUrl());
        expireVO.setRemark(expireDO.getRemark());
        return expireVO;
    }

    public ExpireManager getExpireManager() {
        return expireManager;
    }

    public void setExpireManager(ExpireManager expireManager) {
        this.expireManager = expireManager;
    }
}
