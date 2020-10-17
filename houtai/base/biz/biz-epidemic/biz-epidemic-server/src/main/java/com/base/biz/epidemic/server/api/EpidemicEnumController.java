package com.base.biz.epidemic.server.api;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicLocationEnum;
import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicStatusEnum;
import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicTypeEnum;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/4/6 1:20 PM
 */
@Api(description = "人员管理枚举")
@Controller
@ResponseBody
@RequestMapping(value = "/epidemic/enum/" , produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "http://${crossorigin.ip}")
public class EpidemicEnumController {

    @ResultFilter
    @TokenFilter
    @RequestMapping("all")
    public String enums(){

        Map<String,Object> map = new HashMap<>();
        map.put("type", EpidemicTypeEnum.getAll());
        map.put("location", EpidemicLocationEnum.getAll());
        map.put("status", EpidemicStatusEnum.getAll());

        return JSON.toJSONString(Result.success(map));
    }



}
