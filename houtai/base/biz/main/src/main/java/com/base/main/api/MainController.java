package com.base.main.api;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/4/16 1:56 AM
 */
@Controller
@ResponseBody
public class MainController {

    @RequestMapping("/index")
    public String index(){
        return "hello world";
    }
}
