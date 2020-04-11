package com.base.littleapp.server.service;

import com.base.common.util.VerifyUtil;
import com.base.littleapp.client.model.WxUserInfoVO;
import com.base.littleapp.client.service.WxService;
import com.base.littleapp.server.manager.WxLoginManager;
import com.base.littleapp.server.common.Constant.ErrorCode;
import com.base.littleapp.server.model.WxUserConvertor;
import com.base.littleapp.server.model.WxUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2019/2/24 1:52 AM
 */
@Component
public class WxLoginServiceImpl implements WxService {

    @Autowired
    private WxLoginManager wxLoginManager;

    /**
     * 微信登录，获取登录用户信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     * @throws Exception
     */
    public WxUserInfoVO login(String code , String encryptedData , String iv) throws Exception{

        VerifyUtil.isNotEmpty(code , ErrorCode.LOGIN_USER_CODE_EMPTY.getCode(), ErrorCode.LOGIN_USER_CODE_EMPTY.getDesc());
        VerifyUtil.isNotEmpty(encryptedData , ErrorCode.LOGIN_ENCRYPTED_DATA_EMPTY.getCode(), ErrorCode.LOGIN_ENCRYPTED_DATA_EMPTY.getDesc());
        VerifyUtil.isNotEmpty(iv , ErrorCode.LOGIN_IV_EMPTY.getCode(), ErrorCode.LOGIN_IV_EMPTY.getDesc());

        WxUserInfoDTO wxUserInfoDTO = wxLoginManager.login(code , encryptedData ,iv);

        return WxUserConvertor.dto2vo(wxUserInfoDTO);
    }






    public void setWxLoginManager(WxLoginManager wxLoginManager) {
        this.wxLoginManager = wxLoginManager;
    }
}
