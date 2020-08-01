package com.base.biz.expire.server.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.biz.expire.client.common.ExpireEnums;
import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.server.manager.ExpireManager;
import com.base.biz.expire.server.model.ExpireDO;
import com.base.biz.expire.server.service.ExpireService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/5/24 2:21 PM
 */
@Api(description = "到期提醒接口")
@Controller
@RequestMapping(value = "expire", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.50.196:8080")
public class ExpireController {

    @Autowired
    private ExpireService expireService;
    @Autowired
    private ExpireManager expireManager;

    /**
     * 创建工作证到期提醒
     * @return
     */
    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "创建工作证到期提醒" ,  notes="创建工作证到期提醒")
    @RequestMapping(value = "/createemployeecard", method = RequestMethod.POST)
    @ResponseBody
    public String createEmployeeCard(@RequestBody CreateParam param) throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("static/file/工作证到期提醒表.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        String message = expireService.createEmployeeCard(param.year,param.month, inputStream, true);
        return JSON.toJSONString(Result.success(message));
    }
    public static class CreateParam {
        public Integer year;
        public Integer month;
    }


    /**
     * 创建合同到期提醒
     * @return
     */
    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "创建合同到期提醒" ,  notes="创建合同到期提醒")
    @RequestMapping(value = "/createcontract", method = RequestMethod.POST)
    @ResponseBody
    public String createContract(@RequestBody CreateParam param) throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("static/file/合同到期提醒表.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        String message = expireService.createContract(param.year,param.month, inputStream, true);
        return JSON.toJSONString(Result.success(message));
    }

    /**
     * 创建合同到期提醒
     * @return
     */
    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "创建退休提醒" ,  notes="创建退休提醒")
    @RequestMapping(value = "/createretire", method = RequestMethod.POST)
    @ResponseBody
    public String createretire(@RequestBody CreateParam param) throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("static/file/退休提醒表.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        String message = expireService.createRetire(param.year,param.month, inputStream, true);
        return JSON.toJSONString(Result.success(message));
    }



    // http://localhost/expire/downloademployeecard?code=1_2020_5&token=83178dbf9e164bebb5bbbc38b9b864ba
    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "下载" , notes = "下载")
    @RequestMapping(value = "/downloadexpire", method = RequestMethod.GET)
    @ResponseBody
    public void downloadExpire(HttpServletResponse response, String code) throws Exception {

        ExpireDO expireDO = expireManager.getByCode(code);
        if (expireDO == null) {
            throw new RuntimeException("文件不存在，请重新生成");
        }

        // 名称
        String fileName = new String((expireDO.getFileName()+".xlsx").getBytes("UTF-8"),"ISO-8859-1");
        if(expireDO.getType() == ExpireType.EmployeeCard.getCode()) {
            fileName = "工作证到期提醒-" + fileName;
        }
        if (expireDO.getType() == ExpireType.Contract.getCode()) {
            fileName = "合同到期提醒-" + fileName;
        }
        if (expireDO.getType() == ExpireType.Retire.getCode()) {
            fileName = "退休到期提醒-" + fileName;
        }
        fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        File file = new File(expireDO.getFileUrl());
        if(!file.exists()) {
            throw new RuntimeException("文件不存在，请重新生成");
        }
        InputStream inputStream = new FileInputStream(new File(expireDO.getFileUrl()));
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        OutputStream os = response.getOutputStream();

        byte[] buff = new byte[1024];
        int i = bis.read(buff);
        while (i != -1) {
            os.write(buff, 0, buff.length);
            os.flush();
            i = bis.read(buff);
        }
        if (bis != null) {
            bis.close();
        }
        if(inputStream != null) {
            inputStream.close();
        }
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "查询工作证到期提醒列表" ,  notes="查询工作证到期提醒列表")
    @RequestMapping(value = "/listemployeecard", method = RequestMethod.POST)
    @ResponseBody
    public String listEmployeeCard(@RequestBody ListParam param) throws Exception{

        return JSON.toJSONString(Result.success(expireService.getByTime(param.year, ExpireType.EmployeeCard.getCode())));
    }
    public static class ListParam{
        public Integer year;
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "查询合同到期期提醒列表" ,  notes="查询合同到期期提醒列表")
    @RequestMapping(value = "/listcontract", method = RequestMethod.POST)
    @ResponseBody
    public String listContract(@RequestBody ListParam param) throws Exception{

        return JSON.toJSONString(Result.success(expireService.getByTime(param.year, ExpireType.Contract.getCode())));
    }


    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "退休提醒列表" ,  notes="查询合同到期期提醒列表")
    @RequestMapping(value = "/listretire", method = RequestMethod.POST)
    @ResponseBody
    public String listRetire(@RequestBody ListParam param) throws Exception{

        return JSON.toJSONString(Result.success(expireService.getByTime(param.year, ExpireType.Retire.getCode())));
    }

}
