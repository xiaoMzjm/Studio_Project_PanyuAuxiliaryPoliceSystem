package com.base.biz.wages.client.model;

/**
 * 工资列表页一行的模型
 * @author:小M
 * @date:2020/9/19 7:58 PM
 */
public class WageListVO {

    private String importReportCode;

    private String importReportName;

    private String systemReportCode;

    private String systemReportName;

    private String correctReportCode;

    private String correctReportName;

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

    public String getImportReportCode() {
        return importReportCode;
    }

    public void setImportReportCode(String importReportCode) {
        this.importReportCode = importReportCode;
    }

    public String getSystemReportCode() {
        return systemReportCode;
    }

    public void setSystemReportCode(String systemReportCode) {
        this.systemReportCode = systemReportCode;
    }

    public String getCorrectReportCode() {
        return correctReportCode;
    }

    public void setCorrectReportCode(String correctReportCode) {
        this.correctReportCode = correctReportCode;
    }
}
