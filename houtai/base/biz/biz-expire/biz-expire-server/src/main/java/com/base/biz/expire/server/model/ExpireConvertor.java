package com.base.biz.expire.server.model;

import java.util.List;
import java.util.stream.Collectors;

import com.base.biz.expire.client.model.ExpireVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author:小M
 * @date:2020/9/30 11:44 PM
 */
public class ExpireConvertor {

    public static ExpireVO do2vo(ExpireDO expireDO){
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

    public static List<ExpireVO> do2voList(List<ExpireDO> expireDOList){
        if(CollectionUtils.isEmpty(expireDOList)) {
            return Lists.newArrayList();
        }
        return expireDOList.stream().map(item -> do2vo(item)).collect(Collectors.toList());
    }
}
