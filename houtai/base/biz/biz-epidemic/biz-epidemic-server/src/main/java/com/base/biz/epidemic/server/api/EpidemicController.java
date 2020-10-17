package com.base.biz.epidemic.server.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import com.base.biz.epidemic.client.model.EpidemicVO;
import com.base.biz.epidemic.server.service.EpidemicInnerService;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.common.UserConstant;
import com.base.user.client.model.TokenFilter;
import com.base.user.client.model.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/4/6 1:20 PM
 */
@Api(description = "人员管理枚举")
@Controller
@ResponseBody
@RequestMapping(value = "/epidemic/" , produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "http://${crossorigin.ip}")
public class EpidemicController {

    @Autowired
    private EpidemicInnerService epidemicService;

    @ResultFilter
    @TokenFilter
    @RequestMapping("add")
    public String add(@RequestBody AddRequest addRequest)throws Exception{

        epidemicService.add(addRequest.companyCode,addRequest.type,
            addRequest.location,addRequest.userCode,
            addRequest.beginTime, addRequest.endTime,
            addRequest.detail,addRequest.leaderCode,addRequest.detailLocation);

        return JSON.toJSONString(Result.success(""));
    }

    static class AddRequest{
        public String code;
        public String companyCode;
        public Integer type;
        public Integer location;
        public String userCode;
        public String beginTime;
        public String endTime;
        public String detail;
        public String leaderCode;
        public String detailLocation;
    }


    @ResultFilter
    @TokenFilter
    @RequestMapping("select")
    public String select(@RequestBody EpidemicSelectParam epidemicSelectParam)throws Exception{

        List<EpidemicVO> result= epidemicService.select(epidemicSelectParam);

        return JSON.toJSONString(Result.success(result));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("selectCurrent")
    public String selectCurrent(HttpServletRequest request)throws Exception{

        UserVO userVO = (UserVO)request.getAttribute(UserConstant.REQUEST_USER);
        List<EpidemicVO> result= epidemicService.selectCurrent(userVO.getCode());

        return JSON.toJSONString(Result.success(result));
    }


    @ResultFilter
    @TokenFilter
    @RequestMapping("confirm")
    public String confirm(@RequestBody ConfirmRequest confirmRequest)throws Exception{

        epidemicService.confirm(confirmRequest.code);
        return JSON.toJSONString(Result.success(""));
    }
    static class ConfirmRequest {
        public String code;
    }


    @ResultFilter
    @TokenFilter
    @RequestMapping("delete")
    public String delete(@RequestBody DeleteRequest deleteRequest)throws Exception{

        epidemicService.delete(deleteRequest.code);
        return JSON.toJSONString(Result.success(""));
    }
    static class DeleteRequest {
        public String code;
    }


    @ResultFilter
    @TokenFilter
    @RequestMapping("update")
    public String update(@RequestBody AddRequest addRequest)throws Exception{

        epidemicService.update(addRequest.code, addRequest.companyCode,addRequest.type,
            addRequest.location,addRequest.userCode,
            addRequest.beginTime, addRequest.endTime,
            addRequest.detail,addRequest.leaderCode,addRequest.detailLocation);

        return JSON.toJSONString(Result.success(""));
    }

}
