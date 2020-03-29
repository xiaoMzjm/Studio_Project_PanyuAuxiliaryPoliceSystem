package com.base.biz.user.server.model;

import javax.persistence.Column;

/**
 * @author:小M
 * @date:2020/3/30 12:22 AM
 */
public class BizUserDTO {

    private Long id;

    private String code;

    private String identityCard;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
