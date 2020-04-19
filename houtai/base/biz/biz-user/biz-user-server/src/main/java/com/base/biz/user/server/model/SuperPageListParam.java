package com.base.biz.user.server.model;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;

/**
 * @author:小M
 * @date:2020/4/11 10:27 PM
 */
public class SuperPageListParam {

    @ApiParam(name="companyCodeList",value="companyCodeList")
    public List<String> companyCodeList;

    @ApiParam(name="name",value="name")
    public String name;

    @ApiParam(name="birthdateBegin",value="birthdateBegin")
    public String birthdateBegin;

    @ApiParam(name="birthdateEnd",value="birthdateEnd")
    public String birthdateEnd;
    public Date birthdateEndDate;

    @ApiParam(name="nationList",value="nationList")
    public List<Integer> nationList;

    @ApiParam(name="politicalLandscapeList",value="politicalLandscapeList")
    public List<Integer> politicalLandscapeList;

    @ApiParam(name="graduateInstitutions",value="graduateInstitutions")
    public String graduateInstitutions;

    @ApiParam(name="policeCode",value="policeCode")
    public String policeCode;

    @ApiParam(name="quasiDrivingTypeList",value="quasiDrivingTypeList")
    public List<Integer> quasiDrivingTypeList;

    @ApiParam(name="specialPeopleListList",value="specialPeopleListList")
    public List<Integer> specialPeopleList;

    @ApiParam(name="exservicemanList",value="exservicemanList")
    public List<Integer> exservicemanList;

    @ApiParam(name="specialPeople",value="specialPeople")
    public String specialPeople;

    @ApiParam(name="qualification",value="qualification")
    public String qualification;

    @ApiParam(name="permanentResidenceAddress",value="permanentResidenceAddress")
    public String permanentResidenceAddress;

    @ApiParam(name="sexList",value="sexList")
    public List<Integer> sexList;

    @ApiParam(name="ageBegin",value="ageBegin")
    public Integer ageBegin;

    @ApiParam(name="ageEnd",value="ageEnd")
    public Integer ageEnd;

    @ApiParam(name="nativePlace",value="nativePlace")
    public String nativePlace;

    @ApiParam(name="educationList",value="educationList")
    public List<Integer> educationList;

    @ApiParam(name="major",value="major")
    public String major;

    @ApiParam(name="maritalStatusList",value="maritalStatusList")
    public List<Integer> maritalStatusList;

    @ApiParam(name="identityCard",value="identityCard")
    public String identityCard;

    @ApiParam(name="phone",value="phone")
    public String phone;

    @ApiParam(name="personnelTypeList",value="personnelTypeList")
    public List<Integer> personnelTypeList;

    @ApiParam(name="authorizedStrengthTypeList",value="authorizedStrengthTypeList")
    public List<Integer> authorizedStrengthTypeList;

    @ApiParam(name="placeOfWorkList",value="placeOfWorkList")
    public List<Integer> placeOfWorkList;

    @ApiParam(name="treatmentGradeList",value="treatmentGradeList")
    public List<Integer> treatmentGradeList;

    @ApiParam(name="enrollWayList",value="enrollWayList")
    public List<Integer> enrollWayList;

    @ApiParam(name="beginWorkTimeBegin",value="beginWorkTimeBegin")
    public String beginWorkTimeBegin;
    public Date beginWorkTimeBeginDate;

    @ApiParam(name="beginWorkTimeEnd",value="beginWorkTimeEnd")
    public String beginWorkTimeEnd;
    public Date beginWorkTimeEndDate;

    @ApiParam(name="effectiveDateOfTheContractBegin",value="effectiveDateOfTheContractBegin")
    public String effectiveDateOfTheContractBegin;
    public Date effectiveDateOfTheContractBeginDate;

    @ApiParam(name="effectiveDateOfTheContractEnd",value="effectiveDateOfTheContractEnd")
    public String effectiveDateOfTheContractEnd;
    public Date effectiveDateOfTheContractEndDate;

    @ApiParam(name="retirementDateBegin",value="retirementDateBegin")
    public String retirementDateBegin;
    public Date retirementDateBeginDate;

    @ApiParam(name="retirementDateEnd",value="retirementDateEnd")
    public String retirementDateEnd;
    public Date retirementDateEndDate;

    @ApiParam(name="dimissionTypeList",value="dimissionTypeList")
    public List<Integer> dimissionTypeList;

    @ApiParam(name="workUnitCodeList",value="workUnitCodeList")
    public List<String> workUnitCodeList;

    @ApiParam(name="organizationUnitCodeList",value="organizationUnitCodeList")
    public List<String> organizationUnitCodeList;

    @ApiParam(name="jobCategoryList",value="jobCategoryList")
    public List<Integer> jobCategoryList;

    @ApiParam(name="duty",value="duty")
    public String duty;

    @ApiParam(name="socialSecurityNumber",value="socialSecurityNumber")
    public String socialSecurityNumber;

    @ApiParam(name="beginPoliceWorkTimeBegin",value="beginPoliceWorkTimeBegin")
    public String beginPoliceWorkTimeBegin;
    public Date beginPoliceWorkTimeBeginDate;

    @ApiParam(name="beginPoliceWorkTimeEnd",value="beginPoliceWorkTimeEnd")
    public String beginPoliceWorkTimeEnd;
    public Date beginPoliceWorkTimeEndDate;

    @ApiParam(name="contractExpirationDateBegin",value="contractExpirationDateBegin")
    public String contractExpirationDateBegin;
    public Date contractExpirationDateBeginDate;

    @ApiParam(name="contractExpirationDateEnd",value="contractExpirationDateEnd")
    public String contractExpirationDateEnd;
    public Date contractExpirationDateEndDate;

    @ApiParam(name="dimissionDateBegin",value="dimissionDateBegin")
    public String dimissionDateBegin;
    public Date dimissionDateBeginDate;

    @ApiParam(name="dimissionDateEnd",value="dimissionDateEnd")
    public String dimissionDateEnd;
    public Date dimissionDateEndDate;

    @ApiParam(name="dimissionReason",value="dimissionReason")
    public String dimissionReason;

}
