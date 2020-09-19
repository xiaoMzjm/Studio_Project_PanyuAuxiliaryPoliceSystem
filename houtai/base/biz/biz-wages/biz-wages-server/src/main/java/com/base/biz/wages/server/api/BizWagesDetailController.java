package com.base.biz.wages.server.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.wages.client.enums.WagesDetailImportTypeEnum;
import com.base.biz.wages.client.model.WageListVO;
import com.base.biz.wages.server.service.BizWagesDetailService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.common.util.DateUtil;
import com.base.user.client.model.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
@RequestMapping(value = "/wages/detail", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.50.196:8080")
public class BizWagesDetailController {

    @Autowired
    private BizWagesDetailService bizWagesDetailService;
    @Autowired
    private ExpireClient expireClient;

    /**
     * 导入工资明细
     * @param time
     * @param type
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
    @ResultFilter
    @TokenFilter
    public String importFile(@RequestParam String time, @RequestParam Integer type, @RequestParam(value = "file")MultipartFile file) throws Exception{
        InputStream inputStream = new ClassPathResource("static/file/工资明细.xlsx").getInputStream();
        bizWagesDetailService.importDetail(DateUtil.convert2Date(time,"yyyy/MM"), file, inputStream, type);
        return JSON.toJSONString(Result.success(""));
    }

    @RequestMapping("/list")
    @ResultFilter
    @TokenFilter
    public String list(@RequestBody ListReq req){
        List<WageListVO> wageListVOList = bizWagesDetailService.list(req.year, req.type);
        return JSON.toJSONString(wageListVOList);
    }
    public static class ListReq{
        public Integer year;
        public Integer type;
    }




    /**
     * 下载
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResultFilter
    @TokenFilter
    public void download(HttpServletResponse response, String code, Integer type) throws Exception{
        Assert.notNull(code , "code is null");
        Assert.notNull(type , "type is null");

        ExpireVO expireVO = expireClient.findByCode(code);
        Assert.notNull(expireVO , "文件不存在");

        Integer index = null;
        if(WagesDetailImportTypeEnum.THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.FOUR.getValue().equals(type)) {
            index = 0;
        }else if(WagesDetailImportTypeEnum.SYSTEM_THREE.getValue().equals(type) ||
            WagesDetailImportTypeEnum.SYSTEM_FOUR.getValue().equals(type)) {
            index = 1;
        }else {
            index = 2;
        }

        // 名称
        String[] nameArray = expireVO.getName().split("@");
        String[] urlArray = expireVO.getFileUrl().split("@");
        String fileName = nameArray[index] + ".xlsx";
        fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        String url = urlArray[index];
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        File file = new File(url);
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
