package com.base.biz.assessment.client.enums;

/**
 * @author:小M
 * @date:2020/10/1 1:43 AM
 */
public enum AssessmentStatusEnum {

    NEVER(1,"未录入"),
    PART(2,"部分录入"),
    COMPLETE(3,"录入完成"),
    ;

    private Integer code;
    private String name;

    AssessmentStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
