package com.base.littleapp.service;

import com.base.common.constant.Result;
import com.base.common.util.VerifyUtil;
import com.base.littleapp.manager.WxLoginManager;
import com.base.littleapp.common.Constant.ErrorCode;
import com.base.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2019/2/24 1:52 AM
 */
@Component
public class WxLoginService {

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
    public Result<UserDTO> login(String code , String encryptedData , String iv){

        try {
            VerifyUtil.isNotEmpty(code , ErrorCode.LOGIN_USER_CODE_EMPTY.getCode(), ErrorCode.LOGIN_USER_CODE_EMPTY.getDesc());
            VerifyUtil.isNotEmpty(encryptedData , ErrorCode.LOGIN_ENCRYPTED_DATA_EMPTY.getCode(), ErrorCode.LOGIN_ENCRYPTED_DATA_EMPTY.getDesc());
            VerifyUtil.isNotEmpty(iv , ErrorCode.LOGIN_IV_EMPTY.getCode(), ErrorCode.LOGIN_IV_EMPTY.getDesc());

            UserDTO userDTO = wxLoginManager.login(code , encryptedData ,iv);

            return Result.success(userDTO);
        }catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    public void setWxLoginManager(WxLoginManager wxLoginManager) {
        this.wxLoginManager = wxLoginManager;
    }
}
