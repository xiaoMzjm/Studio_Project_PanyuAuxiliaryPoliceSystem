package com.base.biz.assessment.client.model;

/**
 * @author:小M
 * @date:2020/9/30 4:40 PM
 */
public class AssementPageListVO {

    private String code;

    private String time; // 时间，年

    private Integer status; // 状态，1为录入，2部分录入，3录入完成

    private String excellentPersonnelAwardFileCode; // 优秀人员奖励报表，下载用

    private String summaryFileCode; // 全局年度考核汇总报表，下载用

    private String reimbursementFileCode; // 报销封面报表，下载用

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExcellentPersonnelAwardFileCode() {
        return excellentPersonnelAwardFileCode;
    }

    public void setExcellentPersonnelAwardFileCode(String excellentPersonnelAwardFileCode) {
        this.excellentPersonnelAwardFileCode = excellentPersonnelAwardFileCode;
    }

    public String getSummaryFileCode() {
        return summaryFileCode;
    }

    public void setSummaryFileCode(String summaryFileCode) {
        this.summaryFileCode = summaryFileCode;
    }

    public String getReimbursementFileCode() {
        return reimbursementFileCode;
    }

    public void setReimbursementFileCode(String reimbursementFileCode) {
        this.reimbursementFileCode = reimbursementFileCode;
    }
}
