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

    @ApiParam(name="qualification",value="qualification")
    public String qualification;

    @ApiParam(name="familyAddress",value="familyAddress")
    public String familyAddress;

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

    @ApiParam(name="beginWorkTimeEnd",value="beginWorkTimeEnd")
    public String beginWorkTimeEnd;

    @ApiParam(name="effectiveDateOfTheContractBegin",value="effectiveDateOfTheContractBegin")
    public String effectiveDateOfTheContractBegin;

    @ApiParam(name="effectiveDateOfTheContractEnd",value="effectiveDateOfTheContractEnd")
    public String effectiveDateOfTheContractEnd;

    @ApiParam(name="retirementDateBegin",value="retirementDateBegin")
    public String retirementDateBegin;

    @ApiParam(name="retirementDateEnd",value="retirementDateEnd")
    public String retirementDateEnd;

    @ApiParam(name="workUnitList",value="workUnitList")
    public List<String> workUnitList;

    @ApiParam(name="organizationUnitList",value="organizationUnitList")
    public List<String> organizationUnitList;

    @ApiParam(name="jobCategoryList",value="jobCategoryList")
    public List<Integer> jobCategoryList;

    @ApiParam(name="jobGradeList",value="jobGradeList")
    public List<Integer> jobGradeList;

    @ApiParam(name="duty",value="duty")
    public String duty;

    @ApiParam(name="socialSecurityNumber",value="socialSecurityNumber")
    public String socialSecurityNumber;

    @ApiParam(name="beginPoliceWorkTimeBegin",value="beginPoliceWorkTimeBegin")
    public String beginPoliceWorkTimeBegin;

    @ApiParam(name="beginPoliceWorkTimeEnd",value="beginPoliceWorkTimeEnd")
    public String beginPoliceWorkTimeEnd;

    @ApiParam(name="contractExpirationDateBegin",value="contractExpirationDateBegin")
    public String contractExpirationDateBegin;

    @ApiParam(name="contractExpirationDateEnd",value="contractExpirationDateEnd")
    public String contractExpirationDateEnd;

    @ApiParam(name="dimissionDateBegin",value="dimissionDateBegin")
    public String dimissionDateBegin;

    @ApiParam(name="dimissionDateEnd",value="dimissionDateEnd")
    public String dimissionDateEnd;

    @ApiParam(name="dimissionReason",value="dimissionReason")
    public String dimissionReason;

    @ApiParam(name="firstGradeTimeBegin",value="firstGradeTimeBegin")
    public String firstGradeTimeBegin;

    @ApiParam(name="firstGradeTimeEnd",value="firstGradeTimeEnd")
    public String firstGradeTimeEnd;

    @ApiParam(name="workCardBeginTimeBegin",value="workCardBeginTimeBegin")
    public String workCardBeginTimeBegin;

    @ApiParam(name="workCardBeginTimeEnd",value="workCardBeginTimeEnd")
    public String workCardBeginTimeEnd;

    @ApiParam(name="firstContractBeginTimeBegin",value="firstContractBeginTimeBegin")
    public String firstContractBeginTimeBegin;

    @ApiParam(name="firstContractBeginTimeEnd",value="firstContractBeginTimeEnd")
    public String firstContractBeginTimeEnd;

    @ApiParam(name="firstContractEngTimeBegin",value="firstContractEngTimeBegin")
    public String firstContractEngTimeBegin;

    @ApiParam(name="firstContractEngTimeEnd",value="firstContractEngTimeEnd")
    public String firstContractEngTimeEnd;

    @ApiParam(name="secondContractBeginTimeBegin",value="secondContractBeginTimeBegin")
    public String secondContractBeginTimeBegin;

    @ApiParam(name="secondContractBeginTimeEnd",value="secondContractBeginTimeEnd")
    public String secondContractBeginTimeEnd;

    @ApiParam(name="secondContractEngTimeBegin",value="secondContractEngTimeBegin")
    public String secondContractEngTimeBegin;

    @ApiParam(name="secondContractEngTimeEnd",value="secondContractEngTimeEnd")
    public String secondContractEngTimeEnd;

    @ApiParam(name="thirdContractBeginTimeBegin",value="thirdContractBeginTimeBegin")
    public String thirdContractBeginTimeBegin;

    @ApiParam(name="thirdContractBeginTimeEnd",value="thirdContractBeginTimeEnd")
    public String thirdContractBeginTimeEnd;

    @ApiParam(name="thirdContractEngTimeBegin",value="thirdContractEngTimeBegin")
    public String thirdContractEngTimeBegin;

    @ApiParam(name="thirdContractEngTimeEnd",value="thirdContractEngTimeEnd")
    public String thirdContractEngTimeEnd;

    @ApiParam(name="dueContractList",value="dueContractList")
    public List<Integer> dueContractList;

    @ApiParam(name="icbcCardAccount",value="icbcCardAccount")
    public String icbcCardAccount;

    @ApiParam(name="ruZhiZuLinTimeBegin",value="ruZhiZuLinTimeBegin")
    public String ruZhiZuLinTimeBegin;

    @ApiParam(name="ruZhiZuLinTimeEnd",value="ruZhiZuLinTimeEnd")
    public String ruZhiZuLinTimeEnd;

}
