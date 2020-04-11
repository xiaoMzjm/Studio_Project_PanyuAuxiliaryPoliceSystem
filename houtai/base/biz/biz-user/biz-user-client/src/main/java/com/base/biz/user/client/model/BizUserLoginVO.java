package com.base.biz.user.client.model;

/**
 * @author:小M
 * @date:2020/3/30 12:02 AM
 */
public class BizUserLoginVO {

    private String code;

    private String token;

    private String name; // 姓名

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
