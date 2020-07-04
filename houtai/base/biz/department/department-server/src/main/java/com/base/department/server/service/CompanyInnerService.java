package com.base.department.server.service;

import java.util.List;

import com.base.department.client.model.CompanyVO;

/**
 * @author:小M
 * @date:2020/7/4 11:22 PM
 */
public interface CompanyInnerService {

    List<CompanyVO> listAll();

    void add(String name, String desc, String fatherCode) throws Exception;

    void delete(String code) throws Exception;
}
