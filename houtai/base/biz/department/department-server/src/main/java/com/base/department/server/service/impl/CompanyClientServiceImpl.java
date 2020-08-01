package com.base.department.server.service.impl;

import java.util.List;

import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyClientService;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.manager.impl.CompanyManagerImpl;
import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/8 12:00 AM
 */
@Service
public class CompanyClientServiceImpl implements CompanyClientService {

    @Autowired
    private CompanyManager companyManager;

    @Override
    public CompanyVO findByCode(String code) {
        CompanyDTO companyDTO = companyManager.findByCode(code);
        return CompanyConvertor.dto2vo(companyDTO);
    }

    @Override
    public List<CompanyVO> findByName(String name) {
        List<CompanyDTO> companyDTOList = companyManager.findByName(name);
        return CompanyConvertor.dto2voList(companyDTOList);
    }

    @Override
    public List<CompanyVO> findByMultiName(String name) {

        return null;
    }

    @Override
    public List<CompanyVO> findByCodeList(List<String> codeList) {
        List<CompanyDTO> companyDTOList = companyManager.findByCodeList(codeList);
        return CompanyConvertor.dto2voList(companyDTOList);
    }

    @Override
    public List<CompanyVO> findAll() {
        List<CompanyDTO> companyDTOList = companyManager.findAll();
        return CompanyConvertor.dto2voList(companyDTOList);
    }

}
