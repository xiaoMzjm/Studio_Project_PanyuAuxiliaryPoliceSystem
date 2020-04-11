package com.base.resource.server.model;

import java.util.List;

import com.base.resource.client.common.Constant;
import com.base.resource.client.model.ResourceVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/4/5 8:45 PM
 */
public class ResourceConvertor {

    public static ResourceDTO do2dto(ResourceDO resourceDO) {
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(resourceDO, resourceDTO);
        return resourceDTO;
    }

    public static List<ResourceDTO> do2dtos(List<ResourceDO> resourceDOList) {
        if (CollectionUtils.isEmpty(resourceDOList)) {
            return null;
        }
        List<ResourceDTO> resourceDTOList = Lists.newArrayList();
        for (ResourceDO resourceDO : resourceDOList) {
            resourceDTOList.add(do2dto(resourceDO));
        }
        return resourceDTOList;
    }

    public static ResourceVO dto2vo(ResourceDTO resourceDTO) {
        ResourceVO resourceVO = new ResourceVO();
        resourceVO.setPath(resourceDTO.getPath());
        resourceVO.setName(resourceDTO.getName());
        resourceVO.setExt(resourceDTO.getExt());
        resourceVO.setUrl("/images/" + resourceDTO.getName() + "." + resourceDTO.getExt());
        return resourceVO;
    }
}
