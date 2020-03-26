package com.base.biz.department.manager;

import java.util.List;

import com.base.biz.department.model.CampanyDO;
import com.base.biz.department.model.CampanyDTO;
import com.base.biz.department.repository.CampanyDORespository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/27 2:14 AM
 */
@Component
public class CampanyManager {

    @Autowired
    private CampanyDORespository campanyDORespository;

    public List<CampanyDTO> listAll() {
        List<CampanyDTO> result = Lists.newArrayList();
        List<CampanyDO> campanyDOList = campanyDORespository.findAll();
        return result;
    }

    public void setCampanyDORespository(CampanyDORespository campanyDORespository) {
        this.campanyDORespository = campanyDORespository;
    }
}
