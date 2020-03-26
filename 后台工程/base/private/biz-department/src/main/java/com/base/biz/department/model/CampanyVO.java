package com.base.biz.department.model;

import java.util.Date;
import java.util.List;

/**
 * @author:小M
 * @date:2020/3/27 2:01 AM
 */
public class CampanyVO {

    // 单位code
    private String code;

    // 单位名称
    private String name;

    // 单位描述
    private String desc;

    // 子单位
    private List<CampanyVO> sub;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<CampanyVO> getSub() {
        return sub;
    }

    public void setSub(List<CampanyVO> sub) {
        this.sub = sub;
    }
}
