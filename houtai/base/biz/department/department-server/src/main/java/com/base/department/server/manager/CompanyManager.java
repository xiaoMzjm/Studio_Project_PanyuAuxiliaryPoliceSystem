package com.base.department.server.manager;

import java.util.List;

import com.base.department.server.model.CompanyDO;
import com.base.department.server.model.CompanyDTO;

/**
 * @author:小M
 * @date:2020/7/4 11:29 PM
 */
public interface CompanyManager {

    List<CompanyDTO> listAll();

    List<CompanyDTO> findByFatherCode(String fatherCode);

    List<CompanyDTO> findByName(String name);

    CompanyDTO findByCode(String code);

    List<CompanyDTO> findByCodeList(List<String> codeList);

    List<CompanyDTO> findAll();

    CompanyDTO add(String name, String desc, String fatherCode, String code);

    void delete(String code) throws Exception;
}
