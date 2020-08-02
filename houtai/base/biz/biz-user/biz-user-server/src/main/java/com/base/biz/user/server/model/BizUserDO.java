package com.base.biz.user.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import io.swagger.annotations.ApiParam;

/**
 * @author:小M
 * @date:2020/3/30 12:10 AM
 */
@Entity
@Table(name = "biz_user",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true),
        @Index(name = "idx_police_code",  columnList="policeCode", unique = true),
        @Index(name = "idx_ide_pa",  columnList="identityCard,password", unique = true)})
public class BizUserDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date gmtCreate;

    @Column(nullable = false)
    private Date gmtModified;

    @Column(length = 64 , nullable = false)
    private String code;

    @Column(length = 64 , nullable = false)
    private String password;

    @Column(length = 256)
    private String picUrl;


    @Column(length = 64)
    private String name; // 姓名

    @Column
    private Date birthdate; // 生日

    @Column
    private Integer nation; // 民族 @see NationEnum

    @Column
    private Integer politicalLandscape; // 政治面貌 @see PoliticalLandscapeEnum

    @Column(length = 128)
    private String graduateSchool; // 毕业院校

    @Column(length = 64)
    private String policeCode; // 警号

    @Column
    private String drivingType; // 准驾车型 @see DrivingTypeEnum

    @Column(length = 512)
    private String specialPeople; // 特殊人员，多个逗号隔开 @see SpecialPeopleEnum

    @Column(length = 1024)
    private String qualification; // 资格证书

    @Column(length = 512)
    private String permanentResidenceAddress; // 户籍地址

    @Column(length = 512)
    private String familyAddress; //家庭住址

    @Column
    private Integer sex; // 性别 @see SexEnum

    @Column(length = 512)
    private String nativePlace; // 籍贯

    @Column
    private Integer education; // 学历 @see EducationEnum

    @Column(length = 128)
    private String major; // 专业

    @Column
    private Integer maritalStatus; // 婚姻状况 @see MaritalStatusEnum

    @Column(length = 64 , nullable = false)
    private String identityCard; // 身份证

    @Column(length = 64)
    private String phone; // 手机号码

    @Column
    private Integer personnelType; // 人员类型 @see PersonnelTypeEnum

    @Column
    private Integer authorizedStrengthType; // 编制类型 @see AuthorizedStrengthTypeEnum

    @Column
    private Integer placeOfWork; // 工作岗位 @see PlaceOfWorkEnum

    @Column
    private Integer jobGrade; // 工作职级 @see JopGradeEnum

    @Column
    private Integer treatmentGrade; // 待遇级别

    @Column
    private Integer enrollWay; // 招录方式 @see EnrollWayEnum

    @Column
    private Date beginWorkTime; // 开始工作时间

    @Column
    private Date effectiveDateOfTheContrace; // 合同生效日期

    @Column(length = 128)
    private String workUnitCode; // 工作单位 == 编制单位 关联 单位管理

    @Column(length = 128)
    private String organizationUnitCode; // 编制单位 == 工作单位 关联 单位管理

    @Column
    private Integer jobCategory; // 岗位类别 @see JobCategoryEnum

    @Column(length = 128)
    private String duty; // 职务

    @Column(length = 64)
    private String socialSecurityNumber; // 社保号码

    @Column
    private Date beginPoliceWorkTime; // 入职公安时间

    @Column
    private Date contractExpirationDate; // 合同失效日期

    @Column
    private Date dimssionDate; // 离职日期

    @Column(length = 512)
    private String dimssionReason; // 离职原因

    @Column
    private Date firstGradeTime; // 任一级辅警起算时间
    @Column
    private Date workCardBeginTime; // 工作证起始日期
    @Column
    private Date firstContractBeginTime; // 第一次合同生效时间
    @Column
    private Date firstContractEngTime; // 第一次合同终止时间
    @Column
    private Date secondContractBeginTime; // 第二次合同生效时间
    @Column
    private Date secondContractEngTime; // 第二次合同终止时间
    @Column
    private Date thirdContractBeginTime; // 第三次合同生效时间
    @Column
    private Date thirdContractEngTime; // 第三次合同终止时间
    @Column
    private Integer dueContract; // 到期合同
    @Column(length = 64)
    private String icbcCardAccount; // 工商银行账号
    @Column
    private Date ruZhiZuLinTime; // 入职租赁日期
    @Column
    private Integer userType; // 人员类型，0辅警，1民警

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
