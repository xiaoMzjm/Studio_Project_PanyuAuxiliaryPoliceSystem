package com.base.biz.web.api;

import com.base.biz.web.filter.ApiFilter;
import com.base.common.exception.BaseException;
import com.base.common.util.LogUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://localhost:8099/swagger-ui.html
 * @author:小M
 * @date:2018/11/4 下午9:59
 */
@RestController
public class BaseResource {

    @ApiOperation(value = "index" , notes = "index")
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(){
        LogUtil.Info("info test");
        return "信息展示";
    }

    @ApiFilter
    @ApiOperation(value = "hello接口" , notes = "hello")
    @RequestMapping(value = "/hello/{name}" , method = RequestMethod.GET)
    public Object hello(@PathVariable String name){
        return "hello " + name ;
    }

    @ApiFilter
    @ApiOperation(value = "抛异常" , notes = "抛异常")
    @RequestMapping(value = "/throwEx" , method = RequestMethod.GET)
    public Object throwEx() throws Exception{
        if(true) {
            throw new BaseException("error code " , "error Msg");
        }
        return "hello " ;
    }
}
