package com.base.biz.user.server.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.base.authority.client.model.AuthorityVO;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.model.BizUserLoginVO;
import com.base.biz.user.client.model.BizUserPageListVO;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.model.UpdateParam;

/**
 * @author:小M
 * @date:2020/7/4 11:37 PM
 */
public interface BizUserInnerService {

    List<BizUserPageListVO> findByNameAndCompanyCodeList(String name, List<String> companyList)  throws Exception;

    BizUserDetailVO findByCode(String code) throws Exception;

    BizUserDetailVO dto2vo(BizUserDTO dto);

    List<BizUserPageListVO> superPageList(SuperPageListParam param) throws Exception;

    BizUserLoginVO login(String account, String password) throws Exception;

    BizUserDTO add(BizUserAddParam param) throws Exception;

    void update(UpdateParam param) throws Exception;

    void deleteByCode(String code, boolean deleteUser) throws Exception;

    void importUser(InputStream inputStream)throws Exception;

    List<String> importImage(File file) throws Exception;

    File exportUser(String code, InputStream inputStream) throws Exception;

    File exportIncomeCertificate(String code, InputStream inputStream) throws Exception;

    File exportOnTheJobCertificate(String code, InputStream inputStream) throws Exception;

    String exportSelectUser(InputStream fromFileInputStream, List<String> userCodes) throws Exception;

    List<AuthorityVO> getAuthority(String userCode) throws Exception;
}
