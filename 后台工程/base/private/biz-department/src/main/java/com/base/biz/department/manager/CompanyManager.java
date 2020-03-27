package com.base.biz.department.manager;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.base.biz.department.model.CompanyConvertor;
import com.base.biz.department.model.CompanyDO;
import com.base.biz.department.model.CompanyDTO;
import com.base.biz.department.repository.CompanyDORepository;
import com.base.common.constant.Result;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/27 2:14 AM
 */
@Component
public class CompanyManager {

    @Autowired
    private CompanyDORepository companyDORepository;

    /**
     * 获取所有单位
     * @return
     */
    public List<CompanyDTO> listAll() {
        List<CompanyDO> companyDOList = companyDORepository.findAll();
        return CompanyConvertor.do2dtoList(companyDOList);
    }

    /**
     * 新增一个单位
     * @param name
     * @param desc
     * @param fatherCode
     * @return
     */
    public CompanyDTO add(String name, String desc, String fatherCode){
        CompanyDO companyDO = new CompanyDO();
        Date date = new Date();
        companyDO.setGmtCreate(date);
        companyDO.setGmtModified(date);
        companyDO.setCode(UUIDUtil.get());
        companyDO.setName(name);
        companyDO.setDescription(desc);
        companyDO.setFatherCode(fatherCode);
        System.out.println(JSON.toJSON(companyDO));
        companyDO = companyDORepository.save(companyDO);
        return CompanyConvertor.do2dto(companyDO);
    }

    /**
     * 根据code删除单位
     * @param code
     */
    public void delete(String code) {
        CompanyDO companyDO = new CompanyDO();
        companyDO.setCode(code);
        companyDORepository.delete(companyDO);
    }

    /**
     * 根据父单位查询
     * @param fatherCode
     * @return
     */
    public List<CompanyDO> findByFatherCode(String fatherCode) {
        CompanyDO companyDO = new CompanyDO();
        companyDO.setFatherCode(fatherCode);
        Example<CompanyDO> example = Example.of(companyDO);
        List<CompanyDO> list = companyDORepository.findAll(example);
        return list;
    }





    public void setCompanyDORepository(CompanyDORepository companyDORepository) {
        this.companyDORepository = companyDORepository;
    }
}
