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

    @ApiParam(name="准驾车型",value="quasiDrivingType")
    public String quasiDrivingType;
    @ApiParam(name="准驾车型",value="quasiDrivingTypeStr")
    public String quasiDrivingTypeStr;

    @ApiParam(name="特长",value="speciality")
    public String speciality;

    @ApiParam(name="特殊人员",value="specialPeople")
    public String specialPeople;
    @ApiParam(name="特殊人员",value="specialPeopleStr")
    public String specialPeopleStr;

    @ApiParam(name="资格证书",value="qualification")
    public String qualification;

    @ApiParam(name="是否退役军人",value="exserviceman")
    public Integer exserviceman;
    @ApiParam(name="是否退役军人",value="exservicemanStr")
    public String exservicemanStr;

    @ApiParam(name="户籍地址",value="permanentResidenceAddress")
    public String permanentResidenceAddress;
    @ApiParam(name="家庭地址",value="familyAddress")
    public String familyAddress;

    @ApiParam(name="性别",value="sex")
    public Integer sex;
    @ApiParam(name="性别",value="sexStr")
    public String sexStr;

    @ApiParam(name="年龄",value="age")
    public Integer age;
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
    @ApiParam(name="退休时间",value="retirementDate")
    public String retirementDate;

    @ApiParam(name="离职类别",value="dimissionType")
    public Integer dimissionType;
    @ApiParam(name="离职类别",value="dimissionTypeStr")
    public String dimissionTypeStr;


    @ApiParam(name="工作单位",value="workUnitCode")
    public String workUnitCode;
    @ApiParam(name="工作单位",value="workUnitName")
    public String workUnitName;

    @ApiParam(name="编制单位",value="organizationUnitCode")
    public String organizationUnitCode;
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
        @ApiParam(name="政治面貌",value="politicalLandscapeCode")
        public Integer politicalLandscapeCode;
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
}
