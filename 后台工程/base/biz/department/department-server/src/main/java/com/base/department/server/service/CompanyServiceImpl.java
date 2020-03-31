package com.base.department.server.service;

import java.util.List;
import java.util.Objects;

import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.model.CompanyDO;
import com.base.department.server.model.CompanyDTO;
import com.base.common.exception.BaseException;
import com.base.department.client.model.CompanyVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/27 11:28 PM
 */
@Component
public class CompanyServiceImpl{

    @Autowired
    private CompanyManager companyManager;

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
                companyVO.setSub(buildCompanyTree(companyVOList, companyVO.getCode()));
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
        CompanyDTO companyDTO = companyManager.add(name, desc, fatherCode);
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
        List<CompanyDO> companyDOList = companyManager.findByFatherCode(code);
        if (CollectionUtils.isNotEmpty(companyDOList)) {
            throw new BaseException("请先删除该单位下的子单位");
        }
        // TODO 该单位下面没有警员，才可以删除

        companyManager.delete(code);
        return ;
    }









    public void setCompanyManager(CompanyManager companyManager) {
        this.companyManager = companyManager;
    }
}
