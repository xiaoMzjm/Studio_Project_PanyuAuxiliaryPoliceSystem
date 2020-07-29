package com.base.biz.authority.server.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.biz.authority.client.model.RoleDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author:小M
 * @date:2020/7/30 12:49 AM
 */
@Entity
@Table(name = "role")
public class RoleDO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date gmtCreate;
    @Column
    private Date gmtModified;
    @Column(length = 64,nullable = false,unique = true)
    private String code;
    @Column(length = 64,nullable = false,unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

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

    public static RoleDTO toDTO(RoleDO roleDO){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setCode(roleDO.getCode());
        roleDTO.setName(roleDO.getName());
        return roleDTO;
    }

    public static List<RoleDTO> toDTOList(List<RoleDO> roleDOList){
        if(CollectionUtils.isEmpty(roleDOList)) {
            return null;
        }
        List<RoleDTO> result = Lists.newArrayList();
        for(RoleDO roleDO : roleDOList) {
            result.add(toDTO(roleDO));
        }
        return result;
    }
}
