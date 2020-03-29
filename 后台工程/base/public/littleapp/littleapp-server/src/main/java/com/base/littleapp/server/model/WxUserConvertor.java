package com.base.littleapp.server.model;

import com.base.littleapp.client.model.WxUserInfoVO;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/3/29 10:47 PM
 */
public class WxUserConvertor {

    public static WxUserInfoVO dto2vo(WxUserInfoDTO wxUserInfoDTO){
        WxUserInfoVO wxUserInfoVO = new WxUserInfoVO();
        BeanUtils.copyProperties(wxUserInfoDTO , wxUserInfoVO);
        return wxUserInfoVO;
    }
}
