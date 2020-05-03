package com.base.department.server.model;

import java.util.List;

import com.base.department.client.model.CompanyVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/3/27 2:17 AM
 */
public class CompanyConvertor {

    public static CompanyDTO do2dto(CompanyDO companyDO) {
        if (companyDO == null) {
            return null;
        }
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(companyDO,companyDTO);
        return companyDTO;
    }

    public static List<CompanyDTO> do2dtoList(List<CompanyDO> companyDOList) {
        if (CollectionUtils.isEmpty(companyDOList)) {
            return null;
        }
        List<CompanyDTO> result = Lists.newArrayList();
        for (CompanyDO companyDO : companyDOList) {
            result.add(do2dto(companyDO));
        }
        return result;
    }

    public static CompanyVO dto2vo(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            return null;
        }
        CompanyVO companyVO = new CompanyVO();
        BeanUtils.copyProperties(companyDTO,companyVO);
        return companyVO;
    }

    public static List<CompanyVO> dto2voList(List<CompanyDTO> companyDTOList) {
        List<CompanyVO> result = Lists.newArrayList();
        if(CollectionUtils.isEmpty(companyDTOList)) {
            return result;
        }
        for (CompanyDTO companyDTO : companyDTOList) {
            result.add(dto2vo(companyDTO));
        }
        return result;
    }

}
