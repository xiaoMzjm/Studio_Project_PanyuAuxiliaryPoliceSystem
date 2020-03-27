package com.base.biz.department.api;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:小M
 * @date:2020/3/27 10:01 PM
 */
@Api(description = "用户接口")
@Controller
@RequestMapping("company")
public class DepartmentController {

    @GetMapping("/index")
    public String index(){
        return "index"; //当浏览器输入/index时，会返回 /templates/home.html页面
    }
}
