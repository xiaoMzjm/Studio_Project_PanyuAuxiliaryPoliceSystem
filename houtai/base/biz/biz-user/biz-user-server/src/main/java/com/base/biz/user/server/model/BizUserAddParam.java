package com.base.biz.user.server.model;

import java.util.Date;
import java.util.List;

import com.base.authority.client.common.Enums.AuthorityTypeEnum;
import com.base.biz.user.client.common.Enums.AuthorizedStrengthTypeEnum;
import com.base.biz.user.client.common.Enums.DimssionTypeEnum;
import com.base.biz.user.client.common.Enums.DrivingTypeEnum;
import com.base.biz.user.client.common.Enums.EducationEnum;
import com.base.biz.user.client.common.Enums.EnrollWayEnum;
import com.base.biz.user.client.common.Enums.ExservicemanEnum;
import com.base.biz.user.client.common.Enums.JobGradeEnum;
import com.base.biz.user.client.common.Enums.MaritalStatusEnum;
import com.base.biz.user.client.common.Enums.NationEnum;
import com.base.biz.user.client.common.Enums.PersonnelTypeEnum;
import com.base.biz.user.client.common.Enums.PlaceOfWorkEnum;
import com.base.biz.user.client.common.Enums.PoliticalLandscapeEnum;
import com.base.biz.user.client.common.Enums.SexEnum;
import com.base.biz.user.client.common.Enums.TreatmentGradeEnum;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.support.DataAccessUtils;

/**
 * @author:小M
 * @date:2020/4/6 8:07 PM
 */
public class BizUserAddParam {

    @ApiParam(name="姓名",value="name")
    public String name;
    @ApiParam(name="头像",value="headPicCode")
    public String headPicCode;
    @ApiParam(name="出生日期",value="birthdate")
    public String birthdate;

    @ApiParam(name="民族",value="nation")
    public Integer nation;
    @ApiParam(name="民族",value="nationStr")
    public String nationStr;

    @ApiParam(name="政治面貌",value="politicalLandscape")
    public Integer politicalLandscape;
    @ApiParam(name="政治面貌",value="politicalLandscapeStr")
    public String politicalLandscapeStr;

    @ApiParam(name="毕业院校",value="graduateInstitutions")
    public String graduateInstitutions;
    @ApiParam(name="警号",value="policeCode")
    public String policeCode;

    @ApiParam(name="准驾车型",value="quasiDrivingTypeList")
    public List<Integer> quasiDrivingTypeList;
    @ApiParam(name="准驾车型",value="quasiDrivingTypeStr")
    public String quasiDrivingTypeStr;

    @ApiParam(name="特殊人员",value="specialPeopleList")
    public List<Integer> specialPeopleList;
    @ApiParam(name="特殊人员",value="specialPeopleStr")
    public String specialPeopleStr;

    @ApiParam(name="资格证书",value="qualification")
    public String qualification;

    @ApiParam(name="户籍地址",value="permanentResidenceAddress")
    public String permanentResidenceAddress;
    @ApiParam(name="家庭地址",value="familyAddress")
    public String familyAddress;

    @ApiParam(name="性别",value="sex")
    public Integer sex;
    @ApiParam(name="性别",value="sexStr")
    public String sexStr;

    @ApiParam(name="籍贯",value="nativePlace")
    public String nativePlace;

    @ApiParam(name="学历",value="education")
    public Integer education;
    @ApiParam(name="学历",value="educationStr")
    public String educationStr;

    @ApiParam(name="专业",value="major")
    public String major;

    @ApiParam(name="婚姻状况",value="maritalStatus")
    public Integer maritalStatus;
    @ApiParam(name="婚姻状况",value="maritalStatusStr")
    public String maritalStatusStr;

    @ApiParam(name="身份证",value="identityCard")
    public String identityCard;
    @ApiParam(name="手机",value="phone")
    public String phone;

    @ApiParam(name="人员类别",value="personnelType")
    public Integer personnelType;
    @ApiParam(name="人员类别",value="personnelTypeStr")
    public String personnelTypeStr;

    @ApiParam(name="编制类别",value="authorizedStrengthType")
    public Integer authorizedStrengthType;
    @ApiParam(name="编制类别",value="authorizedStrengthTypeStr")
    public String authorizedStrengthTypeStr;

    @ApiParam(name="工作岗位",value="placeOfWork")
    public Integer placeOfWork;
    @ApiParam(name="工作岗位",value="placeOfWorkStr")
    public String placeOfWorkStr;

    @ApiParam(name="职级",value="jobGrade")
    public Integer jobGrade;
    @ApiParam(name="职级",value="jobGradeStr")
    public String jobGradeStr;

    @ApiParam(name="待遇级别",value="treatmentGrade")
    public Integer treatmentGrade;
    @ApiParam(name="待遇级别",value="treatmentGradeStr")
    public String treatmentGradeStr;

    @ApiParam(name="招录方式",value="enrollWay")
    public Integer enrollWay;
    @ApiParam(name="招录方式",value="enrollWayStr")
    public String enrollWayStr;

    @ApiParam(name="参加工作时间",value="beginWorkTime")
    public String beginWorkTime;
    @ApiParam(name="合同生效时间",value="effectiveDateOfTheContract")
    public String effectiveDateOfTheContract;

    @ApiParam(name="工作单位",value="workUnit")
    public String workUnit;
    @ApiParam(name="工作单位",value="workUnitName")
    public String workUnitName;

    @ApiParam(name="编制单位",value="organizationUnit")
    public String organizationUnit;
    @ApiParam(name="编制单位",value="organizationUnitName")
    public String organizationUnitName;

    @ApiParam(name="岗位类别",value="jobCategory")
    public Integer jobCategory;
    @ApiParam(name="岗位类别",value="jobCategoryStr")
    public String jobCategoryStr;

    @ApiParam(name="职务",value="duty")
    public String duty;
    @ApiParam(name="社保编码",value="socialSecurityNumber")
    public String socialSecurityNumber;
    @ApiParam(name="入职公安时间",value="beginPoliceWorkTime")
    public String beginPoliceWorkTime;
    @ApiParam(name="合同失效时间",value="contractExpirationDate")
    public String contractExpirationDate;
    @ApiParam(name="离职时间",value="dimissionDate")
    public String dimissionDate;
    @ApiParam(name="离职原因",value="dimissionReason")
    public String dimissionReason;

    @ApiParam(name="任一级辅警起算时间",value="firstGradeTime")
    public String firstGradeTime;
    @ApiParam(name="工作证起始日期",value="workCardBeginTime")
    public String workCardBeginTime;
    @ApiParam(name="第一次合同生效时间",value="firstContractBeginTime")
    public String firstContractBeginTime;
    @ApiParam(name="第一次合同终止时间",value="firstContractEngTime")
    public String firstContractEngTime;
    @ApiParam(name="第二次合同生效时间",value="secondContractBeginTime")
    public String secondContractBeginTime;
    @ApiParam(name="第二次合同终止时间",value="secondContractEngTime")
    public String secondContractEngTime;
    @ApiParam(name="第三次合同生效时间",value="thirdContractBeginTime")
    public String thirdContractBeginTime;
    @ApiParam(name="第三次合同终止时间",value="thirdContractEngTime")
    public String thirdContractEngTime;

    @ApiParam(name="到期合同",value="dueContract")
    public Integer dueContract;
    @ApiParam(name="到期合同",value="dueContractStr")
    public String dueContractStr;

    @ApiParam(name="工商银行账号",value="icbcCardAccount")
    public String icbcCardAccount;
    @ApiParam(name="入职租赁日期",value="ruZhiZuLinTime")
    public String ruZhiZuLinTime;




    @ApiParam(name="个人经历",value="personalExperience")
    public List<AddParamExperience> personalExperience;
    @ApiParam(name="家庭成员",value="familyMember")
    public List<AddParamFamilyMember> familyMember;
    @ApiParam(name="奖惩情况",value="award")
    public List<AddParamAward> award;
    @ApiParam(name="考核情况",value="assessment")
    public List<AddParamAssessment> assessment;

    public static class AddParamExperience {
        @ApiParam(name="起始日期",value="timeStart")
        public String timeStart;
        @ApiParam(name="结束日期",value="timeEnd")
        public String timeEnd;
        @ApiParam(name="单位或组织名称",value="unit")
        public String unit;
        @ApiParam(name="部门",value="department")
        public String department;
        @ApiParam(name="职务",value="duty")
        public String duty;
    }

    public static class AddParamFamilyMember {
        @ApiParam(name="姓名",value="name")
        public String name;
        @ApiParam(name="关系",value="relation")
        public String relation;
        @ApiParam(name="单位",value="company")
        public String company;
        @ApiParam(name="单位",value="duty")
        public String duty;
        @ApiParam(name="身份证",value="identityCard")
        public String identityCard;
        @ApiParam(name="手机",value="phone")
        public String phone;
        @ApiParam(name="政治面貌",value="politicalLandscape")
        public Integer politicalLandscape;
    }

    public static class AddParamAward {
        @ApiParam(name="奖惩名称",value="name")
        public String name;
        @ApiParam(name="奖惩时间",value="time")
        public String time;
        @ApiParam(name="奖惩原因",value="reason")
        public String reason;
        @ApiParam(name="批注单位",value="company")
        public String company;
    }

    public static class AddParamAssessment{
        @ApiParam(name="发生时间",value="time")
        public String time;
        @ApiParam(name="等级",value="grade")
        public String grade;
        @ApiParam(name="备注",value="remark")
        public String remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPicCode() {
        return headPicCode;
    }

    public void setHeadPicCode(String headPicCode) {
        this.headPicCode = headPicCode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getNationStr() {
        return nationStr;
    }

    public void setNationStr(String nationStr) {
        this.nationStr = nationStr;
    }

    public Integer getPoliticalLandscape() {
        return politicalLandscape;
    }

    public void setPoliticalLandscape(Integer politicalLandscape) {
        this.politicalLandscape = politicalLandscape;
    }

    public String getPoliticalLandscapeStr() {
        return politicalLandscapeStr;
    }

    public void setPoliticalLandscapeStr(String politicalLandscapeStr) {
        this.politicalLandscapeStr = politicalLandscapeStr;
    }

    public String getGraduateInstitutions() {
        return graduateInstitutions;
    }

    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public List<Integer> getQuasiDrivingTypeList() {
        return quasiDrivingTypeList;
    }

    public void setQuasiDrivingTypeList(List<Integer> quasiDrivingTypeList) {
        this.quasiDrivingTypeList = quasiDrivingTypeList;
    }

    public String getQuasiDrivingTypeStr() {
        return quasiDrivingTypeStr;
    }

    public void setQuasiDrivingTypeStr(String quasiDrivingTypeStr) {
        this.quasiDrivingTypeStr = quasiDrivingTypeStr;
    }

    public List<Integer> getSpecialPeopleList() {
        return specialPeopleList;
    }

    public void setSpecialPeopleList(List<Integer> specialPeopleList) {
        this.specialPeopleList = specialPeopleList;
    }

    public String getSpecialPeopleStr() {
        return specialPeopleStr;
    }

    public void setSpecialPeopleStr(String specialPeopleStr) {
        this.specialPeopleStr = specialPeopleStr;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPermanentResidenceAddress() {
        return permanentResidenceAddress;
    }

    public void setPermanentResidenceAddress(String permanentResidenceAddress) {
        this.permanentResidenceAddress = permanentResidenceAddress;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getEducationStr() {
        return educationStr;
    }

    public void setEducationStr(String educationStr) {
        this.educationStr = educationStr;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatusStr() {
        return maritalStatusStr;
    }

    public void setMaritalStatusStr(String maritalStatusStr) {
        this.maritalStatusStr = maritalStatusStr;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(Integer personnelType) {
        this.personnelType = personnelType;
    }

    public String getPersonnelTypeStr() {
        return personnelTypeStr;
    }

    public void setPersonnelTypeStr(String personnelTypeStr) {
        this.personnelTypeStr = personnelTypeStr;
    }

    public Integer getAuthorizedStrengthType() {
        return authorizedStrengthType;
    }

    public void setAuthorizedStrengthType(Integer authorizedStrengthType) {
        this.authorizedStrengthType = authorizedStrengthType;
    }

    public String getAuthorizedStrengthTypeStr() {
        return authorizedStrengthTypeStr;
    }

    public void setAuthorizedStrengthTypeStr(String authorizedStrengthTypeStr) {
        this.authorizedStrengthTypeStr = authorizedStrengthTypeStr;
    }

    public Integer getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(Integer placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getPlaceOfWorkStr() {
        return placeOfWorkStr;
    }

    public void setPlaceOfWorkStr(String placeOfWorkStr) {
        this.placeOfWorkStr = placeOfWorkStr;
    }

    public Integer getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(Integer jobGrade) {
        this.jobGrade = jobGrade;
    }

    public String getJobGradeStr() {
        return jobGradeStr;
    }

    public void setJobGradeStr(String jobGradeStr) {
        this.jobGradeStr = jobGradeStr;
    }

    public Integer getTreatmentGrade() {
        return treatmentGrade;
    }

    public void setTreatmentGrade(Integer treatmentGrade) {
        this.treatmentGrade = treatmentGrade;
    }

    public String getTreatmentGradeStr() {
        return treatmentGradeStr;
    }

    public void setTreatmentGradeStr(String treatmentGradeStr) {
        this.treatmentGradeStr = treatmentGradeStr;
    }

    public Integer getEnrollWay() {
        return enrollWay;
    }

    public void setEnrollWay(Integer enrollWay) {
        this.enrollWay = enrollWay;
    }

    public String getEnrollWayStr() {
        return enrollWayStr;
    }

    public void setEnrollWayStr(String enrollWayStr) {
        this.enrollWayStr = enrollWayStr;
    }

    public String getBeginWorkTime() {
        return beginWorkTime;
    }

    public void setBeginWorkTime(String beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public String getEffectiveDateOfTheContract() {
        return effectiveDateOfTheContract;
    }

    public void setEffectiveDateOfTheContract(String effectiveDateOfTheContract) {
        this.effectiveDateOfTheContract = effectiveDateOfTheContract;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getOrganizationUnitName() {
        return organizationUnitName;
    }

    public void setOrganizationUnitName(String organizationUnitName) {
        this.organizationUnitName = organizationUnitName;
    }

    public Integer getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(Integer jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobCategoryStr() {
        return jobCategoryStr;
    }

    public void setJobCategoryStr(String jobCategoryStr) {
        this.jobCategoryStr = jobCategoryStr;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getBeginPoliceWorkTime() {
        return beginPoliceWorkTime;
    }

    public void setBeginPoliceWorkTime(String beginPoliceWorkTime) {
        this.beginPoliceWorkTime = beginPoliceWorkTime;
    }

    public String getContractExpirationDate() {
        return contractExpirationDate;
    }

    public void setContractExpirationDate(String contractExpirationDate) {
        this.contractExpirationDate = contractExpirationDate;
    }

    public String getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(String dimissionDate) {
        this.dimissionDate = dimissionDate;
    }

    public String getDimissionReason() {
        return dimissionReason;
    }

    public void setDimissionReason(String dimissionReason) {
        this.dimissionReason = dimissionReason;
    }

    public String getFirstGradeTime() {
        return firstGradeTime;
    }

    public void setFirstGradeTime(String firstGradeTime) {
        this.firstGradeTime = firstGradeTime;
    }

    public String getWorkCardBeginTime() {
        return workCardBeginTime;
    }

    public void setWorkCardBeginTime(String workCardBeginTime) {
        this.workCardBeginTime = workCardBeginTime;
    }

    public String getFirstContractBeginTime() {
        return firstContractBeginTime;
    }

    public void setFirstContractBeginTime(String firstContractBeginTime) {
        this.firstContractBeginTime = firstContractBeginTime;
    }

    public String getFirstContractEngTime() {
        return firstContractEngTime;
    }

    public void setFirstContractEngTime(String firstContractEngTime) {
        this.firstContractEngTime = firstContractEngTime;
    }

    public String getSecondContractBeginTime() {
        return secondContractBeginTime;
    }

    public void setSecondContractBeginTime(String secondContractBeginTime) {
        this.secondContractBeginTime = secondContractBeginTime;
    }

    public String getSecondContractEngTime() {
        return secondContractEngTime;
    }

    public void setSecondContractEngTime(String secondContractEngTime) {
        this.secondContractEngTime = secondContractEngTime;
    }

    public String getThirdContractBeginTime() {
        return thirdContractBeginTime;
    }

    public void setThirdContractBeginTime(String thirdContractBeginTime) {
        this.thirdContractBeginTime = thirdContractBeginTime;
    }

    public String getThirdContractEngTime() {
        return thirdContractEngTime;
    }

    public void setThirdContractEngTime(String thirdContractEngTime) {
        this.thirdContractEngTime = thirdContractEngTime;
    }

    public Integer getDueContract() {
        return dueContract;
    }

    public void setDueContract(Integer dueContract) {
        this.dueContract = dueContract;
    }

    public String getDueContractStr() {
        return dueContractStr;
    }

    public void setDueContractStr(String dueContractStr) {
        this.dueContractStr = dueContractStr;
    }

    public String getIcbcCardAccount() {
        return icbcCardAccount;
    }

    public void setIcbcCardAccount(String icbcCardAccount) {
        this.icbcCardAccount = icbcCardAccount;
    }

    public String getRuZhiZuLinTime() {
        return ruZhiZuLinTime;
    }

    public void setRuZhiZuLinTime(String ruZhiZuLinTime) {
        this.ruZhiZuLinTime = ruZhiZuLinTime;
    }

    public List<AddParamExperience> getPersonalExperience() {
        return personalExperience;
    }

    public void setPersonalExperience(
        List<AddParamExperience> personalExperience) {
        this.personalExperience = personalExperience;
    }

    public List<AddParamFamilyMember> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<AddParamFamilyMember> familyMember) {
        this.familyMember = familyMember;
    }

    public List<AddParamAward> getAward() {
        return award;
    }

    public void setAward(List<AddParamAward> award) {
        this.award = award;
    }

    public List<AddParamAssessment> getAssessment() {
        return assessment;
    }

    public void setAssessment(List<AddParamAssessment> assessment) {
        this.assessment = assessment;
    }
}
