package com.base.biz.user.client.model;

import java.util.List;

/**
 * @author:小M
 * @date:2020/4/10 1:46 AM
 */
public class BizUserDetailVO {
    private String code;
    private String name;
    private String headPicUrl;
    private String birthdate;

    private Integer nation;
    private String nationStr;

    private Integer politicalLandscape;
    private String politicalLandscapeStr;

    private String graduateInstitutions;
    private String policeCode;
    private Integer quasiDrivingType;
    private String quasiDrivingTypeStr;

    private String speciality;
    private Integer exserviceman;
    private String exservicemanStr;
    private String permanentResidenceAddress;
    private String familyAddress;
    private Integer sex;
    private String sexStr;

    private Integer age;
    private String nativePlace;

    private Integer education;
    private String educationStr;

    private String major;

    private Integer maritalStatus;
    private String maritalStatusStr;

    private String identityCard;
    private String phone;

    private Integer personnelType;
    private String personnelTypeStr;

    private Integer authorizedStrengthType;
    private String authorizedStrengthTypeStr;

    private Integer placeOfWork;
    private String placeOfWorkStr;

    private Integer jobGrade;
    private String jobGradeStr;

    private Integer treatmentGrade;
    private String treatmentGradeStr;

    private Integer enrollWay;
    private String enrollWayStr;

    private String beginWorkTime;
    private String effectiveDateOfTheContract;
    private String retirementDate;

    private Integer dimissionType;
    private String dimissionTypeStr;


    private String workUnitCode;
    private String workUnitName;

    private String organizationUnitCode;
    private String organizationUnitName;

    private Integer jobCategory;
    private String jobCategoryStr;

    private String duty;
    private String socialSecurityNumber;
    private String beginPoliceWorkTime;
    private String contractExpirationDate;
    private String dimissionDate;
    private String dimissionReason;


    private List<Experience> personalExperience;
    private List<FamilyMember> familyMember;
    private List<Award> award;
    private List<Assessment> assessment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
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

    public Integer getQuasiDrivingType() {
        return quasiDrivingType;
    }

    public void setQuasiDrivingType(Integer quasiDrivingType) {
        this.quasiDrivingType = quasiDrivingType;
    }

    public String getQuasiDrivingTypeStr() {
        return quasiDrivingTypeStr;
    }

    public void setQuasiDrivingTypeStr(String quasiDrivingTypeStr) {
        this.quasiDrivingTypeStr = quasiDrivingTypeStr;
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

    public String getExservicemanStr() {
        return exservicemanStr;
    }

    public void setExservicemanStr(String exservicemanStr) {
        this.exservicemanStr = exservicemanStr;
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

    public String getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Integer getDimissionType() {
        return dimissionType;
    }

    public void setDimissionType(Integer dimissionType) {
        this.dimissionType = dimissionType;
    }

    public String getDimissionTypeStr() {
        return dimissionTypeStr;
    }

    public void setDimissionTypeStr(String dimissionTypeStr) {
        this.dimissionTypeStr = dimissionTypeStr;
    }

    public String getWorkUnitCode() {
        return workUnitCode;
    }

    public void setWorkUnitCode(String workUnitCode) {
        this.workUnitCode = workUnitCode;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public String getOrganizationUnitCode() {
        return organizationUnitCode;
    }

    public void setOrganizationUnitCode(String organizationUnitCode) {
        this.organizationUnitCode = organizationUnitCode;
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

    public List<Experience> getPersonalExperience() {
        return personalExperience;
    }

    public void setPersonalExperience(
        List<Experience> personalExperience) {
        this.personalExperience = personalExperience;
    }

    public List<FamilyMember> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<FamilyMember> familyMember) {
        this.familyMember = familyMember;
    }

    public List<Award> getAward() {
        return award;
    }

    public void setAward(List<Award> award) {
        this.award = award;
    }

    public List<Assessment> getAssessment() {
        return assessment;
    }

    public void setAssessment(List<Assessment> assessment) {
        this.assessment = assessment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class Experience {
        private String timeStart;
        private String timeEnd;
        private String unit;
        private String department;
        private String duty;

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }
    }

    public static class FamilyMember {
        private String name;
        private String relation;
        private String company;
        private String duty;
        private String identityCard;
        private String phone;
        private Integer politicalLandscapeCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
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

        public Integer getPoliticalLandscapeCode() {
            return politicalLandscapeCode;
        }

        public void setPoliticalLandscapeCode(Integer politicalLandscapeCode) {
            this.politicalLandscapeCode = politicalLandscapeCode;
        }
    }

    public static class Award {
        private String name;
        private String time;
        private String reason;
        private String company;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }

    public static class Assessment{
        private String time;
        private String grade;
        private String remark;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
