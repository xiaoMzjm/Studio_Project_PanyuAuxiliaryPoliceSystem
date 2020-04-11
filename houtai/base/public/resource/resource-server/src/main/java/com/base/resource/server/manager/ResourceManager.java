package com.base.resource.server.manager;

import java.util.Date;
import java.util.List;

import com.base.resource.server.dao.ResourceDao;
import com.base.resource.server.model.ResourceConvertor;
import com.base.resource.server.model.ResourceDO;
import com.base.resource.server.model.ResourceDTO;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/4/5 8:46 PM
 */
@Component
public class ResourceManager {

    @Autowired
    private ResourceDao resourceDao;

    public ResourceDTO add(String path, String name, String ext, String oriName) {

        ResourceDO resourceDO = new ResourceDO();
        Date now = new Date();
        resourceDO.setGmtCreate(now);
        resourceDO.setGmtModified(now);
        resourceDO.setPath(path);
        resourceDO.setName(name);
        resourceDO.setExt(ext);
        resourceDO.setOriName(oriName);
        resourceDO = resourceDao.save(resourceDO);
        return ResourceConvertor.do2dto(resourceDO);
    }


    public List<ResourceDTO> findByNames(List<String> nameList){
        List<ResourceDO> result = resourceDao.findByNames(nameList);
        return ResourceConvertor.do2dtos(result);
    }

}
