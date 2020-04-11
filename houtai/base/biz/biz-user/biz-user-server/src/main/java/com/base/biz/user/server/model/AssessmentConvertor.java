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
public class AssessmentConvertor {

    public static AssessmentDTO do2dto(AssessmentDO doo) {
        if (doo == null) {
            return null;
        }
        AssessmentDTO dto = new AssessmentDTO();
        BeanUtils.copyProperties(doo, dto);
        return dto;
    }

    public static List<AssessmentDTO> do2dtoList(List<AssessmentDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<AssessmentDTO> result = Lists.newArrayList();
        for (AssessmentDO doo : doList) {
            result.add(do2dto(doo));
        }
        return result;
    }
}
