package com.base.biz.authority.client.server;

import java.util.List;

import com.base.biz.authority.client.model.RoleDTO;

/**
 * @author:小M
 * @date:2020/7/30 1:01 AM
 */
public interface RoleService {

    void add(String name) throws Exception;

    List<RoleDTO> listAll() throws Exception;

    void delete(String code) throws Exception;

    void updateName(String code, String name) throws Exception;
}
