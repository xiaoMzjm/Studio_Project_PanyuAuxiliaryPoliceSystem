package com.base.biz.user.server.dao;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.model.BizUserDO;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/30 12:19 AM
 */
@Repository
public interface BizUserDao extends JpaRepository<BizUserDO,Long> {

    @Query(nativeQuery = true, value="select * from biz_user where 1=1 and if(:name != '',name like CONCAT('%',:name,'%'),1=1)  "
        + "and work_unit_code in(:companyList) ")
    List<BizUserDO> findByNameAndCompanyList(@Param("name") String name, @Param("companyList") List<String> companyList);

    @Query(nativeQuery = true, value="select * from biz_user where 1=1 "
        + "and                                                      work_unit_code in(:companyList)  "
        + "and if(:name != '',                                      name like CONCAT('%',:name,'%'),1=1) "
        + "and if(:birthdateBeginDate != '',                        birthdate >= :birthdateBeginDate,1=1) "
        + "and if(:birthdateEndDate != '',                          birthdate <= :birthdateEndDate,1=1) "
        + "and                                                      nation in(:nationList) "
        + "and                                                      political_landscape in(:politicalLandscapeList) "
        + "and if(:graduateInstitutions != '',                      graduate_school like CONCAT('%',:graduateInstitutions,'%'),1=1) "
        + "and if(:policeCode != '',                                police_code like CONCAT('%',:policeCode,'%'),1=1) "
        + "and                                                      driving_type in(:quasiDrivingTypeList) "
        + "and                                                      exserviceman in(:exservicemanList) "
        + "and if(:permanentResidenceAddress != '',                 permanent_residence_address like CONCAT('%',:permanentResidenceAddress,'%'),1=1) "
        + "and                                                      sex in(:sexList) "
        + "and if(:ageBegin != '',                                  age >= :ageBegin,1=1) "
        + "and if(:ageEnd != '',                                    age <= :ageEnd,1=1) "
        + "and if(:nativePlace != '',                               native_place like CONCAT('%',:nativePlace,'%'),1=1) "
        + "and                                                      education in(:educationList) "
        + "and if(:major != '',                                     major like CONCAT('%',:major,'%'),1=1) "
        + "and                                                      marital_status in(:maritalStatusList) "
        + "and if(:identityCard != '',                              identity_card like CONCAT('%',:identityCard,'%'),1=1) "
        + "and if(:phone != '',                                     phone like CONCAT('%',:phone,'%'),1=1) "
        + "and                                                      personnel_type in(:personnelTypeList) "
        + "and                                                      authorized_strength_type in(:authorizedStrengthTypeList) "
        + "and                                                      place_of_work in(:placeOfWorkList) "
        + "and                                                      treatment_grade in(:treatmentGradeList) "
        + "and                                                      enroll_way in(:enrollWayList) "
        + "and if(:beginWorkTimeBeginDate != '',                    begin_work_time >= :beginWorkTimeBeginDate,1=1) "
        + "and if(:beginWorkTimeEndDate != '',                      begin_work_time <= :beginWorkTimeEndDate,1=1) "
        + "and if(:effectiveDateOfTheContractBeginDate != '',       effective_date_of_the_contrace >= :effectiveDateOfTheContractBeginDate,1=1) "
        + "and if(:effectiveDateOfTheContractEndDate != '',         effective_date_of_the_contrace <= :effectiveDateOfTheContractEndDate,1=1) "
        + "and if(:retirementDateBeginDate != '',                   retirement_date >= :retirementDateBeginDate,1=1) "
        + "and if(:retirementDateEndDate != '',                     retirement_date <= :retirementDateEndDate,1=1) "
        + "and                                                      dimssion_type in(:dimissionTypeList) "
        + "and                                                      work_unit_code in(:workUnitCodeList)  "
        + "and                                                      organization_unit_code in(:organizationUnitCodeList)  "
        + "and                                                      job_category in(:jobCategoryList)  "
        + "and if(:duty != '',                                      duty like CONCAT('%',:duty,'%'),1=1) "
        + "and if(:socialSecurityNumber != '',                      social_security_number like CONCAT('%',:socialSecurityNumber,'%'),1=1) "
        + "and if(:beginPoliceWorkTimeBeginDate != '',              begin_police_work_time >= :beginPoliceWorkTimeBeginDate,1=1) "
        + "and if(:beginPoliceWorkTimeEndDate != '',                begin_police_work_time <= :beginPoliceWorkTimeEndDate,1=1) "
        + "and if(:contractExpirationDateBeginDate != '',           contract_expiration_date >= :contractExpirationDateBeginDate,1=1) "
        + "and if(:contractExpirationDateEndDate != '',             contract_expiration_date <= :contractExpirationDateEndDate,1=1) "
        + "and if(:dimissionDateBeginDate != '',                    dimssion_date >= :dimissionDateBeginDate,1=1) "
        + "and if(:dimissionDateEndDate != '',                      dimssion_date <= :dimissionDateEndDate,1=1) "
        + "and if(:dimissionReason != '',                           dimssion_reason like CONCAT('%',:dimissionReason,'%'),1=1) ")
    List<BizUserDO> findByParam(@Param("companyList") List<String> companyList,
                                @Param("name") String name,
                                @Param("birthdateBeginDate") Date birthdateBeginDate,
                                @Param("birthdateEndDate") Date birthdateEndDate,
                                @Param("nationList") List<Integer> nationList,
                                @Param("politicalLandscapeList") List<Integer> politicalLandscapeList,
                                @Param("graduateInstitutions") String graduateInstitutions,
                                @Param("policeCode") String policeCode,
                                @Param("quasiDrivingTypeList") List<Integer> quasiDrivingTypeList,
                                @Param("exservicemanList") List<Integer> exservicemanList,
                                @Param("permanentResidenceAddress") String permanentResidenceAddress,
                                @Param("sexList") List<Integer> sexList,
                                @Param("ageBegin") Integer ageBegin,
                                @Param("ageEnd") Integer ageEnd,
                                @Param("nativePlace") String nativePlace,
                                @Param("educationList") List<Integer> educationList,
                                @Param("major") String major,
                                @Param("maritalStatusList") List<Integer> maritalStatusList,
                                @Param("identityCard") String identityCard,
                                @Param("phone") String phone,
                                @Param("personnelTypeList") List<Integer> personnelTypeList,
                                @Param("authorizedStrengthTypeList") List<Integer> authorizedStrengthTypeList,
                                @Param("placeOfWorkList") List<Integer> placeOfWorkList,
                                @Param("treatmentGradeList") List<Integer> treatmentGradeList,
                                @Param("enrollWayList") List<Integer> enrollWayList,
                                @Param("beginWorkTimeBeginDate") Date beginWorkTimeBeginDate,
                                @Param("beginWorkTimeEndDate") Date beginWorkTimeEndDate,
                                @Param("effectiveDateOfTheContractBeginDate") Date effectiveDateOfTheContractBeginDate,
                                @Param("effectiveDateOfTheContractEndDate") Date effectiveDateOfTheContractEndDate,
                                @Param("retirementDateBeginDate") Date retirementDateBeginDate,
                                @Param("retirementDateEndDate") Date retirementDateEndDate,
                                @Param("dimissionTypeList") List<Integer> dimissionTypeList,
                                @Param("workUnitCodeList") List<String> workUnitCodeList,
                                @Param("organizationUnitCodeList") List<String> organizationUnitCodeList,
                                @Param("jobCategoryList") List<Integer> jobCategoryList,
                                @Param("duty") String duty,
                                @Param("socialSecurityNumber") String socialSecurityNumber,
                                @Param("beginPoliceWorkTimeBeginDate") Date beginPoliceWorkTimeBeginDate,
                                @Param("beginPoliceWorkTimeEndDate") Date beginPoliceWorkTimeEndDate,
                                @Param("contractExpirationDateBeginDate") Date contractExpirationDateBeginDate,
                                @Param("contractExpirationDateEndDate") Date contractExpirationDateEndDate,
                                @Param("dimissionDateBeginDate") Date dimissionDateBeginDate,
                                @Param("dimissionDateEndDate") Date dimissionDateEndDate,
                                @Param("dimissionReason") String dimissionReason);


    void deleteByCode(String code);
}
