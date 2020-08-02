package com.base.biz.user.server.model;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.Enums.SexEnum;
import com.base.biz.user.client.model.BizUserLoginVO;
import com.base.common.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.analysis.function.Add;
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
        // 年龄 根据生日算出
        Date birthday = bizUserDO.getBirthdate();
        if(birthday != null) {
            Date now = new Date();
            int nowYear = DateUtil.getYear(now);
            int nowMonth = DateUtil.getMonth(now);
            int nowDay = DateUtil.getDay(now);

            int birthdayYear = DateUtil.getYear(birthday);
            int birthdayMonth = DateUtil.getMonth(birthday);
            int birthdayDay = DateUtil.getDay(birthday);

            int age = nowYear - birthdayYear;
            if(nowMonth <= birthdayMonth) {
                if(nowMonth == birthdayMonth) {
                    if(nowDay == birthdayDay) {
                        age --;
                    }
                }else {
                    age --;
                }
            }
            bizUserDTO.setAge(age);
        }

        // 退休时间 男=出生日期+60，女=出生日期+50
        if(birthday != null && bizUserDO.getSex() != null) {
            if(bizUserDO.getSex() == SexEnum.MAN.getCode()) {
                Date expireDate = DateUtil.addYears(birthday,60);
                bizUserDTO.setRetirementDate(expireDate);
            }else {
                Date expireDate = DateUtil.addYears(birthday,50);
                bizUserDTO.setRetirementDate(expireDate);
            }
        }
        return bizUserDTO;
    }

    public static List<BizUserDTO> do2dtoList(List<BizUserDO> bizUserDOList){
        List<BizUserDTO> list = Lists.newArrayList();
        for (BizUserDO bizUserDO : bizUserDOList) {
            list.add(do2dto(bizUserDO));
        }
        return list;
    }

    public static BizUserLoginVO dto2vo(BizUserDTO bizUserDTO) {
        if (bizUserDTO == null) {
            return null;
        }
        BizUserLoginVO bizUserVO = new BizUserLoginVO();
        BeanUtils.copyProperties(bizUserDTO, bizUserVO);
        return bizUserVO;
    }

    public static List<BizUserLoginVO> dto2voList(List<BizUserDTO> bizUserDTOList) {
        if (CollectionUtils.isEmpty(bizUserDTOList)) {
            return null;
        }
        List<BizUserLoginVO> result = Lists.newArrayList();
        for(BizUserDTO bizUserDTO : bizUserDTOList) {
            result.add(dto2vo(bizUserDTO));
        }

        return result;
    }

}
