package com.base.biz.user.server.model;

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 个人履历表
 * @author:小M
 * @date:2020/4/5 1:46 PM
 */
public class AwardConvertor {

    public static AwardDTO do2dto(AwardDO doo) {
        if (doo == null) {
            return null;
        }
        AwardDTO dto = new AwardDTO();
        BeanUtils.copyProperties(doo, dto);
        return dto;
    }

    public static List<AwardDTO> do2dtoList(List<AwardDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<AwardDTO> result = Lists.newArrayList();
        for (AwardDO doo : doList) {
            result.add(do2dto(doo));
        }
        return result;
    }
}
