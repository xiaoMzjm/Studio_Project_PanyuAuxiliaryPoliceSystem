package com.base.department.server.service.impl;

import java.util.List;
import java.util.Objects;

import com.base.biz.user.client.service.BizUserClient;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.model.CompanyDTO;
import com.base.common.exception.BaseException;
import com.base.department.client.model.CompanyVO;
import com.base.department.server.service.CompanyInnerService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/27 11:28 PM
 */
@Component
public class CompanyInnerServiceImpl implements CompanyInnerService {

    @Autowired
    private CompanyManager companyManager;
    @Autowired
    private BizUserClient bizUserClient;

    /**
     * 获取单位树
     * @return
     */
    public List<CompanyVO> listAll(){

        List<CompanyDTO> companyDTOList = companyManager.listAll();
        List<CompanyVO> companyVOList = CompanyConvertor.dto2voList(companyDTOList);
        companyVOList = this.buildCompanyTree(companyVOList , null);
        return companyVOList;
    }

    private List<CompanyVO> buildCompanyTree(List<CompanyVO> companyVOList, String fatherCode) {
        List<CompanyVO> list = Lists.newArrayList();

        companyVOList.forEach(companyVO -> {

            if ("".equals(companyVO.getFatherCode())) {
                companyVO.setFatherCode(null);
            }
            if (Objects.equals(fatherCode, companyVO.getFatherCode())) {
                companyVO.setChildren(buildCompanyTree(companyVOList, companyVO.getCode()));
                list.add(companyVO);
            }
        });

        return list;
    }

    /**
     * 新增单位
     * @param name
     * @param desc
     * @param fatherCode
     * @return
     */
    public void add(String name, String desc, String fatherCode) throws Exception{

        List<CompanyDTO> brotherCompanyList = companyManager.findByFatherCode(fatherCode);
        if(CollectionUtils.isNotEmpty(brotherCompanyList)) {
            for(CompanyDTO companyDTO : brotherCompanyList) {
                if(Objects.equals(companyDTO.getName(), name)) {
                    throw new BaseException(String.format("同父单位下，已有名称为[%s]的单位，请重命名",name));
                }
            }
        }

        CompanyDTO companyDTO = companyManager.add(name, desc, fatherCode, null);
        if (companyDTO == null || companyDTO.getId() == null) {
            throw new BaseException("添加失败，请重试");
        }
        return;
    }

    /**
     * 删除单位
     * @param code
     * @return
     */
    public void delete(String code) throws Exception{
        List<CompanyDTO> companyDOList = companyManager.findByFatherCode(code);
        if (CollectionUtils.isNotEmpty(companyDOList)) {
            throw new BaseException("请先删除该单位下的子单位");
        }
        Long num = bizUserClient.countByCompanyCode(code);
        if (num != null && num > 0) {
            throw new BaseException(String.format("存在[%d]位人员属于该单位，无法删除，请先删除相关人员",num));
        }
        companyManager.delete(code);
        return ;
    }
}
