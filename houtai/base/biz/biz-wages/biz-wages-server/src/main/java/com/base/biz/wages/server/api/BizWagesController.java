package com.base.biz.wages.server.api;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;

import com.base.biz.wages.server.service.BizWagesService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.common.util.DateUtil;
import com.base.user.client.model.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/8/30 11:47 AM
 */
@Controller
@ResponseBody
@RequestMapping(value = "/wages", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.50.196:8080")
public class BizWagesController {

    @Autowired
    private BizWagesService bizWagesService;

    @RequestMapping("/list")
    @ResultFilter
    @TokenFilter
    public String list(@RequestBody ListReq req){
        return null;
    }
    public static class ListReq{
        public String time;
    }


    @RequestMapping(value = "/import", method = RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
    @ResultFilter
    @TokenFilter
    public String importFile(@RequestParam String time, @RequestParam Integer type, @RequestParam(value = "file")MultipartFile file) throws Exception{
        InputStream inputStream = new ClassPathResource("static/file/工资明细.xlsx").getInputStream();
        bizWagesService.importDetail(DateUtil.convert2Date(time,"yyyy/MM"), file, inputStream, type);
        return JSON.toJSONString(Result.success(""));
    }
    public static class ImportReq{
        public String time;
        public Integer type;
    }

}
