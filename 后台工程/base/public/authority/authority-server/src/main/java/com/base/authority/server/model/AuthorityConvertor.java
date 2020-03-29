package com.base.authority.server.model;

import java.util.List;

import com.base.authority.client.model.AuthorityVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/3/29 7:50 PM
 */
public class AuthorityConvertor {

    public static AuthorityDTO do2dto(AuthorityDO authorityDO) {
        if (authorityDO == null) {
            return null;
        }
        AuthorityDTO authorityDTO = new AuthorityDTO();
        BeanUtils.copyProperties(authorityDO, authorityDTO);
        return authorityDTO;
    }

    public static List<AuthorityDTO> do2dtoList(List<AuthorityDO> authorityDOList) {
        List<AuthorityDTO> authorityDTOList = Lists.newArrayList();
        for (AuthorityDO authorityDO : authorityDOList) {
            authorityDTOList.add(do2dto(authorityDO));
        }
        return authorityDTOList;
    }



    public static AuthorityVO dto2vo(AuthorityDTO authorityDTO) {
        if (authorityDTO == null) {
            return null;
        }
        AuthorityVO vo = new AuthorityVO();
        BeanUtils.copyProperties(authorityDTO, vo);
        return vo;
    }

    public static List<AuthorityVO> dto2voList(List<AuthorityDTO> authorityDTOList) {
        List<AuthorityVO> voList = Lists.newArrayList();
        for (AuthorityDTO authorityDTO : authorityDTOList) {
            voList.add(dto2vo(authorityDTO));
        }
        return voList;
    }

}
