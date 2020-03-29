package com.base.littleapp.server.manager;

import com.base.littleapp.server.model.WxRequireLoginResultDTO;
import com.base.littleapp.server.model.WxUserInfoDTO;
import com.base.littleapp.server.wrapper.WxWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2019/1/13 10:49 PM
 */
@Component
public class WxLoginManager {

    @Autowired
    private WxWapper wxWapper;


    /**
     * 微信登录，获取登录用户信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     * @throws Exception
     */
    public WxUserInfoDTO login(String code , String encryptedData , String iv) throws Exception {

        // 获取openId,session
        WxRequireLoginResultDTO wxRequireLoginResultDTO = wxWapper.requireWxForLogin(code);

        // 解密获取用户详情
        WxUserInfoDTO wxUserInfoDTO = wxWapper.decodeUserInfo(encryptedData , wxRequireLoginResultDTO.getSession_key() , iv);

        return wxUserInfoDTO;
    }

    public void setWxWapper(WxWapper wxWapper) {
        this.wxWapper = wxWapper;
    }

}
