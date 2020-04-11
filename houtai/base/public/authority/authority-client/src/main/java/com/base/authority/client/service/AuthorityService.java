package com.base.authority.client.service;

import java.util.List;

import com.base.authority.client.model.AuthorityVO;

/**
 * @author:小M
 * @date:2020/3/29 7:48 PM
 */
public interface AuthorityService {

    List<AuthorityVO> listAll();

    AuthorityVO add(String name, String code , String authorityType, String fatherCode) throws Exception;
}
