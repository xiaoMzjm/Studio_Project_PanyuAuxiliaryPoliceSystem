package com.base.department.server.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSON;

import com.base.common.exception.BaseException;
import com.base.department.server.dao.CompanyDORepository;
import com.base.department.server.manager.CompanyManager;
import com.base.department.server.model.CompanyConvertor;
import com.base.department.server.model.CompanyDTO;
import com.base.department.server.model.CompanyDO;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/27 2:14 AM
 */
@Component
public class CompanyManagerImpl implements CompanyManager {

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
     * 根据父单位查询
     * @param fatherCode
     * @return
     */
    public List<CompanyDTO> findByFatherCode(String fatherCode) {
        CompanyDO companyDO = new CompanyDO();
        companyDO.setFatherCode(fatherCode);
        Example<CompanyDO> example = Example.of(companyDO);
        List<CompanyDO> list = companyDORepository.findAll(example);
        return CompanyConvertor.do2dtoList(list);
    }

    /**
     * 根据父单位查询
     * @param name
     * @return
     */
    public List<CompanyDTO> findByName(String name) {
        CompanyDO companyDO = new CompanyDO();
        companyDO.setName(name);
        Example<CompanyDO> example = Example.of(companyDO);
        List<CompanyDO> list = companyDORepository.findAll(example);
        return CompanyConvertor.do2dtoList(list);
    }

    /**
     * 根据code查询
     * @param code
     * @return
     */
    public CompanyDTO findByCode(String code) {
        CompanyDO companyDO = new CompanyDO();
        companyDO.setCode(code);
        Example<CompanyDO> example = Example.of(companyDO);
        Optional<CompanyDO> optional = companyDORepository.findOne(example);
        if (!optional.isPresent()) {
            return null;
        }
        return CompanyConvertor.do2dto(optional.get());
    }

    /**
     * 根据codeLisr查询
     * @param codeList
     * @return
     */
    public List<CompanyDTO> findByCodeList(List<String> codeList) {
        List<CompanyDO> companyDOList = companyDORepository.findByCodeIn(codeList);
        return CompanyConvertor.do2dtoList(companyDOList);
    }

    /**
     * 查询全部
     * @return
     */
    public List<CompanyDTO> findAll(){
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
    public CompanyDTO add(String name, String desc, String fatherCode, String code){
        CompanyDO companyDO = new CompanyDO();
        Date date = new Date();
        companyDO.setGmtCreate(date);
        companyDO.setGmtModified(date);
        if(StringUtils.isEmpty(code)) {
            companyDO.setCode(UUIDUtil.get());
        }else {
            companyDO.setCode(code);
        }
        companyDO.setName(name);
        companyDO.setDescription(desc);
        companyDO.setFatherCode(fatherCode);
        companyDO = companyDORepository.save(companyDO);
        return CompanyConvertor.do2dto(companyDO);
    }

    /**
     * 根据code删除单位
     * @param code
     */
    public void delete(String code) throws Exception{
        CompanyDO companyDO = new CompanyDO();
        companyDO.setCode(code);
        Example<CompanyDO> example = Example.of(companyDO);
        Optional<CompanyDO> optional = companyDORepository.findOne(example);
        if (!optional.isPresent()) {
            throw new BaseException(String.format("单位Code[%s]不存在",code));
        }
        companyDORepository.deleteById(optional.get().getId());
    }
}
