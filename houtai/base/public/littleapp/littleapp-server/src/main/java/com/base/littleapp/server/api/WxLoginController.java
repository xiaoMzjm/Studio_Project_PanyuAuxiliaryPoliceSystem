package com.base.littleapp.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.common.annotation.ResultFilter;
import com.base.littleapp.client.model.WxUserInfoVO;
import com.base.littleapp.client.service.WxService;
import com.base.littleapp.server.service.WxLoginServiceImpl;
import com.base.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信登陆接口
 * @author:小M
 * @date:2019/1/13 10:41 PM
 */
//@Api(description = "微信登陆接口")
//@Controller
//@ResponseBody
//@RequestMapping("user/wx")
//@RestController
public class WxLoginController {

    @Autowired
    private WxService wxLoginService;

    /**
     * http://localhost/wx/login
     * 登陆，返回带skey
     * @return
     */
    //@ResultFilter
    //@ApiOperation(value = "微信登陆" ,  notes="微信登陆")
    //@RequestMapping(value = "/login" , method = RequestMethod.POST , produces = {"application/json;charset=UTF-8"})
    public String login(@RequestBody WxLoginController.WxRegisterParam registerParam,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        System.out.println("code="+registerParam.code + ",encryptedData="+registerParam.encryptedData+",iv="+registerParam.iv);
        WxUserInfoVO wxUserInfoVO = wxLoginService.login(registerParam.code, registerParam.encryptedData ,registerParam.iv);
        if (wxUserInfoVO == null) {
            return JSON.toJSONString(Result.error("登录失败，请重试"));
        }
        return JSON.toJSONString(Result.success(wxUserInfoVO));
    }

    static class WxRegisterParam{
        @ApiParam(name="用户code",value="code",required=true)
        public String code;
        @ApiParam(name="密文",value="encryptedData",required=true)
        public String encryptedData;
        @ApiParam(name="解密向量",value="iv",required=true)
        public String iv;
    }

    public void setWxLoginService(WxLoginServiceImpl wxLoginService) {
        this.wxLoginService = wxLoginService;
    }
}
