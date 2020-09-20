package com.base.biz.wages.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author:小M
 * @date:2020/8/30 11:57 AM
 */
@Entity
@Table(name="wages",indexes = {@Index(name = "idx_time",  columnList = "time")})
public class WagesDO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date gmtCreate;

    @Column(nullable = false)
    private Date gmtModified;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false, length = 64)
    private String departmentName;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 64)
    private String sex;

    @Column(nullable = false, length = 64)
    private String bankCode;

    @Column(nullable = false, length = 64)
    private String identityCard;

    @Column(nullable = false)
    private Integer continuityWorkDay;

    @Column(nullable = false)
    private Integer basePay; // 基本工资

    @Column(nullable = false)
    private Integer allowance; // 连续租赁岗位津贴

    @Column(nullable = false)
    private Integer promotionMoney; // 符合晋升待遇

    @Column(nullable = false)
    private Integer wagesPayable; // 应发工资合计

    @Column(nullable = false)
    private Integer departmentSocialSecurityMoney; // 单位扣缴社保费

    @Column(nullable = false)
    private Integer personalSocialSecurityMoney; // 个人扣缴社保费

    @Column(nullable = false)
    private Integer departmentAccumulat; // 单位扣缴公积金费

    @Column(nullable = false)
    private Integer personalAccumulationFund; // 个人扣缴公积金费

    @Column(nullable = false)
    private Integer personalIncomeTax; // 应缴个税金额

    @Column(nullable = false)
    private Integer realWages; // 实发工资合计

    @Column(nullable = true, length = 512)
    private String remark;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private Integer type;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Integer getContinuityWorkDay() {
        return continuityWorkDay;
    }

    public void setContinuityWorkDay(Integer continuityWorkDay) {
        this.continuityWorkDay = continuityWorkDay;
    }

    public Integer getBasePay() {
        return basePay;
    }

    public void setBasePay(Integer basePay) {
        this.basePay = basePay;
    }

    public Integer getAllowance() {
        return allowance;
    }

    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
    }

    public Integer getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(Integer promotionMoney) {
        this.promotionMoney = promotionMoney;
    }

    public Integer getWagesPayable() {
        return wagesPayable;
    }

    public void setWagesPayable(Integer wagesPayable) {
        this.wagesPayable = wagesPayable;
    }

    public Integer getDepartmentSocialSecurityMoney() {
        return departmentSocialSecurityMoney;
    }

    public void setDepartmentSocialSecurityMoney(Integer departmentSocialSecurityMoney) {
        this.departmentSocialSecurityMoney = departmentSocialSecurityMoney;
    }

    public Integer getPersonalSocialSecurityMoney() {
        return personalSocialSecurityMoney;
    }

    public void setPersonalSocialSecurityMoney(Integer personalSocialSecurityMoney) {
        this.personalSocialSecurityMoney = personalSocialSecurityMoney;
    }

    public Integer getDepartmentAccumulat() {
        return departmentAccumulat;
    }

    public void setDepartmentAccumulat(Integer departmentAccumulat) {
        this.departmentAccumulat = departmentAccumulat;
    }

    public Integer getPersonalAccumulationFund() {
        return personalAccumulationFund;
    }

    public void setPersonalAccumulationFund(Integer personalAccumulationFund) {
        this.personalAccumulationFund = personalAccumulationFund;
    }

    public Integer getPersonalIncomeTax() {
        return personalIncomeTax;
    }

    public void setPersonalIncomeTax(Integer personalIncomeTax) {
        this.personalIncomeTax = personalIncomeTax;
    }

    public Integer getRealWages() {
        return realWages;
    }

    public void setRealWages(Integer realWages) {
        this.realWages = realWages;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
