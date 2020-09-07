package com.base.biz.user.server.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.base.common.annotation.ResultFilter;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/4/5 4:58 PM
 */
@Api(description = "系统接口")
@Controller
@RequestMapping(value = "/system", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.50.196:8080")
public class SystemController {

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "下载帮助文档" , notes = "下载帮助文档")
    @RequestMapping(value = "/downloadhelp", method = RequestMethod.GET)
    @ResponseBody
    public void downloadhelp(HttpServletResponse response) throws Exception {


        ClassPathResource classPathResource = new ClassPathResource("static/file/《广州市公安局番禺分局政工系统》用户使用手册.docx");
        InputStream inputStream = classPathResource.getInputStream();
        String fileName = new String("《广州市公安局番禺分局政工系统》用户使用手册.docx".getBytes("UTF-8"),"ISO-8859-1");

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
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
    }
}
