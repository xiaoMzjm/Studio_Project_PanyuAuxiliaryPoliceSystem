package com.base.biz.user.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2020/4/5 2:03 PM
 */
@Entity
@Table(name = "family_member")
public class FamilyMemberDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date gmtCreate;

    @Column(nullable = false)
    private Date gmtModified;

    @Column(length = 64)
    private String userCode; // 关联用户code

    @Column(length = 64)
    private String name; // 姓名

    @Column(length = 64)
    private String relation; // 关系

    @Column(length = 128)
    private String company; // 单位

    @Column(length = 128)
    private String duty; // 职务

    @Column(length = 64)
    private String identityCard; // 身份证

    @Column(length = 64)
    private String phone; // 电话

    @Column
    private Integer politicalLandscapeCode; // 政治面貌 @see PoliticalLandscapeEnum

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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoliticalLandscapeCode() {
        return politicalLandscapeCode;
    }

    public void setPoliticalLandscapeCode(Integer politicalLandscapeCode) {
        this.politicalLandscapeCode = politicalLandscapeCode;
    }
}
