package com.base.biz.wages.client.enums;

/**
 * @author:小M
 * @date:2020/9/29 2:41 PM
 */
public enum WagesChangeTypeEnum {

    CHANGE(1 , "工资变动"),
    SYSTEM_CHANGE(2 , "系统工资变动"),
    COLLECT_CHANGE(3, "正确工资变动")

        ;
    private Integer type;
    private String desc;

    WagesChangeTypeEnum(Integer type, String desc) {
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
