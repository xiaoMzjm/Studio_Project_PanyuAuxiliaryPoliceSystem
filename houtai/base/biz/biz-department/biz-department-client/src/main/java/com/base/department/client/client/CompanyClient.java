package com.base.department.client.client;

import java.util.List;

import com.base.department.client.model.CompanyVO;

/**
 * @author:小M
 * @date:2020/3/28 2:30 AM
 */
public interface CompanyClient {

    CompanyVO findByCode(String code);

    List<CompanyVO> findByName(String name);

    CompanyVO findByMultiName(String name) ;

    List<CompanyVO> findByCodeList(List<String> codeList) ;

    List<CompanyVO> findAll();

    List<CompanyVO> findAllFaterCompany();

    List<String> findCompanyTree(String companyCode);
}
