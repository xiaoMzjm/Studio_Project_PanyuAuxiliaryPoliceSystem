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
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
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

        if(StringUtils.isBlank(code)) {
            return null;
        }

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
            return null;
        }

        bizUserDO = optional.get();
        return BizUserConvertor.do2dto(bizUserDO);
    }

    /**
     * 根据警号密码查找用户
     * @param code
     * @param password
     * @return
     * @throws Exception
     */
    public BizUserDTO findByPoliceCodeAndPassword(String code, String password) throws Exception{

        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setPoliceCode(code);
        bizUserDO.setPassword(password);

        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if (!optional.isPresent()) {
            return null;
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
     * 根据身份证查询
     * @param identityCodeList
     * @return
     */
    public List<BizUserDTO> findByIdentityCardList(List<String> identityCodeList){
        BizUserDO bizUserDO = new BizUserDO();
        String sql = "select * from biz_user where identity_card in " + inStrList(identityCodeList);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(sql, BizUserDO.class);
        List<BizUserDO> bizUserDOList = query.getResultList();
        entityManager.close();

        return BizUserConvertor.do2dtoList(bizUserDOList);
    }

    /**
     * 根据警号查询
     * @param policeCode
     * @return
     */
    public BizUserDTO findByPoliceCode(String policeCode){
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setPoliceCode(policeCode);
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

        String sql = "select * from biz_user where 1=1 ";
        if(CollectionUtils.isNotEmpty(param.companyCodeList)) {
            sql += " and work_unit_code in " + inStrList(param.companyCodeList);
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
            sql += " and nation in " + inIntList(param.nationList);
        }
        if(CollectionUtils.isNotEmpty(param.politicalLandscapeList)) {
            sql += " and political_landscape in " + inIntList(param.politicalLandscapeList);
        }
        if(StringUtils.isNotEmpty(param.graduateInstitutions)) {
            sql += " and graduate_school like '%%" + param.graduateInstitutions + "%%' ";
        }
        if(StringUtils.isNotEmpty(param.policeCode)) {
            sql += " and police_code like '%" + param.policeCode + "%' ";
        }
        if(CollectionUtils.isNotEmpty(param.quasiDrivingTypeList)) {
            sql += " and (";
            for(Integer quasiDrivingType : param.quasiDrivingTypeList) {
                sql += " driving_type like '%" + quasiDrivingType + "%' or";
            }
            if(sql.endsWith("or")) {
                sql = sql.substring(0, sql.length() - 2) ;
            }
            sql += ") ";
        }
        if(CollectionUtils.isNotEmpty(param.specialPeopleList)) {
            sql += " and (";
            for(Integer specialPeople : param.specialPeopleList) {
                sql += " special_people like '%" + specialPeople + "%' or";
            }
            if(sql.endsWith("or")) {
                sql = sql.substring(0, sql.length() - 2) ;
            }
            sql += ") ";
        }
        if(StringUtils.isNotEmpty(param.qualification)) {
            sql += " and qualification like '%%"+param.qualification+"%%'";
        }
        if(StringUtils.isNotEmpty(param.familyAddress)) {
            sql += " and family_address like '%%"+param.familyAddress+"%%'";
        }
        if(StringUtils.isNotEmpty(param.permanentResidenceAddress)) {
            sql += " and permanent_residence_address like '%%"+param.permanentResidenceAddress+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.sexList)) {
            sql += " and sex in " + inIntList(param.sexList);
        }
        if(param.ageBegin != null) {
            Date now = new Date();
            Date date = DateUtil.addYears(now, - param.ageBegin);
            String dateStr = DateUtil.convert2String(date, BizUserConstant.DateFormat);
            sql += " and birthdate <= '" + dateStr +"'";
        }
        if(param.ageEnd != null) {
            Date now = new Date();
            Date date = DateUtil.addYears(now, - param.ageEnd);
            String dateStr = DateUtil.convert2String(date, BizUserConstant.DateFormat);
            sql += " and birthdate >= '" + dateStr +"'";
        }
        if(StringUtils.isNotEmpty(param.nativePlace)) {
            sql += " and native_place like '%%"+ param.nativePlace +"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.educationList)) {
            sql += " and education in " + inIntList(param.educationList);
        }
        if(StringUtils.isNotEmpty(param.major)) {
            sql += " and major like '%%"+param.major+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.maritalStatusList)) {
            sql += " and marital_status in " + inIntList(param.maritalStatusList);
        }
        if(StringUtils.isNotEmpty(param.identityCard)) {
            sql += " and identity_card like '%%"+param.identityCard+"%%'";
        }
        if(StringUtils.isNotEmpty(param.phone)) {
            sql += " and phone like '%%"+param.phone+"%%'";
        }
        if(CollectionUtils.isNotEmpty(param.personnelTypeList)) {
            sql += " and personnel_type in " + inIntList(param.personnelTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.authorizedStrengthTypeList)) {
            sql += " and authorized_strength_type in " + inIntList(param.authorizedStrengthTypeList);
        }
        if(CollectionUtils.isNotEmpty(param.placeOfWorkList)) {
            sql += " and place_of_work in " + inIntList(param.placeOfWorkList);
        }
        if (CollectionUtils.isNotEmpty(param.treatmentGradeList)) {
            sql += " and treatment_grade in " + inIntList(param.treatmentGradeList);
        }
        if(CollectionUtils.isNotEmpty(param.enrollWayList)) {
            sql += " and enroll_way in " + inIntList(param.enrollWayList);
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
        if(CollectionUtils.isNotEmpty(param.workUnitList)) {
            sql += " and work_unit_code in " + inStrList(param.workUnitList);
        }
        if(CollectionUtils.isNotEmpty(param.organizationUnitList)) {
            sql += " and organization_unit_code in " + inStrList(param.organizationUnitList);
        }
        if(CollectionUtils.isNotEmpty(param.jobCategoryList)) {
            sql += " and job_category in " + inIntList(param.jobCategoryList);
        }
        if(CollectionUtils.isNotEmpty(param.jobGradeList)) {
            sql += " and job_grade in " + inIntList(param.jobGradeList);
        }
        if(StringUtils.isNotEmpty(param.duty)) {
            sql += " and duty like '%%"+param.duty+"%%'";
        }
        if(StringUtils.isNotEmpty(param.socialSecurityNumber)) {
            sql += " and social_security_number like '%%"+param.socialSecurityNumber+"%%'";
        }
        if(StringUtils.isNotEmpty(param.beginPoliceWorkTimeBegin)) {
            sql += " and begin_police_work_time >= '"+param.beginPoliceWorkTimeBegin+"'";
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

        if(StringUtils.isNotEmpty(param.firstGradeTimeBegin)) {
            sql += " and first_grade_time >= '"+param.firstGradeTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.firstGradeTimeEnd)) {
            sql += " and first_grade_time <= '"+param.firstGradeTimeEnd+"'";
        }

        if(StringUtils.isNotEmpty(param.workCardBeginTimeBegin)) {
            sql += " and work_card_begin_time >= '"+param.workCardBeginTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.workCardBeginTimeEnd)) {
            sql += " and work_card_begin_time <= '"+param.workCardBeginTimeEnd+"'";
        }

        if(StringUtils.isNotEmpty(param.firstContractBeginTimeBegin)) {
            sql += " and first_contract_begin_time >= '"+param.firstContractBeginTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.firstContractBeginTimeEnd)) {
            sql += " and first_contract_begin_time <= '"+param.firstContractBeginTimeEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.firstContractEngTimeBegin)) {
            sql += " and first_contract_eng_time >= '"+param.firstContractEngTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.firstContractEngTimeEnd)) {
            sql += " and first_contract_eng_time <= '"+param.firstContractEngTimeEnd+"'";
        }

        if(StringUtils.isNotEmpty(param.secondContractBeginTimeBegin)) {
            sql += " and second_contract_begin_time >= '"+param.secondContractBeginTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.secondContractBeginTimeEnd)) {
            sql += " and second_contract_begin_time <= '"+param.secondContractBeginTimeEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.secondContractEngTimeBegin)) {
            sql += " and second_contract_eng_time >= '"+param.secondContractEngTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.secondContractEngTimeEnd)) {
            sql += " and second_contract_eng_time <= '"+param.secondContractEngTimeEnd+"'";
        }

        if(StringUtils.isNotEmpty(param.thirdContractBeginTimeBegin)) {
            sql += " and third_contract_begin_time >= '"+param.thirdContractBeginTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.thirdContractBeginTimeEnd)) {
            sql += " and third_contract_begin_time <= '"+param.thirdContractBeginTimeEnd+"'";
        }
        if(StringUtils.isNotEmpty(param.thirdContractEngTimeBegin)) {
            sql += " and third_contract_eng_time >= '"+param.thirdContractEngTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.thirdContractEngTimeEnd)) {
            sql += " and third_contract_eng_time <= '"+param.thirdContractEngTimeEnd+"'";
        }

        if(CollectionUtils.isNotEmpty(param.dueContractList)) {
            sql += " and due_contract in " + inIntList(param.dueContractList);
        }
        if(StringUtils.isNotEmpty(param.icbcCardAccount)) {
            sql += " and icbc_card_account like '%%"+param.icbcCardAccount+"%%'";
        }

        if(StringUtils.isNotEmpty(param.ruZhiZuLinTimeBegin)) {
            sql += " and ru_zhi_zu_lin_time >= '"+param.ruZhiZuLinTimeBegin+"'";
        }
        if(StringUtils.isNotEmpty(param.ruZhiZuLinTimeEnd)) {
            sql += " and ru_zhi_zu_lin_time <= '"+param.ruZhiZuLinTimeEnd+"'";
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
    public BizUserDTO addSimple(String code, String password, String name) {
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(code);
        bizUserDO.setPassword(password);
        Date now = new Date();
        bizUserDO.setGmtCreate(now);
        bizUserDO.setGmtModified(now);
        bizUserDO.setIdentityCard(code);
        bizUserDO.setName(name);
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
     * 更新头像
     * @param code
     * @param imageCode
     * @throws Exception
     */
    public void updateImage(String code , String imageCode) throws Exception{
        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setCode(code);
        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if(!optional.isPresent()) {
            throw new BaseException(String.format("该用户不存在，Code[%s]",code));
        }
        bizUserDO = optional.get();

        Date now = new Date();
        bizUserDO.setGmtModified(now);
        bizUserDO.setPicUrl(imageCode);
        bizUserDao.save(bizUserDO);
    }

    /**
     * 根据code删除人员
      * @param code
     */
    public void deleteByCode(String code) {
        bizUserDao.deleteByCode(code);
    }

    /**
     * 根据工作证时间查询
     * @param start
     * @param end
     * @return
     */
    public List<BizUserDTO> getByWorkCardBeginTime(Date start, Date end) {
        List<BizUserDTO> result = Lists.newArrayList();
        List<BizUserDO> bizUserDOList = bizUserDao.findByWorkCardBeginTime(start, end);
        if (CollectionUtils.isNotEmpty(bizUserDOList)) {
            result.addAll(BizUserConvertor.do2dtoList(bizUserDOList));
        }
        return result;
    }

    public List<BizUserDTO> getByContractEngTime(Date start, Date end) {
        List<BizUserDTO> result = Lists.newArrayList();
        List<BizUserDO> first = bizUserDao.findByFirstContractEngTime(start, end);
        if (CollectionUtils.isNotEmpty(first)) {
            result.addAll(BizUserConvertor.do2dtoList(first));
        }
        List<BizUserDO> second = bizUserDao.findBySecondContractEngTime(start, end);
        if (CollectionUtils.isNotEmpty(second)) {
            result.addAll(BizUserConvertor.do2dtoList(second));
        }
        List<BizUserDO> third = bizUserDao.findByThirdContractEngTime(start, end);
        if (CollectionUtils.isNotEmpty(third)) {
            result.addAll(BizUserConvertor.do2dtoList(third));
        }
        return result;
    }

    public List<BizUserDTO> getByBirthDayAndSex(Date start, Date end, Integer sex){
        List<BizUserDTO> result = Lists.newArrayList();
        List<BizUserDO> bizUserDOList = bizUserDao.findByBirthDayAndSex(start, end, sex);
        result.addAll(BizUserConvertor.do2dtoList(bizUserDOList));
        return result;
    }


    private void fillWithByAddParam(BizUserAddParam param,BizUserDO bizUserDO){
        bizUserDO.setName(param.name);
        //bizUserDO.setPassword(MD5Util.MD5("admin"));
        bizUserDO.setPassword("123456");
        bizUserDO.setPicUrl(param.headPicCode);
        bizUserDO.setBirthdate(DateUtil.convert2Date(param.birthdate,BizUserConstant.DateFormat));
        bizUserDO.setNation(param.nation);
        bizUserDO.setPoliticalLandscape(param.politicalLandscape);
        bizUserDO.setGraduateSchool(param.graduateInstitutions);
        bizUserDO.setPoliceCode(param.policeCode);
        if(CollectionUtils.isNotEmpty(param.quasiDrivingTypeList)) {
            String quasiDrivingType = Joiner.on(",").join(param.quasiDrivingTypeList);
            bizUserDO.setDrivingType(quasiDrivingType);
        }
        if(CollectionUtils.isNotEmpty(param.specialPeopleList)) {
            String specialPeople = Joiner.on(",").join(param.specialPeopleList);
            bizUserDO.setSpecialPeople(specialPeople);
        }
        bizUserDO.setQualification(param.qualification);
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
        bizUserDO.setWorkUnitCode(param.workUnit);
        bizUserDO.setOrganizationUnitCode(param.organizationUnit);
        bizUserDO.setJobCategory(param.jobCategory);
        bizUserDO.setDuty(param.duty);
        bizUserDO.setSocialSecurityNumber(param.socialSecurityNumber);
        bizUserDO.setBeginWorkTime(DateUtil.convert2Date(param.beginWorkTime,BizUserConstant.DateFormat));
        bizUserDO.setContractExpirationDate(DateUtil.convert2Date(param.contractExpirationDate,BizUserConstant.DateFormat));
        bizUserDO.setDimssionDate(DateUtil.convert2Date(param.dimissionDate,BizUserConstant.DateFormat));
        bizUserDO.setDimssionReason(param.dimissionReason);
        bizUserDO.setBeginPoliceWorkTime(DateUtil.convert2Date(param.beginPoliceWorkTime,BizUserConstant.DateFormat));
        bizUserDO.setFirstGradeTime(DateUtil.convert2Date(param.firstGradeTime,BizUserConstant.DateFormat));
        bizUserDO.setWorkCardBeginTime(DateUtil.convert2Date(param.workCardBeginTime,BizUserConstant.DateFormat));
        bizUserDO.setFirstContractBeginTime(DateUtil.convert2Date(param.firstContractBeginTime,BizUserConstant.DateFormat));
        bizUserDO.setFirstContractEngTime(DateUtil.convert2Date(param.firstContractEngTime,BizUserConstant.DateFormat));
        bizUserDO.setSecondContractBeginTime(DateUtil.convert2Date(param.secondContractBeginTime,BizUserConstant.DateFormat));
        bizUserDO.setSecondContractEngTime(DateUtil.convert2Date(param.secondContractEngTime,BizUserConstant.DateFormat));
        bizUserDO.setThirdContractBeginTime(DateUtil.convert2Date(param.thirdContractBeginTime,BizUserConstant.DateFormat));
        bizUserDO.setThirdContractEngTime(DateUtil.convert2Date(param.thirdContractEngTime,BizUserConstant.DateFormat));
        bizUserDO.setDueContract(param.dueContract);
        bizUserDO.setIcbcCardAccount(param.icbcCardAccount);
        bizUserDO.setRuZhiZuLinTime(DateUtil.convert2Date(param.ruZhiZuLinTime,BizUserConstant.DateFormat));
    }

    private String inStrList(List<String> list){
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

    private String inIntList(List<Integer> list){
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














}
