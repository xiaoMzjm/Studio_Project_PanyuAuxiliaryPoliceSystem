package com.base.biz.epidemic.client.model;

/**
 * @author:小M
 * @date:2020/4/6 1:38 PM
 */
public class EnumVO {

    private Integer code;
    private String name;

    public EnumVO(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
