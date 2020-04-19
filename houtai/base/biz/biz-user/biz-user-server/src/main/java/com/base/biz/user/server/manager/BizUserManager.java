package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/30 12:25 AM
 */
@Component
public class BizUserManager {

    @Autowired
    private BizUserDao bizUserDao;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

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

    private String strList(List<String> list){
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(String s : list) {
            sb.append("'" + s + "'").append(",");
        }
        String str = sb.toString();
        if(str.endsWith(",")) {
            str = str.substring(0,str.length()-1);
        }
        return "(" + str + ")";
    }

    private String intList(List<Integer> list){
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(Integer i : list) {
            sb.append(i).append(",");
        }
        String str = sb.toString();
        if(str.endsWith(",")) {
            str = str.substring(0,str.length()-1);
        }
        return "(" + str + ")";
    }

    /**
     * 超级查询
     * @param param
     * @return
     */
    public List<BizUserDTO> findBySuperParam(SuperPageListParam param) {

        String sql = "select * from biz_user where 1=1 ";
        if(CollectionUtils.isNotEmpty(param.companyCodeList)) {
            sql += " and work_unit_code in " + strList(param.companyCodeList);
        }
        if(StringUtils.isNotEmpty(param.name)) {
            sql += " and name like '%%" + param.name + "%%' ";
        }
        if(StringUtils.isNotEmpty(param.birthdateBegin)) {
            sql += " and birthdate >= '" + param.birthdateBegin + "'";
        }
        if(StringUtils.isNotEmpty(param.birthdateEnd)) {
            sql += " and birthdate <= '" + param.birthdateEnd + "'";
        }
        if(CollectionUtils.isNotEmpty(param.nationList)) {
            sql += " and nation in " + intList(param.nationList);
        }
        if(CollectionUtils.isNotEmpty(param.politicalLandscapeList)) {
            sql += " and political_landscape in " + intList(param.politicalLandscapeList);
        }
        if(StringUtils.isNotEmpty(param.policeCode)) {
            sql += " and police_code like '%" + param.policeCode + "%' ";
        }
        if(CollectionUtils.isNotEmpty(param.quasiDrivingTypeList)) {
            sql += " and driving_type in " + intList(param.quasiDrivingTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.exservicemanList)) {
            sql += " and exserviceman in " + intList(param.exservicemanList);
        }
        if(StringUtils.isNotEmpty(param.specialPeople)) {
            sql += " and (";
            String[] specialPeopleArray = param.specialPeople.split(",");
            for(String specialPeople : specialPeopleArray) {
                sql += " special_people like '%" + Integer.valueOf(specialPeople) + "%' or";
            }
            if(sql.endsWith("or")) {
                sql = sql.substring(0, sql.length() - 2) ;
            }
            sql += ") ";
        }
        if(StringUtils.isNotEmpty(param.qualification)) {
            sql += " and qualification like '%%"+param.qualification+"%%'";
        }
        if(StringUtils.isNotEmpty(param.permanentResidenceAddress)) {
            sql += " and permanent_residence_address like '%%"+param.permanentResidenceAddress+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.sexList)) {
            sql += " and sex in " + intList(param.sexList);
        }
        if(param.ageBegin != null) {
            sql += " and age >= " + param.ageBegin;
        }
        if(param.ageEnd != null) {
            sql += " and age <= " + param.ageEnd;
        }
        if(StringUtils.isNotEmpty(param.nativePlace)) {
            sql += " and native_place like '%%"+ param.nativePlace +"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.educationList)) {
            sql += " and education in " + intList(param.educationList);
        }
        if(StringUtils.isNotEmpty(param.major)) {
            sql += " and major like '%%"+param.major+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.maritalStatusList)) {
            sql += " and marital_status in " + intList(param.maritalStatusList);
        }
        if(StringUtils.isNotEmpty(param.identityCard)) {
            sql += " and identity_card like '%%"+param.identityCard+"%%'";
        }
        if(StringUtils.isNotEmpty(param.phone)) {
            sql += " and phone like '%%"+param.phone+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.personnelTypeList)) {
            sql += " and personnel_type in " + intList(param.personnelTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.authorizedStrengthTypeList)) {
            sql += " and authorized_strength_type in " + intList(param.authorizedStrengthTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.placeOfWorkList)) {
            sql += " and place_of_work in " + intList(param.placeOfWorkList);
        }
        if (CollectionUtils.isNotEmpty(param.treatmentGradeList)) {
            sql += " and treatment_grade in " + intList(param.treatmentGradeList);
        }
        if(CollectionUtils.isNotEmpty(param.enrollWayList)) {
            sql += " and enroll_way in " + intList(param.enrollWayList);
        }
        if(StringUtils.isNotEmpty(param.beginWorkTimeBegin)) {
            sql += " and begin_work_time >= '"+param.beginWorkTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.beginWorkTimeEnd)) {
            sql += " and begin_work_time <= '"+param.beginWorkTimeEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.effectiveDateOfTheContractBegin)) {
            sql += " and effective_date_of_the_contrace >= '"+param.effectiveDateOfTheContractBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.effectiveDateOfTheContractEnd)) {
            sql += " and effective_date_of_the_contrace <= '"+param.effectiveDateOfTheContractEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.retirementDateBegin)) {
            sql += " and retirement_date >= '"+param.retirementDateBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.retirementDateEnd)) {
            sql += " and retirement_date <= '"+param.retirementDateEnd+"'";
        }
        if(CollectionUtils.isNotEmpty(param.dimissionTypeList)) {
            sql += " and dimssion_type in " + intList(param.dimissionTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.workUnitCodeList)) {
            sql += " and work_unit_code in " + strList(param.workUnitCodeList);
        }
        if(CollectionUtils.isNotEmpty(param.organizationUnitCodeList)) {
            sql += " and organization_unit_code in " + strList(param.organizationUnitCodeList);
        }
        if(CollectionUtils.isNotEmpty(param.jobCategoryList)) {
            sql += " and job_category in " + intList(param.jobCategoryList);
        }
        if(StringUtils.isNotEmpty(param.duty)) {
            sql += " and duty like '%%"+param.duty+"%%'";
        }
        if(StringUtils.isNotEmpty(param.socialSecurityNumber)) {
            sql += " and social_security_number like '%%"+param.socialSecurityNumber+"%%'";
        }
        if(StringUtils.isNotEmpty(param.retirementDateBegin)) {
            sql += " and begin_police_work_time >= '"+param.retirementDateBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.beginPoliceWorkTimeEnd)) {
            sql += " and begin_police_work_time <= '"+param.beginPoliceWorkTimeEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.contractExpirationDateBegin)) {
            sql += " and contract_expiration_date >= '"+param.contractExpirationDateBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.contractExpirationDateEnd)) {
            sql += " and contract_expiration_date <= '"+param.contractExpirationDateEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.dimissionDateBegin)) {
            sql += " and dimssion_date >= '"+param.dimissionDateBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.dimissionDateEnd)) {
            sql += " and dimssion_date <= '"+param.dimissionDateEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.dimissionReason)) {
            sql += " and dimssion_reason like '%%"+param.dimissionReason+"%%'";
        }

        System.out.println("==========");
        System.out.println(sql);
        System.out.println("==========");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(sql, BizUserDO.class);
        List<BizUserDO> bizUserDOList = query.getResultList();
        entityManager.close();

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
        bizUserDO.setSpecialPeople(param.specialPeople);
        bizUserDO.setQualification(param.qualification);
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
