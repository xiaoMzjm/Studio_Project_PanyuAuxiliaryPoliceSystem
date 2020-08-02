package com.base.department.server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.base.common.exception.BaseException;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyClientService;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.manager.impl.CompanyManagerImpl;
import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.model.CompanyDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    public CompanyVO findByMultiName(String name){
        String[] array = name.split("/");
        List<String> childrenCodes = Lists.newArrayList();
        CompanyVO result = null;
        for(int i = 0 ;i < array.length; ) {
            String companyName = array[i];
            List<CompanyVO> companyVOList = findByName(companyName);
            if( i == 0) {
                companyVOList = companyVOList.stream().filter(item -> StringUtils.isBlank(item.getFatherCode())).collect(Collectors.toList());
            }else {
                List<CompanyVO> temp = Lists.newArrayList();
                for(CompanyVO companyVO : companyVOList) {
                    if(childrenCodes.contains(companyVO.getCode())) {
                        temp.add(companyVO);
                    }
                }
                companyVOList = temp;
            }
            if(CollectionUtils.isEmpty(companyVOList)) {
                return null;
            }
            CompanyVO companyVO = companyVOList.get(0);
            List<CompanyVO> childrenVOs = findByFatherCode(companyVO.getCode());
            childrenCodes = childrenVOs.stream().map(CompanyVO::getCode).collect(Collectors.toList());
            result = companyVO;
            i++;
        }
        return result;
    }

    private List<CompanyVO> findByFatherCode(String fatherCode){
        List<CompanyDTO> companyDTOList = companyManager.findByFatherCode(fatherCode);
        return CompanyConvertor.dto2voList(companyDTOList);
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
