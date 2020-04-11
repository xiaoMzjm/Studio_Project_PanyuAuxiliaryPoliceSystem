package com.base.biz.user.server.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.BizUserDao;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.biz.user.server.model.BizUserConvertor;
import com.base.biz.user.server.model.BizUserDO;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.model.UpdateParam;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.MD5Util;
import com.base.department.client.service.CompanyService;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataUnit;

/**
 * @author:小M
 * @date:2020/3/30 12:25 AM
 */
@Component
public class BizUserManager {

    @Autowired
    private BizUserDao bizUserDao;

    /**
     * 根据code查询
     * @param code
     * @return
     * @throws Exception
     */
    public BizUserDTO findByCode(String code) throws Exception{

        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(code);

        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if (!optional.isPresent()) {
            throw new BaseException(String.format("该用户不存在[%s]",code));
        }

        bizUserDO = optional.get();
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * 根据账号密码查找用户
     * @param code
     * @param password
     * @return
     * @throws Exception
     */
    public BizUserDTO findByCodeAndPassword(String code, String password) throws Exception{

        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(code);
        bizUserDO.setPassword(password);

        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if (!optional.isPresent()) {
            throw new BaseException("账号或密码错误");
        }

        bizUserDO = optional.get();
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * 根据身份证查询
     * @param identityCode
     * @return
     */
    public BizUserDTO findByIdentityCard(String identityCode){
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setIdentityCard(identityCode);
        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if (!optional.isPresent()) {
            return null;
        }
        bizUserDO = optional.get();
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * genuine名称和单位code查询
     * @param name
     * @param companyCodeList
     * @return
     */
    public List<BizUserDTO> findByNameAndCompany(String name, List<String> companyCodeList) {
        List<BizUserDO> bizUserDOList = bizUserDao.findByNameAndCompanyList(name, companyCodeList);
        return BizUserConvertor.do2dtoList(bizUserDOList);
    }

    /**
     * 超级查询
     * @param param
     * @return
     */
    public List<BizUserDTO> findBySuperParam(SuperPageListParam param) {
        List<BizUserDO> bizUserDOList = bizUserDao.findByParam(
            param.companyCodeList,
            param.name,
            param.birthdateBeginDate,
            param.birthdateEndDate,
            param.nationList,
            param.politicalLandscapeList,
            param.graduateInstitutions,
            param.policeCode,
            param.quasiDrivingTypeList,
            param.exservicemanList,
            param.permanentResidenceAddress,
            param.sexList,
            param.ageBegin,
            param.ageEnd,
            param.nativePlace,
            param.educationList,
            param.major,
            param.maritalStatusList,
            param.identityCard,
            param.phone,
            param.personnelTypeList,
            param.authorizedStrengthTypeList,
            param.placeOfWorkList,
            param.treatmentGradeList,
            param.enrollWayList,
            param.beginWorkTimeBeginDate,
            param.beginWorkTimeEndDate,
            param.effectiveDateOfTheContractBeginDate,
            param.effectiveDateOfTheContractEndDate,
            param.retirementDateBeginDate,
            param.retirementDateEndDate,
            param.dimissionTypeList,
            param.workUnitCodeList,
            param.organizationUnitCodeList,
            param.jobCategoryList,
            param.duty,
            param.socialSecurityNumber,
            param.beginPoliceWorkTimeBeginDate,
            param.beginPoliceWorkTimeEndDate,
            param.contractExpirationDateBeginDate,
            param.contractExpirationDateEndDate,
            param.dimissionDateBeginDate,
            param.dimissionDateEndDate,
            param.dimissionReason);
        return BizUserConvertor.do2dtoList(bizUserDOList);
    }

    /**
     * 根据单位code数
     * @param companyCode
     * @return
     */
    public Long countByCompanyCode(String companyCode) {
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setWorkUnitCode(companyCode);
        Example<BizUserDO> example = Example.of(bizUserDO);
        Long result = bizUserDao.count(example);
        return result;
    }

    /**
     * 新增最简单必要属性的一个用户
     * @param code
     * @param password
     * @return
     * @throws Exception
     */
    public BizUserDTO addSimple(String code, String password) {
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(code);
        bizUserDO.setPassword(password);
        Date now = new Date();
        bizUserDO.setGmtCreate(now);
        bizUserDO.setGmtModified(now);
        bizUserDO.setIdentityCard(code);
        bizUserDO = bizUserDao.save(bizUserDO);
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * 添加人员
     * @param param
     * @return
     * @throws Exception
     */
    public BizUserDTO add(BizUserAddParam param) {
        BizUserDO bizUserDO = new BizUserDO();
        Date now = new Date();
        bizUserDO.setGmtCreate(now);
        bizUserDO.setGmtModified(now);
        bizUserDO.setCode(param.identityCard);
        bizUserDO.setIdentityCard(param.identityCard);
        fillWithByAddParam(param, bizUserDO);
        bizUserDO = bizUserDao.save(bizUserDO);
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * 跟新
     * @param param
     * @throws Exception
     */
    public void update(UpdateParam param) throws Exception{
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(param.code);
        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if(!optional.isPresent()) {
            throw new BaseException(String.format("该用户不存在，Code[%s]",param.code));
        }
        bizUserDO = optional.get();

        Date now = new Date();
        bizUserDO.setGmtModified(now);
        fillWithByAddParam(param, bizUserDO);

        bizUserDao.save(bizUserDO);
    }

    /**
     * 根据code删除人员
      * @param code
     */
    public void deleteByCode(String code) {
        bizUserDao.deleteByCode(code);
    }


    private void fillWithByAddParam(BizUserAddParam param,BizUserDO bizUserDO){
        bizUserDO.setAge(param.age);
        bizUserDO.setName(param.name);
        bizUserDO.setPassword(MD5Util.MD5("admin"));
        bizUserDO.setPicUrl(param.headPicCode);
        bizUserDO.setBirthdate(DateUtil.convert2Date(param.birthdate,BizUserConstant.DateFormat));
        bizUserDO.setNation(param.nation);
        bizUserDO.setPoliticalLandscape(param.politicalLandscape);
        bizUserDO.setGraduateSchool(param.graduateInstitutions);
        bizUserDO.setPoliceCode(param.policeCode);
        bizUserDO.setDrivingType(param.quasiDrivingType);
        bizUserDO.setSpeciality(param.speciality);
        bizUserDO.setExserviceman(param.exserviceman);
        bizUserDO.setPermanentResidenceAddress(param.permanentResidenceAddress);
        bizUserDO.setFamilyAddress(param.familyAddress);
        bizUserDO.setSex(param.sex);
        bizUserDO.setNativePlace(param.nativePlace);
        bizUserDO.setEducation(param.education);
        bizUserDO.setMajor(param.major);
        bizUserDO.setMaritalStatus(param.maritalStatus);
        bizUserDO.setPhone(param.phone);
        bizUserDO.setPersonnelType(param.personnelType);
        bizUserDO.setAuthorizedStrengthType(param.authorizedStrengthType);
        bizUserDO.setPlaceOfWork(param.placeOfWork);
        bizUserDO.setJobGrade(param.jobGrade);
        bizUserDO.setTreatmentGrade(param.treatmentGrade);
        bizUserDO.setEnrollWay(param.enrollWay);
        bizUserDO.setBeginWorkTime(DateUtil.convert2Date(param.beginWorkTime,BizUserConstant.DateFormat));
        bizUserDO.setEffectiveDateOfTheContrace(DateUtil.convert2Date(param.effectiveDateOfTheContract,BizUserConstant.DateFormat));
        bizUserDO.setRetirementDate(DateUtil.convert2Date(param.retirementDate,BizUserConstant.DateFormat));
        bizUserDO.setDimssionType(param.dimissionType);
        bizUserDO.setWorkUnitCode(param.workUnitCode);
        bizUserDO.setOrganizationUnitCode(param.organizationUnitCode);
        bizUserDO.setJobCategory(param.jobCategory);
        bizUserDO.setDuty(param.duty);
        bizUserDO.setSocialSecurityNumber(param.socialSecurityNumber);
        bizUserDO.setBeginWorkTime(DateUtil.convert2Date(param.beginWorkTime,BizUserConstant.DateFormat));
        bizUserDO.setContractExpirationDate(DateUtil.convert2Date(param.contractExpirationDate,BizUserConstant.DateFormat));
        bizUserDO.setDimssionDate(DateUtil.convert2Date(param.dimissionDate,BizUserConstant.DateFormat));
        bizUserDO.setDimssionReason(param.dimissionReason);
        bizUserDO.setBeginPoliceWorkTime(DateUtil.convert2Date(param.beginPoliceWorkTime,BizUserConstant.DateFormat));
    }
















}
