package com.base.biz.authority.server.service;

import java.util.List;

import com.base.biz.authority.client.model.RoleDTO;
import com.base.biz.authority.client.server.RoleService;
import com.base.biz.authority.server.manager.RoleManager;
import com.base.biz.authority.server.model.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:小M
 * @date:2020/7/30 1:03 AM
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;

    @Override
    public void add(String name) throws Exception {

        roleManager.add(name);
    }

    @Override
    public List<RoleDTO> listAll() throws Exception {
        List<RoleDO> roleDOList =  roleManager.listAll();
        return RoleDO.toDTOList(roleDOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String code) throws Exception {
        // 删除角色
        roleManager.delete(code);
        // 删除角色和权限的绑定关系 TODO

        // 删除角色和用户的绑定关系 TODO
    }

    @Override
    public void updateName(String code, String name) throws Exception {
        roleManager.updateName(code, name);
    }
}
