package com.base.authority.client.model;

import java.util.Date;
import java.util.List;

/**
 * @author:小M
 * @date:2020/3/29 7:49 PM
 */
public class AuthorityVO {


    private String code;

    private String name;

    private String type;

    private String fatherCode;

    private List<AuthorityVO> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }

    public List<AuthorityVO> getChildren() {
        return children;
    }

    public void setChildren(List<AuthorityVO> children) {
        this.children = children;
    }
}
