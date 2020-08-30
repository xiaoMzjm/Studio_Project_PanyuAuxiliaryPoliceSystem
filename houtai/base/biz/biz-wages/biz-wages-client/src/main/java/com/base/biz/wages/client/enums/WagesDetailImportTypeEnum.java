package com.base.biz.wages.client.enums;

/**
 * @author:小M
 * @date:2020/8/30 5:38 PM
 */
public enum WagesDetailImportTypeEnum {

    THREE(1, "三级租赁岗（实名制）辅警工资表"),
    FOUR(2, "四级租赁岗（实名制）辅警工资表"),
    SYSTEM_THREE(3, "三级租赁岗（实名制）辅警工资表（系统生成）"),
    SYSTEM_FOUR(4, "四级租赁岗（实名制）辅警工资表（系统生成）"),
    CORRECT_THREE(5, "三级租赁岗（实名制）辅警工资表（正确记录）"),
    CORRECT_FOUR(6, "四级租赁岗（实名制）辅警工资表（正确记录）"),
    ;

    private Integer value;
    private String name;

    public static String getName(Integer value) {
        for(WagesDetailImportTypeEnum e : WagesDetailImportTypeEnum.values()) {
            if(e.getValue().equals(value)) {
                return e.getName();
            }
        }
        return null;
    }

    WagesDetailImportTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
