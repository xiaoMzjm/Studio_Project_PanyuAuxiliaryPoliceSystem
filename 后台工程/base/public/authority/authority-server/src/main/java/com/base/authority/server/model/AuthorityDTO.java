package com.base.authority.server.model;

import java.util.Date;

import javax.persistence.Column;

/**
 * @author:小M
 * @date:2020/3/29 7:50 PM
 */
public class AuthorityDTO {

    private Long id;

    private Date GmtCreate;

    private Date GmtModified;

    private String code;

    private String name;

    private String type;

    private String fatherCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return GmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        GmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return GmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        GmtModified = gmtModified;
    }

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
}
