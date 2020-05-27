package com.base.main.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:小M
 * @date:2020/4/26 12:50 AM
 */
@Api(description = "单位接口")
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.1.5:8080")
public class PageController {

    @ApiOperation(value = "单位管理" ,  notes="单位管理")
    @GetMapping("/CompanyManager/*")
    public String companyView(){
        return "index";
    }

    @ApiOperation(value = "人员管理" ,  notes="人员管理")
    @GetMapping("/UserManager/*")
    public String userView(){
        return "index";
    }

    @ApiOperation(value = "到期提醒" ,  notes="到期提醒")
    @GetMapping("/ExpireManager/*")
    public String expireView(){
        return "index";
    }

    @ApiOperation(value = "登录" ,  notes="登录")
    @GetMapping("/login")
    public String login(){
        return "index";
    }
}
