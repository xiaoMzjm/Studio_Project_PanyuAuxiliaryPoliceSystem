package com.base.authority.server.api;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.service.AuthorityService;
import com.base.authority.server.manager.AuthorityManager;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/3/29 9:29 PM
 */
@Api(description = "权限接口")
@Controller
@RequestMapping(value = "authority", produces = {"application/json;charset=UTF-8"})
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @ResultFilter
    @ApiOperation(value = "获取菜单树" ,  notes="获取菜单树")
    @RequestMapping(value = "/listall", method = RequestMethod.POST)
    @ResponseBody
    public String listAll(){
        List<AuthorityVO> companyVOList = authorityService.listAll();
        return JSON.toJSONString(Result.success(companyVOList));
    }
}
