package com.base.department.client.service;

import java.util.List;

import com.base.department.client.model.CompanyVO;

/**
 * @author:小M
 * @date:2020/3/28 2:30 AM
 */
public interface CompanyClientService {

    CompanyVO findByCode(String code);

    List<CompanyVO> findByName(String name);

    List<CompanyVO> findByCodeList(List<String> codeList);

    List<CompanyVO> findAll();
}
