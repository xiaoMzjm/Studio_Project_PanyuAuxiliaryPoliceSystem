package com.base.biz.user.server.api;

import com.alibaba.fastjson.JSON;

import com.base.biz.user.client.model.BizUserVO;
import com.base.biz.user.client.service.BizUserService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/3/30 12:49 AM
 */
@Api(description = "用户接口")
@Controller
@RequestMapping(value = "user", produces = {"application/json;charset=UTF-8"})
public class BizUserController {

    @Autowired
    private BizUserService bizUserService;

    @ApiOperation(value = "人员列表页面" ,  notes="人员列表页面")
    @GetMapping("")
    public String userListView(){
        return "userlist";
    }

    @ResultFilter
    @ApiOperation(value = "登录" ,  notes="登录")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody LoginParam param) throws Exception{
        String account = param.account;
        String password = param.password;
        BizUserVO bizUserVO = bizUserService.login(account, password);
        return JSON.toJSONString(Result.success(bizUserVO));
    }
    static class LoginParam{
        @ApiParam(name="账号",value="account",required=true)
        public String account;
        @ApiParam(name="密码",value="password",required=true)
        public String password;
    }
}
