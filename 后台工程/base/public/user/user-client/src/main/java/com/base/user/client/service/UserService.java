package com.base.user.client.service;

import com.base.user.client.model.UserVO;

/**
 * @author:小M
 * @date:2020/3/29 11:29 PM
 */
public interface UserService {

    public UserVO register(String code) throws Exception;

    public UserVO login(String code) throws Exception;
}
