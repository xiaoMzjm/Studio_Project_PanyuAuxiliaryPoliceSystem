package com.base.biz.wages.client.model;

/**
 * 工资列表页一行的模型
 * @author:小M
 * @date:2020/9/19 7:58 PM
 */
public class WageListVO {

    private String importReportName;

    private String systemReportName;

    private String correctReportName;

    private String code;

    public String getImportReportName() {
        return importReportName;
    }

    public void setImportReportName(String importReportName) {
        this.importReportName = importReportName;
    }

    public String getSystemReportName() {
        return systemReportName;
    }

    public void setSystemReportName(String systemReportName) {
        this.systemReportName = systemReportName;
    }

    public String getCorrectReportName() {
        return correctReportName;
    }

    public void setCorrectReportName(String correctReportName) {
        this.correctReportName = correctReportName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
