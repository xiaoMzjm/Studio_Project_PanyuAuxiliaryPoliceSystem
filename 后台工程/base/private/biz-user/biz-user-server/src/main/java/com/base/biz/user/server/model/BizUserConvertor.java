package com.base.biz.user.server.model;

import java.util.List;

import com.base.biz.user.client.model.BizUserVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2020/3/30 12:23 AM
 */
public class BizUserConvertor {

    public static BizUserDTO do2dto(BizUserDO bizUserDO){
        if (bizUserDO == null) {
            return null;
        }
        BizUserDTO bizUserDTO = new BizUserDTO();
        BeanUtils.copyProperties(bizUserDO, bizUserDTO);
        return bizUserDTO;
    }

    public static List<BizUserDTO> do2dtoList(List<BizUserDO> bizUserDOList){
        List<BizUserDTO> list = Lists.newArrayList();
        for (BizUserDO bizUserDO : bizUserDOList) {
            list.add(do2dto(bizUserDO));
        }
        return list;
    }

    public static BizUserVO dto2vo(BizUserDTO bizUserDTO) {
        if (bizUserDTO == null) {
            return null;
        }
        BizUserVO bizUserVO = new BizUserVO();
        BeanUtils.copyProperties(bizUserDTO, bizUserVO);
        return bizUserVO;
    }
}
