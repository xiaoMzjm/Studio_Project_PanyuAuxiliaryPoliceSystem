package com.base.biz.assessment.client.enums;

/**
 * @author:小M
 * @date:2020/10/1 12:10 AM
 */
public enum AssessmentDownloadTypeEnum {

    ExcellentPersonnelAwardFile(1, "优秀人员奖励报表"),
    SummaryFile(2,"全局年度考核汇总报表"),
    Reimbursement(3,"报销封面报表"),
    ;
    private Integer type;
    private String name;

    AssessmentDownloadTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
