package com.base.department.server.model;

import java.util.List;

import com.base.department.client.model.CompanyVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/3/27 2:17 AM
 */
public class CompanyConvertor {

    public static CompanyDTO do2dto(CompanyDO companyDO) {
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(companyDO,companyDTO);
        return companyDTO;
    }

    public static List<CompanyDTO> do2dtoList(List<CompanyDO> companyDOList) {
        List<CompanyDTO> result = Lists.newArrayList();
        for (CompanyDO companyDO : companyDOList) {
            result.add(do2dto(companyDO));
        }
        return result;
    }

    public static CompanyVO dto2vo(CompanyDTO companyDTO) {
        CompanyVO companyVO = new CompanyVO();
        BeanUtils.copyProperties(companyDTO,companyVO);
        return companyVO;
    }

    public static List<CompanyVO> dto2voList(List<CompanyDTO> companyDTOList) {
        List<CompanyVO> result = Lists.newArrayList();
        for (CompanyDTO companyDTO : companyDTOList) {
            result.add(dto2vo(companyDTO));
        }
        return result;
    }

}
