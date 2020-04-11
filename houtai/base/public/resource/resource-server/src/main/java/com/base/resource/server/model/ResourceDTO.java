package com.base.resource.server.model;

import java.util.Date;

import javax.persistence.Column;

/**
 * @author:小M
 * @date:2020/4/5 8:44 PM
 */
public class ResourceDTO {

    private Long id;

    private Date GmtCreate;

    private Date GmtModified;

    private String path;

    private String name;

    private String oriName;

    private String ext;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriName() {
        return oriName;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
