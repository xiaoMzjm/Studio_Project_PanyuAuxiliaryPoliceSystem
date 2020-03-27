package com.base.biz.department.model;

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
 * @date:2020/3/27 2:01 AM
 */
@Entity
@Table(name = "company",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true),
        @Index(name = "idx_name",  columnList="name", unique = true),
        @Index(name = "idx_father_code",  columnList="fatherCode", unique = true)})
public class CompanyDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date GmtCreate;

    @Column(nullable = false)
    private Date GmtModified;

    // 单位code
    @Column(length = 64 , nullable = false)
    private String code;

    // 单位名称
    @Column(length = 64 , nullable = false)
    private String name;

    // 单位描述
    @Column(length = 2048)
    private String description;

    // 父单位
    @Column(length = 64)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }
}
