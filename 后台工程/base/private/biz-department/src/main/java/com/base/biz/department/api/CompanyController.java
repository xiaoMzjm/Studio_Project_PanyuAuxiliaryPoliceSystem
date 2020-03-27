package com.base.biz.department.api;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.base.biz.department.service.CompanyServiceImpl;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.department.client.model.CompanyVO;
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
 * @date:2020/3/27 10:01 PM
 */
@Api(description = "用户接口")
@Controller
@RequestMapping(value = "company", produces = {"application/json;charset=UTF-8"})
public class CompanyController {

    @Autowired
    private CompanyServiceImpl campanyService;

    @GetMapping("")
    public String companyView(){
        return "company";
    }

    @ResultFilter
    @ApiOperation(value = "获取单位树" ,  notes="获取单位树")
    @RequestMapping(value = "/listall", method = RequestMethod.POST)
    @ResponseBody
    public String listAll(){

        List<CompanyVO> companyVOList = campanyService.listAll();
        return JSON.toJSONString(Result.success(companyVOList));
    }

    @ResultFilter
    @ApiOperation(value = "添加单位" ,  notes="添加单位")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody AddParam param) throws Exception{
        String name = param.name;
        String desc = param.desc;
        String fatherCode = param.fatherCode;
        campanyService.add(name, desc, fatherCode);
        return JSON.toJSONString(Result.success(null));
    }
    static class AddParam{
        @ApiParam(name="名称",value="name",required=true)
        public String name;
        @ApiParam(name="描述",value="desc",required=true)
        public String desc;
        @ApiParam(name="父单位code",value="fatherCode")
        public String fatherCode;
    }

    @ResultFilter
    @ApiOperation(value = "删除单位" ,  notes="删除单位")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestBody DeleteParam param) throws Exception{
        String code = param.code;
        campanyService.delete(code);
        return JSON.toJSONString(Result.success(null));
    }
    static class DeleteParam {
        @ApiParam(name="code",value="code",required=true)
        public String code;
    }









    public void setCampanyService(CompanyServiceImpl campanyService) {
        this.campanyService = campanyService;
    }
}
