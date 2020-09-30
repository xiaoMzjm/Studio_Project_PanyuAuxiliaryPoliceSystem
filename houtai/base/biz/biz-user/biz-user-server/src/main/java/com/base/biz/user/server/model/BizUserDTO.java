package com.base.biz.user.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author:小M
 * @date:2020/3/30 12:22 AM
 */
public class BizUserDTO {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String code;

    private String password;

    private String picCode;

    private String picUrl;

    private String name; // 姓名

    private Date birthdate; // 生日

    private Integer nation; // 民族 @see NationEnum

    private Integer politicalLandscape; // 政治面貌 @see PoliticalLandscapeEnum

    private String graduateSchool; // 毕业院校

    private String policeCode; // 警号

    private String drivingType; // 准驾车型,逗号隔开 @see DrivingTypeEnum

    private String speciality; // 特长

    private String specialPeople; // 特殊人员

    private String qualification; // 资格证书

    private Integer exserviceman; // 是否退役军人 @see ExservicemanEnum

    private String permanentResidenceAddress; // 户籍地址

    private String familyAddress; //家庭住址

    private Integer sex; // 性别 @see SexEnum

    private Integer age; // 年龄

    private String nativePlace; // 籍贯

    private Integer education; // 学历 @see EducationEnum

    private String major; // 专业

    private Integer maritalStatus; // 婚姻状况 @see MaritalStatusEnum

    private String identityCard; // 身份证

    private String phone; // 手机号码

    private Integer personnelType; // 人员类型 @see PersonnelTypeEnum

    private Integer authorizedStrengthType; // 编制类型 @see AuthorizedStrengthTypeEnum

    private Integer placeOfWork; // 工作岗位 @see PlaceOfWorkEnum

    private Integer jobGrade; // 工作职级 @see JobGradeEnum

    private Integer treatmentGrade; // 待遇级别

    private Integer enrollWay; // 招录方式 @see EnrollWayEnum

    private Date beginWorkTime; // 开始工作时间

    private Date effectiveDateOfTheContrace; // 合同生效日期

    private Date retirementDate; // 退休日期。男：出生日期+60，女：出生日期+50

    private Integer dimssionType;// 离职类别 @see DimssionTypeEnum

    private String workUnitCode; // 工作单位 == 编制单位 关联 单位管理

    private String organizationUnitCode; // 编制单位 == 工作单位 关联 单位管理

    private Integer jobCategory; // 岗位类别 @see JobCategoryEnum

    private String duty; // 职务

    private String socialSecurityNumber; // 社保号码

    private Date beginPoliceWorkTime; // 入职公安时间

    private Date contractExpirationDate; // 合同失效日期

    private Date dimssionDate; // 离职日期

    private String dimssionReason; // 离职原因

    private Date firstGradeTime; // 任一级辅警起算时间

    private Date workCardBeginTime; // 工作证起始日期

    private Date firstContractBeginTime; // 第一次合同生效时间

    private Date firstContractEngTime; // 第一次合同终止时间

    private Date secondContractBeginTime; // 第二次合同生效时间

    private Date secondContractEngTime; // 第二次合同终止时间

    private Date thirdContractBeginTime; // 第三次合同生效时间

    private Date thirdContractEngTime; // 第三次合同终止时间

    private Integer dueContract; // 到期合同

    private String icbcCardAccount; // 工商银行账号

    private Date ruZhiZuLinTime; // 入职租赁日期

    private Integer userType; // 人员类型，0辅警，1民警

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public Integer getPoliticalLandscape() {
        return politicalLandscape;
    }

    public void setPoliticalLandscape(Integer politicalLandscape) {
        this.politicalLandscape = politicalLandscape;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public String getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(String drivingType) {
        this.drivingType = drivingType;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getExserviceman() {
        return exserviceman;
    }

    public void setExserviceman(Integer exserviceman) {
        this.exserviceman = exserviceman;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Integer getAuthorizedStrengthType() {
        return authorizedStrengthType;
    }

    public void setAuthorizedStrengthType(Integer authorizedStrengthType) {
        this.authorizedStrengthType = authorizedStrengthType;
    }

    public Integer getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(Integer placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public Integer getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(Integer jobGrade) {
        this.jobGrade = jobGrade;
    }

    public Integer getTreatmentGrade() {
        return treatmentGrade;
    }

    public void setTreatmentGrade(Integer treatmentGrade) {
        this.treatmentGrade = treatmentGrade;
    }

    public Integer getEnrollWay() {
        return enrollWay;
    }

    public void setEnrollWay(Integer enrollWay) {
        this.enrollWay = enrollWay;
    }

    public Date getBeginWorkTime() {
        return beginWorkTime;
    }

    public void setBeginWorkTime(Date beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public Date getEffectiveDateOfTheContrace() {
        return effectiveDateOfTheContrace;
    }

    public void setEffectiveDateOfTheContrace(Date effectiveDateOfTheContrace) {
        this.effectiveDateOfTheContrace = effectiveDateOfTheContrace;
    }

    public Date getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(Date retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Integer getDimssionType() {
        return dimssionType;
    }

    public void setDimssionType(Integer dimssionType) {
        this.dimssionType = dimssionType;
    }

    public String getWorkUnitCode() {
        return workUnitCode;
    }

    public void setWorkUnitCode(String workUnitCode) {
        this.workUnitCode = workUnitCode;
    }

    public String getOrganizationUnitCode() {
        return organizationUnitCode;
    }

    public void setOrganizationUnitCode(String organizationUnitCode) {
        this.organizationUnitCode = organizationUnitCode;
    }

    public Integer getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(Integer jobCategory) {
        this.jobCategory = jobCategory;
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

    public Date getBeginPoliceWorkTime() {
        return beginPoliceWorkTime;
    }

    public void setBeginPoliceWorkTime(Date beginPoliceWorkTime) {
        this.beginPoliceWorkTime = beginPoliceWorkTime;
    }

    public Date getContractExpirationDate() {
        return contractExpirationDate;
    }

    public void setContractExpirationDate(Date contractExpirationDate) {
        this.contractExpirationDate = contractExpirationDate;
    }

    public Date getDimssionDate() {
        return dimssionDate;
    }

    public void setDimssionDate(Date dimssionDate) {
        this.dimssionDate = dimssionDate;
    }

    public String getDimssionReason() {
        return dimssionReason;
    }

    public void setDimssionReason(String dimssionReason) {
        this.dimssionReason = dimssionReason;
    }

    public String getSpecialPeople() {
        return specialPeople;
    }

    public void setSpecialPeople(String specialPeople) {
        this.specialPeople = specialPeople;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {


        this.qualification = qualification;
    }


    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public Date getFirstGradeTime() {
        return firstGradeTime;
    }

    public void setFirstGradeTime(Date firstGradeTime) {
        this.firstGradeTime = firstGradeTime;
    }

    public Date getWorkCardBeginTime() {
        return workCardBeginTime;
    }

    public void setWorkCardBeginTime(Date workCardBeginTime) {
        this.workCardBeginTime = workCardBeginTime;
    }

    public Date getFirstContractBeginTime() {
        return firstContractBeginTime;
    }

    public void setFirstContractBeginTime(Date firstContractBeginTime) {
        this.firstContractBeginTime = firstContractBeginTime;
    }

    public Date getFirstContractEngTime() {
        return firstContractEngTime;
    }

    public void setFirstContractEngTime(Date firstContractEngTime) {
        this.firstContractEngTime = firstContractEngTime;
    }

    public Date getSecondContractBeginTime() {
        return secondContractBeginTime;
    }

    public void setSecondContractBeginTime(Date secondContractBeginTime) {
        this.secondContractBeginTime = secondContractBeginTime;
    }

    public Date getSecondContractEngTime() {
        return secondContractEngTime;
    }

    public void setSecondContractEngTime(Date secondContractEngTime) {
        this.secondContractEngTime = secondContractEngTime;
    }

    public Date getThirdContractBeginTime() {
        return thirdContractBeginTime;
    }

    public void setThirdContractBeginTime(Date thirdContractBeginTime) {
        this.thirdContractBeginTime = thirdContractBeginTime;
    }

    public Date getThirdContractEngTime() {
        return thirdContractEngTime;
    }

    public void setThirdContractEngTime(Date thirdContractEngTime) {
        this.thirdContractEngTime = thirdContractEngTime;
    }

    public Integer getDueContract() {
        return dueContract;
    }

    public void setDueContract(Integer dueContract) {
        this.dueContract = dueContract;
    }

    public String getIcbcCardAccount() {
        return icbcCardAccount;
    }

    public void setIcbcCardAccount(String icbcCardAccount) {
        this.icbcCardAccount = icbcCardAccount;
    }

    public Date getRuZhiZuLinTime() {
        return ruZhiZuLinTime;
    }

    public void setRuZhiZuLinTime(Date ruZhiZuLinTime) {
        this.ruZhiZuLinTime = ruZhiZuLinTime;
    }
}
