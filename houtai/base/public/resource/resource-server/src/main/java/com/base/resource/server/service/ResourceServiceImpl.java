package com.base.resource.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.resource.client.model.ResourceVO;
import com.base.resource.client.service.ResourceService;
import com.base.resource.server.manager.ResourceManager;
import com.base.resource.server.model.ResourceConvertor;
import com.base.resource.server.model.ResourceDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/5 9:26 PM
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceManager resourceManager;

    @Override
    public Map<String, ResourceVO> findByNameList(List<String> nameList) {

        List<ResourceDTO> resourceDTOList = resourceManager.findByNames(nameList);
        if (CollectionUtils.isEmpty(resourceDTOList)) {
            return null;
        }
        Map<String, ResourceVO> map = new HashMap<>();
        for(ResourceDTO resourceDTO : resourceDTOList) {
            ResourceVO resourceVO = ResourceConvertor.dto2vo(resourceDTO);
            map.put(resourceVO.getName(), resourceVO);
        }
        return map;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
}
