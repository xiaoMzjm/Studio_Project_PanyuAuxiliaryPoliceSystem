package com.base.littleapp.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.littleapp.service.WxLoginService;
import com.base.user.filter.TokenAnnotation;
import com.base.user.model.UserDTO;
import com.base.littleapp.model.WxUserInfoVO;
import com.base.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
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
@Api(description = "微信登陆接口")
@Controller
@ResponseBody
@RequestMapping("user/wx")
@RestController
public class WxLoginController {

    @Autowired
    private WxLoginService wxLoginService;

    /**
     * http://localhost/wx/login
     * 登陆，返回带skey
     * @return
     */
    @ApiOperation(value = "微信登陆" ,  notes="微信登陆")
    @RequestMapping(value = "/login" , method = RequestMethod.POST , produces = {"application/json;charset=UTF-8"})
    @TokenAnnotation
    public String login(@RequestBody WxLoginController.WxRegisterParam registerParam,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        System.out.println("code="+registerParam.code + ",encryptedData="+registerParam.encryptedData+",iv="+registerParam.iv);
        Result<UserDTO> wxLoginResult = wxLoginService.login(registerParam.code, registerParam.encryptedData ,registerParam.iv);
        if(!wxLoginResult.getSuccess()) {
            return JSON.toJSONString(Result.error(wxLoginResult.getErrorCode() , wxLoginResult.getErrorMsg()));
        }
        WxUserInfoVO wxUserInfoVO = new WxUserInfoVO();
        BeanUtils.copyProperties(wxLoginResult.getData() , wxUserInfoVO);
        Result<WxUserInfoVO> result = Result.success(wxUserInfoVO);
        response.addHeader("token" , result.getData().getToken());
        Cookie cookie = new Cookie("token" , result.getData().getToken());
        response.addCookie(cookie);
        return JSON.toJSONString(result);
    }

    static class WxRegisterParam{
        @ApiParam(name="用户code",value="code",required=true)
        public String code;
        @ApiParam(name="密文",value="encryptedData",required=true)
        public String encryptedData;
        @ApiParam(name="解密向量",value="iv",required=true)
        public String iv;
    }

    public void setWxLoginService(WxLoginService wxLoginService) {
        this.wxLoginService = wxLoginService;
    }
}
