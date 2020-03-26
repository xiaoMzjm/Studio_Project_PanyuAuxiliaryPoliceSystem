package com.base.example.api.example.example_jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2019/2/24 2:34 AM
 */
@Entity
@Table(name = "user")
public class UserDO {

    @Id
    private String id;

    @Column
    private String nickName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
