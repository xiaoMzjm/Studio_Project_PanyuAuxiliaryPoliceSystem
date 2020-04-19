package com.base.biz.user.server.service;

import java.util.*;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.common.Enums;
import com.base.biz.user.client.common.Enums.AuthorizedStrengthTypeEnum;
import com.base.biz.user.client.common.Enums.DimssionTypeEnum;
import com.base.biz.user.client.common.Enums.DrivingTypeEnum;
import com.base.biz.user.client.common.Enums.EducationEnum;
import com.base.biz.user.client.common.Enums.EnrollWayEnum;
import com.base.biz.user.client.common.Enums.ExservicemanEnum;
import com.base.biz.user.client.common.Enums.JobCategoryEnum;
import com.base.biz.user.client.common.Enums.JobGradeEnum;
import com.base.biz.user.client.common.Enums.MaritalStatusEnum;
import com.base.biz.user.client.common.Enums.NationEnum;
import com.base.biz.user.client.common.Enums.PersonnelTypeEnum;
import com.base.biz.user.client.common.Enums.PlaceOfWorkEnum;
import com.base.biz.user.client.common.Enums.PoliticalLandscapeEnum;
import com.base.biz.user.client.common.Enums.SexEnum;
import com.base.biz.user.client.common.Enums.SpecialPeopleEnum;
import com.base.biz.user.client.common.Enums.TreatmentGradeEnum;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyService;
import com.base.resource.client.model.ResourceVO;
import com.base.resource.client.service.ResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/7 2:44 AM
 */

@Service
public class BizUserAddUserCheckService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ResourceService resourceService;


    public void check(BizUserAddParam param) throws BaseException {
        if (StringUtils.isEmpty(param.identityCard)) {
            throw new BaseException(String.format("身份证信息不能为空"));
        }
        if (StringUtils.isEmpty(param.name)) {
            throw new BaseException(String.format("姓名不能为空"));
        }
        // 姓名
        if (StringUtils.isNotEmpty(param.name)) {
            if (param.name.length() > 64) {
                throw new BaseException(String.format("姓名[%s]长度不能超过64个字符",param.name));
            }
        }
        // 头像
        if (StringUtils.isNotEmpty(param.headPicCode)) {
            Map<String,ResourceVO> resourceVOMap = resourceService.findByNameList(Lists.newArrayList(param.headPicCode));
            if(resourceVOMap == null || resourceVOMap.get(param.headPicCode) == null) {
                throw new BaseException(String.format("头像Code不存在[%s]",param.headPicCode));
            }
        }

        // 生日
        if (StringUtils.isNotEmpty(param.birthdate)) {
            Date birthday = DateUtil.convert2Date(param.birthdate, BizUserConstant.DateFormat);
            if (birthday == null) {
                throw new BaseException(String.format("生日[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 民族
        if (param.nation != null) {
            NationEnum e = NationEnum.get(param.nation);
            if (e == null) {
                throw new BaseException(String.format("民族[%s]格式错误，正确格式范围为[%s]",param.nation, NationEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.nationStr)) {
            NationEnum e = NationEnum.get(param.nationStr);
            if (e == null) {
                throw new BaseException(String.format("民族[%s]格式错误，正确格式范围为[%s]",param.nationStr, NationEnum.getAllName()));
            }
            param.nation = e.getCode();
        }
        // 政治面貌
        if (param.politicalLandscape != null) {
            PoliticalLandscapeEnum e = PoliticalLandscapeEnum.get(param.politicalLandscape);
            if (e == null) {
                throw new BaseException(String.format("政治面貌[%s]格式错误，正确格式范围为[%s]",param.politicalLandscape, PoliticalLandscapeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.politicalLandscapeStr)) {
            PoliticalLandscapeEnum e = PoliticalLandscapeEnum.get(param.politicalLandscapeStr);
            if (e == null) {
                throw new BaseException(String.format("政治面貌[%s]格式错误，正确格式范围为[%s]",param.politicalLandscape, PoliticalLandscapeEnum.getAllName()));
            }
            param.politicalLandscape = e.getCode();
        }
        // 毕业院校
        if (StringUtils.isNotEmpty(param.graduateInstitutions)) {
            if (param.graduateInstitutions.length() > 128) {
                throw new BaseException(String.format("毕业院校[%s]长度不能超过128个字符",param.graduateInstitutions));
            }
        }
        // 警号
        if (StringUtils.isNotEmpty(param.policeCode)) {
            if (param.policeCode.length() > 64) {
                throw new BaseException(String.format("警号[%s]长度不能超过64个字符",param.policeCode));
            }
        }
        // 准驾车型
        if (param.quasiDrivingType != null) {
            String[] quasiDrivingTypeArray = param.quasiDrivingType.split(",");
            for(String quasiDrivingType : quasiDrivingTypeArray) {
                DrivingTypeEnum e = DrivingTypeEnum.get(Integer.valueOf(quasiDrivingType));
                if (e == null) {
                    throw new BaseException(String.format("准驾车型[%s]格式错误，正确格式范围为[%s]",Integer.valueOf(quasiDrivingType), DrivingTypeEnum.getAllCode()));
                }
            }
        }
        if (StringUtils.isNotEmpty(param.quasiDrivingTypeStr)) {
            StringBuilder sb = new StringBuilder();
            String[] quasiDrivingTypeStrArray = param.quasiDrivingTypeStr.split(",");
            for(String quasiDrivingTypeStr : quasiDrivingTypeStrArray) {
                DrivingTypeEnum e = DrivingTypeEnum.get(quasiDrivingTypeStr);
                if (e == null) {
                    throw new BaseException(String.format("准驾车型[%s]格式错误，正确格式范围为[%s]",quasiDrivingTypeStr, DrivingTypeEnum.getAllName()));
                }
                sb.append(e.getCode()).append(",");
            }
            String s = sb.toString();
            if(s.endsWith(",")) {
                s = s.substring(0, s.length()-1);
            }
            param.quasiDrivingType = s;
        }
        // 特殊人员
        if (StringUtils.isNotEmpty(param.specialPeople)) {
            String[] specialPeopleArray = param.specialPeople.split(",");
            for(String specialPeople : specialPeopleArray) {
                SpecialPeopleEnum e = SpecialPeopleEnum.get(Integer.valueOf(specialPeople));
                if (e == null) {
                    throw new BaseException(String.format("特殊人员[%s]格式错误，正确格式范围为[%s]",Integer.valueOf(specialPeople), SpecialPeopleEnum.getAllCode()));
                }
            }
        }
        if (StringUtils.isNotEmpty(param.specialPeopleStr)) {
            String[] specialPeopleStrArray = param.specialPeopleStr.split(",");
            StringBuilder sb = new StringBuilder();
            for(String specialPeopleStr : specialPeopleStrArray){
                SpecialPeopleEnum e = SpecialPeopleEnum.get(specialPeopleStr);
                if (e == null) {
                    throw new BaseException(String.format("特殊人员[%s]格式错误，正确格式范围为[%s]",specialPeopleStr, SpecialPeopleEnum.getAllName()));
                }
                sb.append(e.getCode()).append(",");
            }
            String s = sb.toString();
            if(s.endsWith(",")) {
                s = s.substring(0, s.length()-1);
            }
            param.specialPeople = s;
        }
        // 户籍地址
        if (StringUtils.isNotEmpty(param.permanentResidenceAddress)) {
            if (param.permanentResidenceAddress.length() > 512) {
                throw new BaseException(String.format("户籍[%s]长度不能超过512个字符",param.permanentResidenceAddress));
            }
        }
        // 家庭地址
        if (StringUtils.isNotEmpty(param.familyAddress)) {
            if (param.familyAddress.length() > 512) {
                throw new BaseException(String.format("家庭地址[%s]长度不能超过512个字符",param.familyAddress));
            }
        }
        // 性别
        if (param.sex != null) {
            SexEnum e = SexEnum.get(param.sex);
            if (e == null) {
                throw new BaseException(String.format("性别[%s]格式错误，正确格式范围为[%s]",param.sex, SexEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.sexStr)) {
            SexEnum e = SexEnum.get(param.sexStr);
            if (e == null) {
                throw new BaseException(String.format("性别[%s]格式错误，正确格式范围为[%s]",param.sexStr, SexEnum.getAllName()));
            }
            param.sex = e.getCode();
        }
        // 籍贯
        if (StringUtils.isNotEmpty(param.nativePlace)) {
            if (param.nativePlace.length() > 512) {
                throw new BaseException(String.format("籍贯[%s]长度不能超过512个字符",param.nativePlace));
            }
        }
        // 学历
        if (param.education != null) {
            EducationEnum e = EducationEnum.get(param.education);
            if (e == null) {
                throw new BaseException(String.format("学历[%s]格式错误，正确格式范围为[%s]", param.education, EducationEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.educationStr)) {
            EducationEnum e = EducationEnum.get(param.educationStr);
            if (e == null) {
                throw new BaseException(String.format("学历[%s]格式错误，正确格式范围为[%s]", param.educationStr, EducationEnum.getAllName()));
            }
            param.education = e.getCode();
        }
        // 专业
        if (StringUtils.isNotEmpty(param.major)) {
            if (param.major.length() > 128) {
                throw new BaseException(String.format("专业[%s]长度不能超过128个字符",param.major));
            }
        }
        // 婚姻状况
        if (param.maritalStatus != null) {
            MaritalStatusEnum e = MaritalStatusEnum.get(param.maritalStatus);
            if (e == null) {
                throw new BaseException(String.format("婚姻状况[%s]格式错误。正确格式范围为[%s]", param.maritalStatus, MaritalStatusEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.maritalStatusStr)) {
            MaritalStatusEnum e = MaritalStatusEnum.get(param.maritalStatusStr);
            if (e == null) {
                throw new BaseException(String.format("婚姻状况[%s]格式错误。正确格式范围为[%s]", param.maritalStatusStr, MaritalStatusEnum.getAllName()));
            }
            param.maritalStatus = e.getCode();
        }
        // 身份证
        if (StringUtils.isNotEmpty(param.identityCard)) {
            if (param.identityCard.length() > 64) {
                throw new BaseException(String.format("身份证[%s]长度不能超过64个字符",param.identityCard));
            }
        }
        // 手机号码
        if (StringUtils.isNotEmpty(param.phone)) {
            if (param.phone.length() > 64) {
                throw new BaseException(String.format("手机[%s]长度不能超过64个字符",param.phone));
            }
        }
        // 人员类别
        if (param.personnelType != null) {
            PersonnelTypeEnum e = PersonnelTypeEnum.get(param.personnelType);
            if (e == null) {
                throw new BaseException(String.format("人员类别[%s]格式错误。正确格式范围为[%s]", param.personnelType, PersonnelTypeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.personnelTypeStr)) {
            PersonnelTypeEnum e = PersonnelTypeEnum.get(param.personnelTypeStr);
            if (e == null) {
                throw new BaseException(String.format("人员类别[%s]格式错误。正确格式范围为[%s]", param.personnelTypeStr, PersonnelTypeEnum.getAllName()));
            }
            param.personnelType = e.getCode();
        }
        // 编制类型
        if (param.authorizedStrengthType != null) {
            AuthorizedStrengthTypeEnum e = AuthorizedStrengthTypeEnum.get(param.authorizedStrengthType);
            if (e == null) {
                throw new BaseException(String.format("编制类型[%s]格式错误。正确格式范围为[%s]", param.authorizedStrengthType, AuthorizedStrengthTypeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.authorizedStrengthTypeStr)){
            AuthorizedStrengthTypeEnum e = AuthorizedStrengthTypeEnum.get(param.authorizedStrengthTypeStr);
            if (e == null) {
                throw new BaseException(String.format("编制类型[%s]格式错误。正确格式范围为[%s]", param.authorizedStrengthTypeStr, AuthorizedStrengthTypeEnum.getAllName()));
            }
            param.authorizedStrengthType = e.getCode();
        }
        // 工作岗位
        if (param.placeOfWork != null) {
            PlaceOfWorkEnum e = PlaceOfWorkEnum.get(param.placeOfWork);
            if (e == null) {
                throw new BaseException(String.format("工作岗位[%s]格式错误。正确格式范围为[%s]", param.placeOfWork, PlaceOfWorkEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.placeOfWorkStr)) {
            PlaceOfWorkEnum e = PlaceOfWorkEnum.get(param.placeOfWorkStr);
            if (e == null) {
                throw new BaseException(String.format("工作岗位[%s]格式错误。正确格式范围为[%s]", param.placeOfWorkStr, PlaceOfWorkEnum.getAllName()));
            }
            param.placeOfWork = e.getCode();
        }
        // 职级
        if (param.jobGrade != null) {
            JobGradeEnum e = JobGradeEnum.get(param.jobGrade);
            if (e == null) {
                throw new BaseException(String.format("职级[%s]格式错误。正确格式范围为[%s]", param.jobGrade, JobGradeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.jobGradeStr)) {
            JobGradeEnum e = JobGradeEnum.get(param.jobGradeStr);
            if (e == null) {
                throw new BaseException(String.format("职级[%s]格式错误。正确格式范围为[%s]", param.jobGradeStr, JobGradeEnum.getAllName()));
            }
            param.jobGrade = e.getCode();
        }
        // 待遇级别
        if (param.treatmentGrade != null) {
            TreatmentGradeEnum e = TreatmentGradeEnum.get(param.treatmentGrade);
            if (e == null) {
                throw new BaseException(String.format("待遇级别[%s]格式错误。正确格式范围为[%s]", param.treatmentGrade, TreatmentGradeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.treatmentGradeStr)) {
            TreatmentGradeEnum e = TreatmentGradeEnum.get(param.treatmentGradeStr);
            if (e == null) {
                throw new BaseException(String.format("待遇级别[%s]格式错误。正确格式范围为[%s]", param.treatmentGradeStr, TreatmentGradeEnum.getAllName()));
            }
            param.treatmentGrade = e.getCode();
        }
        // 招录方式
        if (param.enrollWay != null) {
            EnrollWayEnum e = EnrollWayEnum.get(param.enrollWay);
            if (e == null) {
                throw new BaseException(String.format("招录方式[%s]格式错误。正确格式范围为[%s]", param.enrollWay, EnrollWayEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.enrollWayStr)) {
            EnrollWayEnum e = EnrollWayEnum.get(param.enrollWayStr);
            if (e == null) {
                throw new BaseException(String.format("招录方式[%s]格式错误。正确格式范围为[%s]", param.enrollWayStr, EnrollWayEnum.getAllName()));
            }
            param.enrollWay = e.getCode();
        }
        // 参加工作时间
        if (StringUtils.isNotEmpty(param.beginWorkTime)) {
            Date date = DateUtil.convert2Date(param.beginWorkTime, BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("参加工作时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 合同生效时间
        if (StringUtils.isNotEmpty(param.effectiveDateOfTheContract)) {
            Date date = DateUtil.convert2Date(param.effectiveDateOfTheContract, BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("合同生效时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 退休时间
        if (StringUtils.isNotEmpty(param.retirementDate)) {
            Date date = DateUtil.convert2Date(param.retirementDate, BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("退休时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 工作单位
        String workUnitName = "";
        if (StringUtils.isNotEmpty(param.workUnitCode)) {
            CompanyVO companyVO = companyService.findByCode(param.workUnitCode);
            if (companyVO == null) {
                throw new BaseException(String.format("工作单位[%s]不存在，请填写[单位管理]模块中存在的单位",param.workUnitCode));
            }
            workUnitName = companyVO.getName();
        }
        if (StringUtils.isNotEmpty(param.workUnitName)) {
            CompanyVO companyVO = companyService.findByName(param.workUnitName);
            if (companyVO == null) {
                throw new BaseException(String.format("工作单位[%s]不存在，请填写[单位管理]模块中存在的单位",param.workUnitName));
            }
            workUnitName = companyVO.getName();
            param.workUnitCode = companyVO.getCode();
        }
        // 编制单位
        if (StringUtils.isNotEmpty(param.organizationUnitCode)) {
            CompanyVO companyVO = companyService.findByCode(param.organizationUnitCode);
            if (companyVO == null) {
                throw new BaseException(String.format("编制单位[%s]不存在，请填写[单位管理]模块中存在的单位",param.organizationUnitCode));
            }
        }
        if (StringUtils.isNotEmpty(param.organizationUnitName)) {
            CompanyVO companyVO = companyService.findByName(param.organizationUnitName);
            if (companyVO == null) {
                throw new BaseException(String.format("编制单位[%s]不存在，请填写[单位管理]模块中存在的单位",param.organizationUnitName));
            }
            param.organizationUnitCode = companyVO.getCode();
        }
        // 岗位类别
        if (param.jobCategory != null) {
            JobCategoryEnum e = JobCategoryEnum.get(param.jobCategory);
            if (e == null) {
                throw new BaseException(String.format("岗位类别[%s]格式错误，正确格式为[%s]",param.jobCategory, JobCategoryEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.jobCategoryStr)) {
            JobCategoryEnum e = JobCategoryEnum.get(param.jobCategoryStr);
            if (e == null) {
                throw new BaseException(String.format("岗位类别[%s]格式错误，正确格式为[%s]",param.jobCategoryStr, JobCategoryEnum.getAllName()));
            }
            param.jobCategory = e.getCode();
        }
        // 职务
        if(StringUtils.isNotEmpty(param.duty)) {
            if(param.duty.length() > 128) {
                throw new BaseException(String.format("职务[%s]长度不能超过128个字符",param.duty));
            }
        }
        // 社保编码
        if(StringUtils.isNotEmpty(param.socialSecurityNumber)) {
            if(param.socialSecurityNumber.length() > 64) {
                throw new BaseException(String.format("社保编码[%s]长度不能超过64个字符",param.socialSecurityNumber));
            }
        }
        //入职公安时间
        if (StringUtils.isNotEmpty(param.beginPoliceWorkTime)) {
            Date date = DateUtil.convert2Date(param.beginPoliceWorkTime,BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("入职公安时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.beginPoliceWorkTime));
            }
        }
        // 合同失效时间
        if (StringUtils.isNotEmpty(param.contractExpirationDate)) {
            Date date = DateUtil.convert2Date(param.contractExpirationDate,BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("合同失效时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.contractExpirationDate));
            }
        }
        // 离职时间
        if (StringUtils.isNotEmpty(param.dimissionDate)) {
            Date date = DateUtil.convert2Date(param.dimissionDate,BizUserConstant.DateFormat);
            if (date == null) {
                throw new BaseException(String.format("离职时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.dimissionDate));
            }
        }
        // 离职原因
        if(StringUtils.isNotEmpty(param.dimissionReason)) {
            if(param.dimissionReason.length() > 512) {
                throw new BaseException(String.format("离职原因[%s]长度不能超过512个字符",param.dimissionReason));
            }
        }
        // 工作经历
        if(CollectionUtils.isNotEmpty(param.personalExperience)) {
            for(BizUserAddParam.AddParamExperience addParamExperience : param.personalExperience) {
                // 起始日期
                if(StringUtils.isNotEmpty(addParamExperience.timeStart)) {
                    Date date = DateUtil.convert2Date(addParamExperience.timeStart,BizUserConstant.DateFormat);
                    if (date == null) {
                        throw new BaseException(String.format("履历-起始日期[%s]格式错误，正确格式为：yyyy/mm/dd",addParamExperience.timeStart));
                    }
                }
                // 结束日期
                if(StringUtils.isNotEmpty(addParamExperience.timeEnd)) {
                    Date date = DateUtil.convert2Date(addParamExperience.timeEnd,BizUserConstant.DateFormat);
                    if (date == null) {
                        throw new BaseException(String.format("履历-结束日期[%s]格式错误，正确格式为：yyyy/mm/dd",addParamExperience.timeEnd));
                    }
                }
                // 单位或组织名称
                if(StringUtils.isNotEmpty(addParamExperience.unit)) {
                    if(addParamExperience.unit.length() > 128) {
                        throw new BaseException(String.format("履历-单位或组织名称[%s]长度不能超过128个字符", addParamExperience.unit));
                    }
                }
                // 部门
                if(StringUtils.isNotEmpty(addParamExperience.department)) {
                    if(addParamExperience.department.length() > 128) {
                        throw new BaseException(String.format("履历-部门[%s]长度不能超过128个字符",addParamExperience.department));
                    }
                }
                // 职务
                if(StringUtils.isNotEmpty(addParamExperience.duty)) {
                    if(addParamExperience.duty.length() > 128) {
                        throw new BaseException(String.format("履历-职务[%s]长度不能超过128个字符",addParamExperience.duty));
                    }
                }
            }
            BizUserAddParam.AddParamExperience lastAddParamExperienceparam = param.personalExperience.get(param.personalExperience.size() - 1);
            // 单位或组织名称
            if(StringUtils.isNotEmpty(lastAddParamExperienceparam.unit)) {
                // 单位或组织名称必须实际工作单位一致
                if (!lastAddParamExperienceparam.unit.equals(workUnitName)) {
                    throw new BaseException(String.format("履历-最后一行[单位或组织名称]和当前的[工作岗位]必须一致，当前工作岗位为[%s]，最后一行单位或组织名称为[%s]",workUnitName, lastAddParamExperienceparam.unit));
                }
            }
        }
        // 家庭成员
        if(CollectionUtils.isNotEmpty(param.familyMember)) {
            for(BizUserAddParam.AddParamFamilyMember addParamFamilyMember : param.familyMember) {

                // 姓名
                if(StringUtils.isNotEmpty(addParamFamilyMember.name)) {
                    if(addParamFamilyMember.name.length() > 64) {
                        throw new BaseException(String.format("家庭成员-姓名[%s]长度不能超过64个字符",addParamFamilyMember.name));
                    }
                }
                // 关系
                if(StringUtils.isNotEmpty(addParamFamilyMember.relation)) {
                    if(addParamFamilyMember.relation.length() > 64) {
                        throw new BaseException(String.format("家庭成员-关系[%s]长度不能超过64个字符",addParamFamilyMember.relation));
                    }
                }
                // 单位
                if(StringUtils.isNotEmpty(addParamFamilyMember.company)) {
                    if(addParamFamilyMember.company.length() > 64) {
                        throw new BaseException(String.format("家庭成员-单位[%s]长度不能超过64个字符",addParamFamilyMember.company));
                    }
                }
                // 职务
                if(StringUtils.isNotEmpty(addParamFamilyMember.duty)) {
                    if(addParamFamilyMember.duty.length() > 128) {
                        throw new BaseException(String.format("家庭成员-部门[%s]长度不能超过128个字符",addParamFamilyMember.duty));
                    }
                }
                // 身份证
                if (StringUtils.isNotEmpty(addParamFamilyMember.identityCard)) {
                    if (addParamFamilyMember.identityCard.length() > 64) {
                        throw new BaseException(String.format("家庭成员-身份证[%s]长度不能超过64个字符",addParamFamilyMember.identityCard));
                    }
                }
                // 电话
                if (StringUtils.isNotEmpty(addParamFamilyMember.phone)) {
                    if (addParamFamilyMember.phone.length() > 64) {
                        throw new BaseException(String.format("家庭成员-手机[%s]长度不能超过64个字符",addParamFamilyMember.phone));
                    }
                }
                // 政治身份
                if (addParamFamilyMember.politicalLandscapeCode != null) {
                    PoliticalLandscapeEnum e = PoliticalLandscapeEnum.get(addParamFamilyMember.politicalLandscapeCode);
                    if (e == null) {
                        throw new BaseException(String.format("家庭成员-政治身份[%s]格式错误，正确格式范围为[%s]",addParamFamilyMember.politicalLandscapeCode, PoliticalLandscapeEnum.getAllCode()));
                    }
                }
            }
        }
        // 奖惩情况
        if(CollectionUtils.isNotEmpty(param.award)) {
            for(BizUserAddParam.AddParamAward award : param.award) {
                // 奖惩名称
                if (StringUtils.isNotEmpty(award.name)) {
                    if (award.name.length() > 128) {
                        throw new BaseException(String.format("奖惩情况-奖惩名称[%s]长度不能超过128个字符",award.name));
                    }
                }
                // 奖惩时间
                if (StringUtils.isNotEmpty(award.time)) {
                    Date date = DateUtil.convert2Date(award.time,BizUserConstant.DateFormat);
                    if (date == null) {
                        throw new BaseException(String.format("奖惩情况-奖惩时间[%s]格式错误，正确格式为：yyyy/mm/dd",award.time));
                    }
                }
                // 奖惩原因
                if (StringUtils.isNotEmpty(award.reason)) {
                    if (award.reason.length() > 512) {
                        throw new BaseException(String.format("奖惩情况-奖惩原因[%s]长度不能超过512个字符",award.name));
                    }
                }
                // 批准单位
                if (StringUtils.isNotEmpty(award.company)) {
                    if (award.company.length() > 512) {
                        throw new BaseException(String.format("奖惩情况-批准单位[%s]长度不能超过512个字符",award.company));
                    }
                }
            }
        }
        // 考核情况
        if(CollectionUtils.isNotEmpty(param.assessment)){
            for(BizUserAddParam.AddParamAssessment addParamAssessment : param.assessment) {

                // 发生时间
                if (StringUtils.isNotEmpty(addParamAssessment.time)) {
                    Date date = DateUtil.convert2Date(addParamAssessment.time,BizUserConstant.DateYYYYFormat);
                    if (date == null) {
                        throw new BaseException(String.format("考核情况-发生时间[%s]格式错误，正确格式为：yyyy",addParamAssessment.time));
                    }
                }
                // 等级
                if (StringUtils.isNotEmpty(addParamAssessment.grade)) {
                    if (addParamAssessment.grade.length() > 64) {
                        throw new BaseException(String.format("考核情况-等级[%s]长度不能超过64个字符",addParamAssessment.grade));
                    }
                }
                // 备注
                if (StringUtils.isNotEmpty(addParamAssessment.remark)) {
                    if (addParamAssessment.remark.length() > 64) {
                        throw new BaseException(String.format("考核情况-备注[%s]长度不能超过512个字符",addParamAssessment.remark));
                    }
                }
            }
        }
    }
}
