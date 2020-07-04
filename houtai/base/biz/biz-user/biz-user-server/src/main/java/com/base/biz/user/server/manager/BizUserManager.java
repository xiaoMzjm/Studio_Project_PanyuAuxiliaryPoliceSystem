package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.model.BizUserAddParam;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.model.UpdateParam;

/**
 * @author:小M
 * @date:2020/7/5 12:02 AM
 */
public interface BizUserManager{

    BizUserDTO findByCode(String code) throws Exception;

    List<BizUserDTO> findByCodes(List<String> codes) throws Exception;

    BizUserDTO findByCodeAndPassword(String code, String password) throws Exception;

    BizUserDTO findByPoliceCodeAndPassword(String code, String password) throws Exception;

    BizUserDTO findByIdentityCard(String identityCode);

    List<BizUserDTO> findByIdentityCardList(List<String> identityCodeList);

    BizUserDTO findByPoliceCode(String policeCode);

    List<BizUserDTO> findByNameAndCompany(String name, List<String> companyCodeList);

    List<BizUserDTO> findBySuperParam(SuperPageListParam param);

    Long countByCompanyCode(String companyCode);

    BizUserDTO addSimple(String code, String password, String name);

    BizUserDTO add(BizUserAddParam param);

    void update(UpdateParam param) throws Exception;

    void updateImage(String code , String imageCode) throws Exception;

    void deleteByCode(String code);

    List<BizUserDTO> getByWorkCardBeginTime(Date start, Date end);

    List<BizUserDTO> getByContractEngTime(Date start, Date end);

    List<BizUserDTO> getByBirthDayAndSex(Date start, Date end, Integer sex);


}
