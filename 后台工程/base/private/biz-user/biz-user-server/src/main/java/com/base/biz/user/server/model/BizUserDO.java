package com.base.biz.user.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2020/3/30 12:10 AM
 */
@Entity
@Table(name = "biz_user",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true),
        @Index(name = "idx_ide_pa",  columnList="identityCard,password", unique = true)})
public class BizUserDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64 , nullable = false)
    private String code;

    @Column(length = 64 , nullable = false)
    private String identityCard;

    @Column(length = 64 , nullable = false)
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
