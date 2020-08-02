package com.base.biz.epidemic.server.model;

import java.util.Date;
import java.util.List;

/**
 * @author:小M
 * @date:2020/8/2 3:33 PM
 */
public class EpidemicSelectParam {

    private List<String> companyCodeList;

    private List<Integer> typeList;

    private List<Integer> locationList;

    private String userName;

    private List<String> userCodeList;

    private String beginTime;

    private String endTime;

    private List<Integer> statusList;

    public List<String> getCompanyCodeList() {
        return companyCodeList;
    }

    public void setCompanyCodeList(List<String> companyCodeList) {
        this.companyCodeList = companyCodeList;
    }

    public List<String> getUserCodeList() {
        return userCodeList;
    }

    public void setUserCodeList(List<String> userCodeList) {
        this.userCodeList = userCodeList;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public List<Integer> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Integer> locationList) {
        this.locationList = locationList;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
