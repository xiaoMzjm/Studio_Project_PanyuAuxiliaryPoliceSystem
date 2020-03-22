package com.base.user.service;

import com.base.common.constant.Result;
import com.base.common.exception.BaseException;
import com.base.common.util.LogUtil;
import com.base.common.util.MD5Util;
import com.base.common.util.VerifyUtil;
import com.base.user.common.Constant.ErrorCode;
import com.base.user.manager.UserManager;
import com.base.user.model.UserConvertor;
import com.base.user.model.UserDTO;
import com.base.user.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/2/9 9:44 PM
 */
@Service
public class UserService {

    @Autowired
    private UserManager userManager;

    /**
     * 注册
     * @param account
     * @param password
     * @return
     */
    public Result<Void> register(String account, String password){

        try {
            VerifyUtil.isNotNull(account, ErrorCode.REGISTER_ACCOUNT_NULL.getCode(), ErrorCode.REGISTER_ACCOUNT_NULL.getDesc());
            VerifyUtil.isNotNull(password, ErrorCode.REGISTER_PASSWORD_NULL.getCode(), ErrorCode.REGISTER_PASSWORD_NULL.getDesc());

            UserDTO userDTO = userManager.findByAccount(account);
            if (userDTO != null) {
                throw new BaseException(ErrorCode.REGISTER_REPEAT.getCode(), ErrorCode.REGISTER_REPEAT.getDesc());
            }

            userDTO = new UserDTO();
            userDTO.setAccount(account);
            userDTO.setPassword(MD5Util.MD5(password));

            userManager.save(userDTO);

            return Result.success(null);
        }catch (Exception e) {
            LogUtil.Error(e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    public Result<UserVO> login(String account, String password) {
        try {
            VerifyUtil.isNotNull(account, ErrorCode.LOGIN_ACCOUNT_NULL.getCode(), ErrorCode.LOGIN_ACCOUNT_NULL.getDesc());
            VerifyUtil.isNotNull(password, ErrorCode.LOGIN_PASSWORD_NULL.getCode(), ErrorCode.LOGIN_PASSWORD_NULL.getDesc());

            UserDTO userDTO = userManager.findByAccountAndPassword(account, password);
            if (userDTO != null) {
                return Result.success(UserConvertor.dto2VO(userDTO));
            } else {
                return Result.error(ErrorCode.LOGIN_INVALID_ACCOUNT_OR_PASSWORD.getCode(), ErrorCode.LOGIN_INVALID_ACCOUNT_OR_PASSWORD.getDesc());
            }

        }catch (Exception e) {
            LogUtil.Error(e);
            return Result.error(e.getMessage());
        }
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
