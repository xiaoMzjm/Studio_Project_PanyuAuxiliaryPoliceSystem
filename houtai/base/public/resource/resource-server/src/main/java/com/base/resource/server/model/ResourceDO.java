package com.base.resource.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2020/4/5 8:25 PM
 */
@Entity
@Table(name = "resource",
    indexes = {@Index(name = "idx_name",  columnList="name", unique = true)})
public class ResourceDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date GmtCreate;

    @Column(nullable = false)
    private Date GmtModified;

    @Column(length = 256 , nullable = false)
    private String path;

    @Column(length = 256 , nullable = false)
    private String name;

    @Column(length = 64 , nullable = false)
    private String oriName;

    @Column(length = 12 , nullable = false)
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
