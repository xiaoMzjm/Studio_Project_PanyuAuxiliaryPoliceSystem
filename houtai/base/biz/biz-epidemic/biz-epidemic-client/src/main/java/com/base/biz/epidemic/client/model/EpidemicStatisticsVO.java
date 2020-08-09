package com.base.biz.epidemic.client.model;

/**
 * @author:小M
 * @date:2020/8/4 10:56 PM
 */
public class EpidemicStatisticsVO {

    private Integer day;

    private String zhengGongFileName;

    private String zhengGongFileCode;

    private String shiJuFileName;

    private String shiJuFileCode;

    private String remark;

    public String getZhengGongFileName() {
        return zhengGongFileName;
    }

    public void setZhengGongFileName(String zhengGongFileName) {
        this.zhengGongFileName = zhengGongFileName;
    }

    public String getZhengGongFileCode() {
        return zhengGongFileCode;
    }

    public void setZhengGongFileCode(String zhengGongFileCode) {
        this.zhengGongFileCode = zhengGongFileCode;
    }

    public String getShiJuFileName() {
        return shiJuFileName;
    }

    public void setShiJuFileName(String shiJuFileName) {
        this.shiJuFileName = shiJuFileName;
    }

    public String getShiJuFileCode() {
        return shiJuFileCode;
    }

    public void setShiJuFileCode(String shiJuFileCode) {
        this.shiJuFileCode = shiJuFileCode;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
