package com.base.littleapp.manager;

import java.util.Date;

import com.base.littleapp.wrapper.WxWapper;
import com.base.common.exception.BaseException;
import com.base.common.util.UUIDUtil;
import com.base.user.manager.UserManager;
import com.base.littleapp.model.WxRequireLoginResultDTO;
import com.base.user.model.UserDTO;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private UserManager userManager;


    /**
     * 微信登录，获取登录用户信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     * @throws Exception
     */
    public UserDTO login(String code , String encryptedData , String iv)throws Exception {

        // 2.获取openId,session
        WxRequireLoginResultDTO wxRequireLoginResultDTO = wxWapper.requireWxForLogin(code);

        // 3.解密获取用户详情
        UserDTO userDTO = wxWapper.decodeUserInfo(encryptedData , wxRequireLoginResultDTO.getSession_key() , iv);

        // 4.保存到数据库
        userDTO = this.save2db(userDTO);

        return userDTO;
    }

    /**
     * 保存到数据库
     * @param wxUserInfoDTO
     */
    private synchronized UserDTO save2db(UserDTO wxUserInfoDTO) throws BaseException {

        UserDTO userDTO = userManager.findByOpenId(wxUserInfoDTO.getOpenId());

        // 无则新增
        if(userDTO == null) {
            userDTO = new UserDTO();
            BeanUtils.copyProperties(wxUserInfoDTO , userDTO);
            String token = UUIDUtil.get();
            userDTO.setToken(token);
            userManager.save(userDTO);
            wxUserInfoDTO.setToken(token);
        }
        // 有则更新token
        else {
            String token = UUIDUtil.get();
            userDTO.setToken(token);
            userManager.updateToken(userDTO.getId() , token);
            userDTO = userManager.findByToken(token);
            BeanUtils.copyProperties(userDTO , wxUserInfoDTO);
        }
        return wxUserInfoDTO;
    }

    public void setWxWapper(WxWapper wxWapper) {
        this.wxWapper = wxWapper;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
