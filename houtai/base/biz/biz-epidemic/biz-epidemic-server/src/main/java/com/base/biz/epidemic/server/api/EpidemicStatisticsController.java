package com.base.biz.epidemic.server.api;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;

import com.base.biz.epidemic.server.service.EpidemicInnerService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/8/2 6:39 PM
 */
@Api(description = "人员管理枚举")
@Controller
@ResponseBody
@RequestMapping(value = "/epidemic/statistics/" , produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "http://192.168.50.196:8080")
public class EpidemicStatisticsController {

    @Autowired
    private EpidemicInnerService epidemicInnerService;

    @ResultFilter
    @TokenFilter
    @RequestMapping("create")
    public String create(@RequestBody CreateRequest createRequest)throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("static/file/防疫政工办统计表.xlsx");
        InputStream zhengGongBanFile = classPathResource.getInputStream();

        ClassPathResource classPathResource2 = new ClassPathResource("static/file/防疫市局统计表.xlsx");
        InputStream shiJuFile = classPathResource2.getInputStream();

        epidemicInnerService.createStatistics(zhengGongBanFile, shiJuFile, createRequest.date);
        return JSON.toJSONString(Result.success(""));
    }
    static class CreateRequest{
        String date;
    }

}
