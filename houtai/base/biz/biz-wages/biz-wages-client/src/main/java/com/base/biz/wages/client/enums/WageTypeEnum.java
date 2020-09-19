package com.base.biz.wages.client.enums;

/**
 * @author:小M
 * @date:2020/9/19 10:27 PM
 */
public enum WageTypeEnum {

    THREE(1 , "三级明细"),
    FOUR(2, "四级明细")

    ;
    private Integer type;
    private String desc;

    WageTypeEnum(Integer type, String desc) {
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
