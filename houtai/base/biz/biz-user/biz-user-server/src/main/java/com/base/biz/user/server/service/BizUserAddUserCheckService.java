package com.base.biz.user.server.service;

import java.util.Date;

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
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/7 2:44 AM
 */

@Service
public class BizUserAddUserCheckService {

    public static void check(BizUserAddParam param) throws BaseException {
        // 姓名
        if (StringUtils.isNotEmpty(param.name)) {
            if (param.name.length() > 64) {
                throw new BaseException(String.format("姓名[%s]长度不能超过64个字符",param.name));
            }
        }
        // 生日
        if (StringUtils.isNotEmpty(param.birthdate)) {
            Date birthday = DateUtil.convert2Date(param.birthdate, "yyyy/mm/dd");
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
            DrivingTypeEnum e = DrivingTypeEnum.get(param.quasiDrivingType);
            if (e == null) {
                throw new BaseException(String.format("准驾车型[%s]格式错误，正确格式范围为[%s]",param.quasiDrivingType, DrivingTypeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.quasiDrivingTypeStr)) {
            DrivingTypeEnum e = DrivingTypeEnum.get(param.quasiDrivingTypeStr);
            if (e == null) {
                throw new BaseException(String.format("准驾车型[%s]格式错误，正确格式范围为[%s]",param.quasiDrivingTypeStr, DrivingTypeEnum.getAllName()));
            }
        }
        // 特长
        if (StringUtils.isNotEmpty(param.speciality)) {
            if (param.speciality.length() > 4096) {
                throw new BaseException(String.format("特长[%s]长度不能超过4096个字符",param.speciality));
            }
        }
        // 是否退役军人
        if (param.exserviceman != null) {
            ExservicemanEnum e = ExservicemanEnum.get(param.exserviceman);
            if (e == null) {
                throw new BaseException(String.format("是否退役军人[%s]格式错误，正确格式范围为[%s]",param.exserviceman, ExservicemanEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.exservicemanStr)) {
            ExservicemanEnum e = ExservicemanEnum.get(param.exservicemanStr);
            if (e == null) {
                throw new BaseException(String.format("是否退役军人[%s]格式错误，正确格式范围为[%s]",param.exservicemanStr, ExservicemanEnum.getAllName()));
            }
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
        }
        // 年龄
        if (param.age != null) {
            if (param.age < 0 || param.age >= 100) {
                throw new BaseException(String.format("年龄[%s]格式错误，应该大于0，小于100",param.age));
            }
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
        }
        // 参加工作时间
        if (StringUtils.isNotEmpty(param.beginWorkTime)) {
            Date date = DateUtil.convert2Date(param.beginWorkTime, "yyyy/mm/dd");
            if (date == null) {
                throw new BaseException(String.format("参加工作时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 合同生效时间
        if (StringUtils.isNotEmpty(param.effectiveDateOfTheContract)) {
            Date date = DateUtil.convert2Date(param.effectiveDateOfTheContract, "yyyy/mm/dd");
            if (date == null) {
                throw new BaseException(String.format("合同生效时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 退休时间
        if (StringUtils.isNotEmpty(param.retirementDate)) {
            Date date = DateUtil.convert2Date(param.retirementDate, "yyyy/mm/dd");
            if (date == null) {
                throw new BaseException(String.format("退休时间[%s]格式错误，正确格式为：yyyy/mm/dd",param.birthdate));
            }
        }
        // 离职类别
        if (param.dimissionType != null) {
            DimssionTypeEnum e = DimssionTypeEnum.get(param.dimissionType);
            if (e == null) {
                throw new BaseException(String.format("离职类别[%s]格式错误，正确格式为[%s]",param.dimissionType, DimssionTypeEnum.getAllCode()));
            }
        }
        if (StringUtils.isNotEmpty(param.dimissionTypeStr)) {
            DimssionTypeEnum e = DimssionTypeEnum.get(param.dimissionTypeStr);
            if (e == null) {
                throw new BaseException(String.format("离职类别[%s]格式错误，正确格式为[%s]",param.dimissionTypeStr, DimssionTypeEnum.getAllName()));
            }
        }
        // 工作单位
        if (StringUtils.isNotEmpty(param.workUnitCode)) {

        }

    }
}
