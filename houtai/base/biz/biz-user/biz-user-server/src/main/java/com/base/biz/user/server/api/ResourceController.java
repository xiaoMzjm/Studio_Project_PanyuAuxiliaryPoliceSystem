package com.base.biz.user.server.api;

import java.io.File;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.common.util.UUIDUtil;
import com.base.resource.client.common.Constant;
import com.base.resource.client.model.SingleUpdateVO;
import com.base.resource.client.service.ResourceService;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/4/5 4:58 PM
 */
@Api(description = "资源接口")
@Controller
@RequestMapping(value = "/resource", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.1.5:8080")
public class ResourceController {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private ResourceService resourceService;

    @ResultFilter
    @TokenFilter
    @ApiOperation(value = "上传图片")
    @RequestMapping(value = "/uploadpic" ,method = RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
    @ResponseBody
    public String uploadPic(
                            @RequestParam(value = "file", required = false)MultipartFile file,HttpSession session) throws Exception{

        if (file == null) {
            return JSON.toJSONString(Result.error("未上传文件"));
        }

        if(StringUtils.isEmpty(diskStaticUrl)) {
            return JSON.toJSONString(Result.error("ResourceStaticUrl is null"));
        }

        String oriName = file.getOriginalFilename();

        String diskImagesPath = diskStaticUrl + "images";

        // 创建目录
        File pathFolder = new File(diskImagesPath);
        if (!pathFolder.exists()) {
            pathFolder.mkdirs();
        }

        // 保存文件
        String name = UUIDUtil.get();
        String url = diskImagesPath + "/" + name + "." + Constant.PIC_EXT;
        file.transferTo(new File(url));

        // web访问地址
        String webUrlPrefix = "/static/images/";

        // 保存数据库
        resourceService.add(webUrlPrefix, name, Constant.PIC_EXT, oriName);

        SingleUpdateVO singleUpdateVO = new SingleUpdateVO();
        singleUpdateVO.setCode(name);
        singleUpdateVO.setUrl(webUrlPrefix + name + "." + Constant.PIC_EXT);
        return JSON.toJSONString(Result.success(singleUpdateVO));
    }
}
