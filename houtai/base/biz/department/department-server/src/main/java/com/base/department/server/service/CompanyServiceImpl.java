package com.base.department.server.service;

import java.util.List;

import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyService;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/8 12:00 AM
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyManager companyManager;

    @Override
    public CompanyVO findByCode(String code) {
        CompanyDTO companyDTO = companyManager.findByCode(code);
        return CompanyConvertor.dto2vo(companyDTO);
    }

    @Override
    public CompanyVO findByName(String name) {
        CompanyDTO companyDTO = companyManager.findByName(name);
        return CompanyConvertor.dto2vo(companyDTO);
    }

    @Override
    public List<CompanyVO> findByCodeList(List<String> codeList) {
        List<CompanyDTO> companyDTOList = companyManager.findByCodeList(codeList);
        return CompanyConvertor.dto2voList(companyDTOList);
    }

}
