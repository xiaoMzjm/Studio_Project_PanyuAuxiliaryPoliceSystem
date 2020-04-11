package com.base.user.client.service;

import com.base.user.client.model.UserVO;

/**
 * @author:小M
 * @date:2020/3/29 11:29 PM
 */
public interface UserService {

    UserVO findByCode(String code) throws Exception;

    UserVO add(String code) throws Exception;

    UserVO updateToken(String code) throws Exception;

    void deleteByCode(String code) throws Exception;



}
