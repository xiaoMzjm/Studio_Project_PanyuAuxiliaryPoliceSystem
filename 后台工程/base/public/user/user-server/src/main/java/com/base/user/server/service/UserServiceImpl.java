package com.base.user.server.service;

import com.base.common.exception.BaseException;
import com.base.common.util.UUIDUtil;
import com.base.common.util.VerifyUtil;
import com.base.user.client.model.UserVO;
import com.base.user.client.service.UserService;
import com.base.user.server.common.Constant.ErrorCode;
import com.base.user.server.manager.UserManager;
import com.base.user.server.model.UserConvertor;
import com.base.user.server.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/2/9 9:44 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    /**
     * 注册
     * @param code
     * @return
     */
    public UserVO register(String code) throws Exception{

        VerifyUtil.isNotNull(code, ErrorCode.REGISTER_CODE_NULL.getCode(), ErrorCode.REGISTER_CODE_NULL.getDesc());
        UserDTO userDTO = userManager.findByCode(code);
        if (userDTO != null) {
            throw new BaseException(ErrorCode.REGISTER_REPEAT.getCode(), ErrorCode.REGISTER_REPEAT.getDesc());
        }
        String token = UUIDUtil.get();
        userDTO = userManager.save(code, token);
        return UserConvertor.dto2VO(userDTO);

    }

    /**
     * 登录
     * @param code
     * @return
     * @throws Exception
     */
    public UserVO login(String code) throws Exception {
        VerifyUtil.isNotNull(code, ErrorCode.REGISTER_CODE_NULL.getCode(), ErrorCode.REGISTER_CODE_NULL.getDesc());
        String token = UUIDUtil.get();
        UserDTO userDTO = userManager.updateToken(code, token);
        return UserConvertor.dto2VO(userDTO);
    }






    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
