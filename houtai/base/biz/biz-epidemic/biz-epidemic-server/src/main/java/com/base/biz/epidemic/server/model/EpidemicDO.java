package com.base.biz.epidemic.server.model;

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
 * @date:2020/8/2 3:11 PM
 */
@Entity
@Table(name = "epidemic" ,
    indexes = {
        @Index(name = "idx_code",  columnList="code", unique = true),
    })
public class EpidemicDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64 , nullable = false)
    private String code;

    @Column(nullable = false)
    private Date gmtCreate;

    @Column(nullable = false)
    private Date gmtModified;

    @Column(length = 64 , nullable = false)
    private String companyCode;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private Integer location;

    @Column(length =  64, nullable = false)
    private String userCode;

    @Column(nullable = false)
    private Date beginTime;

    @Column(nullable = false)
    private Date endTime;

    @Column(length =  4096)
    private String detail;

    @Column(length =  64, nullable = false)
    private String leaderCode;

    @Column(length =  128)
    private String detailLocation;

    @Column(nullable = false)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLeaderCode() {
        return leaderCode;
    }

    public void setLeaderCode(String leaderCode) {
        this.leaderCode = leaderCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(String detailLocation) {
        this.detailLocation = detailLocation;
    }
}
