package com.base.department.client.model;

import java.util.List;

/**
 * @author:小M
 * @date:2020/3/27 2:01 AM
 */
public class CompanyVO {

    // 单位code
    private String code;

    // 单位名称
    private String name;

    // 单位描述
    private String description;

    // 父code
    private String fatherCode;

    // 子单位
    private List<CompanyVO> sub;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CompanyVO> getSub() {
        return sub;
    }

    public void setSub(List<CompanyVO> sub) {
        this.sub = sub;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }
}
