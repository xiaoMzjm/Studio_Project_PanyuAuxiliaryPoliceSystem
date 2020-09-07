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
import com.base.department.server.service.CompanyInnerService;
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
    @Autowired
    private CompanyInnerService companyInnerService;

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

    @Override
    public List<CompanyVO> findAllFaterCompany() {
        List<CompanyDTO> companyDTOList = companyManager.findAllFatherCompany();
        return CompanyConvertor.dto2voList(companyDTOList);
    }

    @Override
    public List<String> findCompanyTree(String companyCode) {
        List<CompanyVO> companyVOList = companyInnerService.listAll();
        List<String> result = Lists.newArrayList();
        this.buildTree(companyCode, companyVOList,false,result);
        return result;
    }

    private boolean buildTree(String code, List<CompanyVO> companyVOList, boolean isFind, List<String> result) {
        if(CollectionUtils.isEmpty(companyVOList)) {
            return isFind;
        }
        if(isFind) {
            // 如果父节点已经是目标节点了，那么这层的节点都包含
            result.addAll(companyVOList.stream().map(CompanyVO::getCode).collect(Collectors.toList()));
            companyVOList.forEach(item->{
                // 这层节点下面所有节点也都包含进来
                buildTree(code, item.getChildren(),isFind,result);
            });
            return isFind;
        }else {
            for(CompanyVO companyVO : companyVOList) {
                if(companyVO.getCode().equals(code)) {
                    buildTree(code, companyVO.getChildren(), true, result);
                    if(StringUtils.isEmpty(companyVO.getFatherCode())) {
                        result.add(companyVO.getCode());
                    }else {
                        result.addAll(companyVOList.stream().map(CompanyVO::getCode).collect(Collectors.toList()));
                    }
                    return true;
                }else {
                    boolean find = buildTree(code, companyVO.getChildren(),isFind,result);
                    if(find && StringUtils.isNotEmpty(companyVO.getFatherCode())) {
                        result.addAll(companyVOList.stream().map(CompanyVO::getCode).collect(Collectors.toList()));
                        return true;
                    }else if(find) {
                        result.add(companyVO.getCode());
                        return true;
                    }
                }
            }
        }
        return isFind;
    }

    private List<CompanyVO> findByFatherCode(String fatherCode){
        List<CompanyDTO> companyDTOList = companyManager.findByFatherCode(fatherCode);
        return CompanyConvertor.dto2voList(companyDTOList);
    }
}
