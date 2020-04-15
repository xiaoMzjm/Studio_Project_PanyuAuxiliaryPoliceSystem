package com.base.biz.user.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2020/3/30 12:10 AM
 */
@Entity
@Table(name = "biz_user",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true),
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
    private Integer drivingType; // 准驾车型 @see DrivingTypeEnum

    @Column(length = 4096)
    private String speciality; // 特长

    @Column
    private Integer exserviceman; // 是否退役军人 @see ExservicemanEnum

    @Column(length = 512)
    private String permanentResidenceAddress; // 户籍地址

    @Column(length = 512)
    private String familyAddress; //家庭住址

    @Column
    private Integer sex; // 性别 @see SexEnum

    @Column
    private Integer age; // 年龄

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

    @Column
    private Date retirementDate; // 退休日期。男：出生日期+60，女：出生日期+50

    @Column
    private Integer dimssionType;// 离职类别 @see DimssionTypeEnum

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

    public Integer getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(Integer drivingType) {
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
}