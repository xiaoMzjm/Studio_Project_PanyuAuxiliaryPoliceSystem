package com.base.biz.expire.client.model;

import java.util.Date;

/**
 * @author:小M
 * @date:2020/5/25 12:51 AM
 */
public class ExpireVO {

    private String name = "";
    private String code = "";
    private String message = "";

    // 防疫新增
    private Date time;
    private String fileUrl;
    private String remark;

    public ExpireVO() {
    }

    public ExpireVO(String message) {
        this.message = message;
    }

    public ExpireVO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
