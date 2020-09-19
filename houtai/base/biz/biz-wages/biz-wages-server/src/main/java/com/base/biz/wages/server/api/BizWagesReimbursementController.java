package com.base.biz.wages.server.api;

import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.base.biz.expire.client.service.ExpireClient;
import com.base.biz.wages.client.model.WageListVO;
import com.base.biz.wages.server.service.BizWagesDetailService;
import com.base.biz.wages.server.service.BizWagesReimbursementService;
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
 * @date:2020/9/20 12:52 AM
 */
@Controller
@ResponseBody
@RequestMapping(value = "/wages/reimbursement", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://${crossorigin.ip}:8080")
public class BizWagesReimbursementController {

    @Autowired
    private BizWagesReimbursementService bizWagesReimbursementService;
    @Autowired
    private ExpireClient expireClient;

    /**
     * 导入工资报销封面
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
        InputStream inputStream = new ClassPathResource("static/file/工资报销封面.xlsx").getInputStream();
        bizWagesReimbursementService.importReimbursement(DateUtil.convert2Date(time,"yyyy/MM"), file, inputStream, type);
        return JSON.toJSONString(Result.success(""));
    }
}
