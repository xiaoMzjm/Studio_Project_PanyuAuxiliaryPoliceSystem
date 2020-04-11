package com.base.littleapp.client.service;

import com.base.littleapp.client.model.WxUserInfoVO;

/**
 * @author:小M
 * @date:2020/3/29 10:42 PM
 */
public interface WxService {

    public WxUserInfoVO login(String code , String encryptedData , String iv) throws Exception;
}
