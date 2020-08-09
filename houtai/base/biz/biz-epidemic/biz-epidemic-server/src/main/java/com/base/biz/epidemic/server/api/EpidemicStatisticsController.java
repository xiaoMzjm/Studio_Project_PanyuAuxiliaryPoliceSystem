package com.base.biz.epidemic.server.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.biz.epidemic.client.model.EpidemicStatisticsVO;
import com.base.biz.epidemic.server.service.EpidemicInnerService;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClientService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.common.exception.BaseException;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private ExpireClientService expireClientService;

    @ResultFilter
    @TokenFilter
    @RequestMapping("create")
    public String create(@RequestBody CreateRequest createRequest)throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("static/file/防疫政工办统计表.xlsx");
        InputStream zhengGongBanFile = classPathResource.getInputStream();

        ClassPathResource classPathResource2 = new ClassPathResource("static/file/防疫市局统计表.xlsx");
        InputStream shiJuFile = classPathResource2.getInputStream();

        epidemicInnerService.createStatistics(zhengGongBanFile, shiJuFile, createRequest.date, createRequest.remark);
        return JSON.toJSONString(Result.success(""));
    }
    static class CreateRequest{
        public String date;
        public String remark;
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("select")
    public String select(@RequestBody SelectRequest selectRequest)throws Exception{
        List<EpidemicStatisticsVO> epidemicStatisticsVOList = epidemicInnerService.selectStatistics(selectRequest.date);
        return JSON.toJSONString(Result.success(epidemicStatisticsVOList));
    }
    static class SelectRequest{
        public String date;
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "下载" , notes = "下载")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadExpire(HttpServletResponse response, String code) throws Exception {


        if(StringUtils.isEmpty(code)) {
            throw new BaseException("code为空");
        }
        String[] codeArray = code.split("@");
        String expireCode = codeArray[0];
        String type = codeArray[1];
        String fileName = "";
        String fileUrl = "";

        ExpireVO expireVO = expireClientService.findByCode(expireCode);
        if (expireVO == null) {
            throw new BaseException("文件不存在，请重新生成");
        }
        String name = expireVO.getName();
        String url = expireVO.getFileUrl();
        if(Integer.valueOf(type).equals(1)) {
            fileName = name.split("@")[0];
            fileUrl = url.split("@")[0];
        }else {
            fileName = name.split("@")[1];
            fileUrl = url.split("@")[1];
        }

        // 名称
        fileName = new String((fileName+".xlsx").getBytes("UTF-8"),"ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        File file = new File(fileUrl);
        if(!file.exists()) {
            throw new RuntimeException("文件不存在，请重新生成");
        }
        InputStream inputStream = new FileInputStream(file);
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

}
