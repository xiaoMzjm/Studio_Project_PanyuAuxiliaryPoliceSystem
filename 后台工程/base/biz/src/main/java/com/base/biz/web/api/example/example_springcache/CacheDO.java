package com.base.biz.web.api.example.example_springcache;

import java.io.Serializable;

/**
 * @author:小M
 * @date:2018/11/4 下午11:59
 */
public class CacheDO implements Serializable {

    private Long id;

    private String name;

    @Override
    public String toString() {
        return "CacheDO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
