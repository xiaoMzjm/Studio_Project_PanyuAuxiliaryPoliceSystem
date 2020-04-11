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
public class FamilyMemberConvertor {

    public static FamilyMemberDTO do2dto(FamilyMemberDO doo) {
        if (doo == null) {
            return null;
        }
        FamilyMemberDTO dto = new FamilyMemberDTO();
        BeanUtils.copyProperties(doo, dto);
        return dto;
    }

    public static List<FamilyMemberDTO> do2dtoList(List<FamilyMemberDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<FamilyMemberDTO> result = Lists.newArrayList();
        for (FamilyMemberDO doo : doList) {
            result.add(do2dto(doo));
        }
        return result;
    }
}
