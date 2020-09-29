package com.base.biz.wages.client.enums;

/**
 * @author:小M
 * @date:2020/9/20 12:01 PM
 */
public enum WagesReimbursementImportTypeEnum {

    REIMBURSEMENT(1 , "报销封面"),
    SYSTEM_REIMBURSEMENT(2 , "报销封面"),
    COLLECT_REIMBURSEMENT(3, "正确的报销封面")

    ;
    private Integer type;
    private String desc;

    WagesReimbursementImportTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
