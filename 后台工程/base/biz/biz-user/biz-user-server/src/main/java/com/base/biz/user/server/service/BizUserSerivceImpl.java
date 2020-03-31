package com.base.biz.user.server.service;

import com.base.biz.user.client.model.BizUserVO;
import com.base.biz.user.client.service.BizUserService;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.model.BizUserConvertor;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.common.util.VerifyUtil;
import com.base.user.client.model.UserVO;
import com.base.user.client.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/3/30 12:03 AM
 */
@Service
public class BizUserSerivceImpl implements BizUserService {

    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private UserService userService;

    public BizUserVO login(String account, String password) throws Exception{

        VerifyUtil.isNotNull(account, "", "账户为空");
        VerifyUtil.isNotNull(account, "", "密码为空");

        BizUserDTO bizUserDTO = bizUserManager.findByAccountAndCode(account, password);

        UserVO userVO = userService.login(bizUserDTO.getCode());

        BizUserVO bizUserVO = BizUserConvertor.dto2vo(bizUserDTO);

        bizUserVO.setToken(userVO.getToken());

        return bizUserVO;
    }
}
