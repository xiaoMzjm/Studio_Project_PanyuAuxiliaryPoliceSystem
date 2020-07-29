package com.base.biz.authority.server.manager;

import java.util.List;

import com.base.biz.authority.server.model.RoleDO;

/**
 * @author:小M
 * @date:2020/7/30 1:05 AM
 */
public interface RoleManager {

    void add(String name) throws Exception;

    RoleDO findByName(String name) throws Exception;

    List<RoleDO> listAll() throws Exception;

    void delete(String code) throws Exception;

    RoleDO findByCode(String code) throws Exception;

    void updateName(String code, String name) throws Exception;


}
