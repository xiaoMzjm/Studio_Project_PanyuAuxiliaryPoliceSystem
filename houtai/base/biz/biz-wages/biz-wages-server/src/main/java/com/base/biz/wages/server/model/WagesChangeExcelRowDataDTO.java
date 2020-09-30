package com.base.biz.wages.server.model;

import java.math.BigDecimal;

/**
 * @author:小M
 * @date:2020/9/29 6:20 PM
 */
public class WagesChangeExcelRowDataDTO {

    private Integer sequence;
    private String departmentName;
    private Integer nowPeopleNum;
    private Integer lastPeopleNum ;
    private BigDecimal shouldPayMoney ;
    private BigDecimal realPayMoney;
    private String changeDetail;

    public WagesChangeExcelRowDataDTO() {
    }

    public WagesChangeExcelRowDataDTO(Integer sequence, String departmentName, Integer nowPeopleNum,
                                      Integer lastPeopleNum, BigDecimal shouldPayMoney, BigDecimal realPayMoney,
                                      String changeDetail) {
        this.sequence = sequence;
        this.departmentName = departmentName;
        this.nowPeopleNum = nowPeopleNum;
        this.lastPeopleNum = lastPeopleNum;
        this.shouldPayMoney = shouldPayMoney;
        this.realPayMoney = realPayMoney;
        this.changeDetail = changeDetail;
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

    public Integer getNowPeopleNum() {
        return nowPeopleNum;
    }

    public void setNowPeopleNum(Integer nowPeopleNum) {
        this.nowPeopleNum = nowPeopleNum;
    }

    public Integer getLastPeopleNum() {
        return lastPeopleNum;
    }

    public void setLastPeopleNum(Integer lastPeopleNum) {
        this.lastPeopleNum = lastPeopleNum;
    }

    public BigDecimal getShouldPayMoney() {
        return shouldPayMoney;
    }

    public void setShouldPayMoney(BigDecimal shouldPayMoney) {
        this.shouldPayMoney = shouldPayMoney;
    }

    public BigDecimal getRealPayMoney() {
        return realPayMoney;
    }

    public void setRealPayMoney(BigDecimal realPayMoney) {
        this.realPayMoney = realPayMoney;
    }

    public String getChangeDetail() {
        return changeDetail;
    }

    public void setChangeDetail(String changeDetail) {
        this.changeDetail = changeDetail;
    }
}
