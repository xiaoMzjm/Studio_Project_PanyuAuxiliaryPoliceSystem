package com.base.biz.epidemic.server.model.convertor;

import java.util.List;

import com.base.biz.epidemic.client.model.EpidemicDTO;
import com.base.biz.epidemic.server.model.EpidemicDO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/8/2 3:28 PM
 */
public class EpidemicConvertor {

    public static EpidemicDTO do2dto(EpidemicDO epidemicDO) {
        if(epidemicDO == null) {
            return null;
        }
        EpidemicDTO epidemicDTO = new EpidemicDTO();
        BeanUtils.copyProperties(epidemicDO,epidemicDTO);
        return epidemicDTO;
    }

    public static List<EpidemicDTO> do2dtoList(List<EpidemicDO> epidemicDOList) {
        if(CollectionUtils.isEmpty(epidemicDOList)) {
            return Lists.newArrayList();
        }
        List<EpidemicDTO> result = Lists.newArrayList();
        for(EpidemicDO epidemicDO : epidemicDOList) {
            result.add(do2dto(epidemicDO));
        }
        return result;
    }
}
