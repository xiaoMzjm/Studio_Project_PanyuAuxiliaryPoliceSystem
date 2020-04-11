package com.base.biz.user.server.model;

import io.swagger.annotations.ApiParam;

/**
 * @author:小M
 * @date:2020/4/11 9:06 PM
 */
public class UpdateParam extends BizUserAddParam {

    @ApiParam(name="code",value="code")
    public String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
