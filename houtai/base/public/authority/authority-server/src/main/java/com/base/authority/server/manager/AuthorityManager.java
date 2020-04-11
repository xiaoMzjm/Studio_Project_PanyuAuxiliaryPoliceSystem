package com.base.authority.server.manager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.base.authority.client.common.Enums.AuthorityTypeEnum;
import com.base.authority.server.dao.AuthorityRepository;
import com.base.authority.server.model.AuthorityConvertor;
import com.base.authority.server.model.AuthorityDO;
import com.base.authority.server.model.AuthorityDTO;
import com.base.common.exception.BaseException;
import com.base.common.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.SetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/29 8:28 PM
 */
@Component
public class AuthorityManager {

    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * 新增权限
     * @param name
     * @param authorityTypeEnum
     * @param fatherCode
     */
    public AuthorityDTO add(String name, String code, AuthorityTypeEnum authorityTypeEnum, String fatherCode) throws Exception{

        AuthorityDTO exist = getByName(name);
        if (exist != null) {
            throw new BaseException("该名称已经存在，请重新命名");
        }

        if (StringUtils.isNotEmpty(fatherCode)) {
            exist = getByCode(fatherCode);
            if (exist == null) {
                throw new BaseException("该父级菜单不存在");
            }
        }

        AuthorityDO authorityDO = new AuthorityDO();
        Date now = new Date();
        authorityDO.setGmtCreate(now);
        authorityDO.setGmtModified(now);
        authorityDO.setCode(code);
        authorityDO.setName(name);
        authorityDO.setType(authorityTypeEnum.getType());
        authorityDO.setFatherCode(fatherCode);
        authorityDO = authorityRepository.save(authorityDO);
        if (authorityDO.getId() == null) {
            throw new RuntimeException("新增权限失败");
        }
        return AuthorityConvertor.do2dto(authorityDO);
    }

    /**
     * 根据名称搜索
     * @param name
     * @return
     */
    public AuthorityDTO getByName(String name){
        AuthorityDO authorityDO = new AuthorityDO();
        authorityDO.setName(name);
        Example<AuthorityDO> example = Example.of(authorityDO);
        Optional<AuthorityDO> option = authorityRepository.findOne(example);
        if (option.isPresent()) {
            return AuthorityConvertor.do2dto(option.get());
        }
        return null;
    }

    /**
     * 根据code搜索
     * @param code
     * @return
     */
    public AuthorityDTO getByCode(String code){
        AuthorityDO authorityDO = new AuthorityDO();
        authorityDO.setCode(code);
        Example<AuthorityDO> example = Example.of(authorityDO);
        Optional<AuthorityDO> option = authorityRepository.findOne(example);
        if (option.isPresent()) {
            return AuthorityConvertor.do2dto(option.get());
        }
        return null;
    }

    /**
     * 搜索所有
     * @return
     */
    public List<AuthorityDTO> findAll(){
        List<AuthorityDO> authorityDOList = authorityRepository.findAll();
        return AuthorityConvertor.do2dtoList(authorityDOList);
    }




    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }
}
