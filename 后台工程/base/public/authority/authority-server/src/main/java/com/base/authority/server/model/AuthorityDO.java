package com.base.authority.server.model;

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
 * @date:2020/3/29 7:50 PM
 */
@Entity
@Table(name = "authority",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true)})
public class AuthorityDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date GmtCreate;

    @Column(nullable = false)
    private Date GmtModified;

    @Column(nullable = false, length = 64)
    private String code;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 64)
    private String type;

    @Column(nullable = false, length = 64)
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
