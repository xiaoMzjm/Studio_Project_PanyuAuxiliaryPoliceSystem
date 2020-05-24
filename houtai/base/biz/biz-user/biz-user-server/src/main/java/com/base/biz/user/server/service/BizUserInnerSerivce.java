package com.base.biz.user.server.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.common.Enums.AuthorizedStrengthTypeEnum;
import com.base.biz.user.client.common.Enums.DimssionTypeEnum;
import com.base.biz.user.client.common.Enums.DrivingTypeEnum;
import com.base.biz.user.client.common.Enums.DueContractEnum;
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
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.model.BizUserDetailVO.Experience;
import com.base.biz.user.client.model.BizUserDetailVO.FamilyMember;
import com.base.biz.user.client.model.BizUserLoginVO;
import com.base.biz.user.client.model.BizUserPageListVO;
import com.base.biz.user.server.common.BizUserAddExcelReader;
import com.base.biz.user.server.common.ZipUtil;
import com.base.biz.user.server.manager.AssessmentManager;
import com.base.biz.user.server.manager.AwardManager;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.biz.user.server.manager.FamilyMemberManager;
import com.base.biz.user.server.manager.PersonalExperienceManager;
import com.base.biz.user.server.model.AssessmentDTO;
import com.base.biz.user.server.model.AwardDTO;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.biz.user.server.model.BizUserConvertor;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.biz.user.server.model.FamilyMemberDTO;
import com.base.biz.user.server.model.PersonalExperienceDTO;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.model.UpdateParam;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.VerifyUtil;
import com.base.common.util.WordUtil;
import com.base.common.util.WordUtil.*;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyService;
import com.base.resource.client.model.ResourceVO;
import com.base.resource.client.service.ResourceService;
import com.base.user.client.model.UserVO;
import com.base.user.client.service.UserService;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:小M
 * @date:2020/3/30 12:03 AM
 */
@Service
public class BizUserInnerSerivce {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private BizUserAddUserCheckService bizUserAddUserCheckService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonalExperienceManager personalExperienceManager;
    @Autowired
    private FamilyMemberManager familyMemberManager;
    @Autowired
    private AwardManager awardManager;
    @Autowired
    private AssessmentManager assessmentManager;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ResourceService resourceService;

    /**
     *
     * @param name
     * @param companyList
     * @return
     */
    public List<BizUserPageListVO> findByNameAndCompanyCodeList(String name, List<String> companyList) {
        List<String> companyCodes = null;
        if(org.springframework.util.CollectionUtils.isEmpty(companyList)) {
            if(CollectionUtils.isEmpty(companyCodes)) {
                List<CompanyVO> companyVOList = companyService.findAll();
                companyCodes = Lists.newArrayList();
                for(CompanyVO companyVO : companyVOList) {
                    companyCodes.add(companyVO.getCode());
                }
            }
            companyList = companyCodes;
        }
        List<BizUserDTO> bizUserDTOList = bizUserManager.findByNameAndCompany(name, companyList);
        if(CollectionUtils.isEmpty(bizUserDTOList)) {
            return Lists.newArrayList();
        }
        List<BizUserPageListVO> result = Lists.newArrayList();
        List<String> companyCodeList = Lists.newArrayList();
        for(BizUserDTO bizUserDTO : bizUserDTOList) {
            if (bizUserDTO.getCode().equals("admin")) {
                continue;
            }
            BizUserPageListVO bizUserPageListVO = new BizUserPageListVO();
            bizUserPageListVO.setCode(bizUserDTO.getCode());
            bizUserPageListVO.setName(bizUserDTO.getName());
            bizUserPageListVO.setCompanyCode(bizUserDTO.getWorkUnitCode());
            result.add(bizUserPageListVO);
            companyCodeList.add(bizUserDTO.getWorkUnitCode());
        }
        Map<String,CompanyVO> companyVOMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyCodeList)) {
            List<CompanyVO> companyVOList = companyService.findByCodeList(companyCodeList);
            if (CollectionUtils.isNotEmpty(companyVOList)) {
                for(CompanyVO companyVO : companyVOList) {
                    companyVOMap.put(companyVO.getCode(), companyVO);
                }
            }
        }
        for(BizUserPageListVO bizUserPageListVO : result) {
            CompanyVO companyVO = companyVOMap.get(bizUserPageListVO.getCompanyCode());
            if(companyVO != null) {
                bizUserPageListVO.setCompanyName(companyVO.getName());
            }
        }

        return result;
    }

    /**
     * 根据code查询详情
     * @param code
     * @return
     */
    public BizUserDetailVO findByCode(String code) throws Exception{
        if(StringUtils.isBlank(code)) {
            throw new BaseException("Code为空");
        }
        BizUserDTO dto = bizUserManager.findByCode(code);
        return dto2vo(dto);
    }

    public BizUserDetailVO dto2vo(BizUserDTO dto){
        BizUserDetailVO vo = new BizUserDetailVO();
        vo.setName(dto.getName());
        String picName = dto.getPicUrl();
        Map<String,ResourceVO> resourceVOMap = resourceService.findByNameList(Lists.newArrayList(picName));
        if(MapUtils.isNotEmpty(resourceVOMap)) {
            ResourceVO resourceVO = resourceVOMap.get(picName);
            if (resourceVO != null) {
                vo.setHeadPicUrl(resourceVO.getUrl());
                vo.setHeadPicCode(resourceVO.getName());
            }
        }
        vo.setBeginWorkTime(DateUtil.convert2String(dto.getBeginWorkTime(), BizUserConstant.DateFormat));
        vo.setCode(dto.getCode());
        vo.setBirthdate(DateUtil.convert2String(dto.getBirthdate(), BizUserConstant.DateFormat));
        vo.setNation(dto.getNation());
        vo.setNationStr(NationEnum.getName(dto.getNation()));
        vo.setPoliticalLandscape(dto.getPoliticalLandscape());
        vo.setPoliticalLandscapeStr(PoliticalLandscapeEnum.getName(dto.getPoliticalLandscape()));
        vo.setGraduateInstitutions(dto.getGraduateSchool());
        vo.setPoliceCode(dto.getPoliceCode());
        if(StringUtils.isNotEmpty(dto.getDrivingType())) {
            String[] drivingTypeStrArray = dto.getDrivingType().split(",");
            List<Integer> list = Lists.newArrayList();
            for(String drivingTypeStr : drivingTypeStrArray) {
                list.add(Integer.valueOf(drivingTypeStr));
            }
            vo.setQuasiDrivingTypeList(list);
        }
        vo.setQuasiDrivingTypeStr(DrivingTypeEnum.getName(dto.getDrivingType()));
        if(StringUtils.isNotEmpty(dto.getSpecialPeople())) {
            String[] array = dto.getSpecialPeople().split(",");
            List<Integer> list = Lists.newArrayList();
            for(String a : array) {
                list.add(Integer.valueOf(a));
            }
            vo.setSpecialPeopleList(list);
        }
        vo.setSpecialPeopleStr(SpecialPeopleEnum.getName(dto.getSpecialPeople()));
        vo.setQualification(dto.getQualification());
        vo.setSpeciality(dto.getSpeciality());
        vo.setExserviceman(dto.getExserviceman());
        vo.setExservicemanStr(ExservicemanEnum.getName(dto.getExserviceman()));
        vo.setPermanentResidenceAddress(dto.getPermanentResidenceAddress());
        vo.setFamilyAddress(dto.getFamilyAddress());
        vo.setSex(dto.getSex());
        vo.setSexStr(SexEnum.getName(dto.getSex()));
        vo.setAge(dto.getAge());
        vo.setNativePlace(dto.getNativePlace());
        vo.setEducation(dto.getEducation());
        vo.setEducationStr(EducationEnum.getName(dto.getEducation()));
        vo.setMajor(dto.getMajor());
        vo.setMaritalStatus(dto.getMaritalStatus());
        vo.setMaritalStatusStr(MaritalStatusEnum.getName(dto.getMaritalStatus()));
        vo.setIdentityCard(dto.getIdentityCard());
        vo.setPhone(dto.getPhone());
        vo.setPersonnelType(dto.getPersonnelType());
        vo.setPersonnelTypeStr(PersonnelTypeEnum.getName(dto.getPersonnelType()));
        vo.setAuthorizedStrengthType(dto.getAuthorizedStrengthType());
        vo.setAuthorizedStrengthTypeStr(AuthorizedStrengthTypeEnum.getName(dto.getAuthorizedStrengthType()));
        vo.setPlaceOfWork(dto.getPlaceOfWork());
        vo.setPlaceOfWorkStr(PlaceOfWorkEnum.getName(dto.getPlaceOfWork()));
        vo.setJobGrade(dto.getJobGrade());
        vo.setJobGradeStr(JobGradeEnum.getName(dto.getJobGrade()));
        vo.setTreatmentGrade(dto.getTreatmentGrade());
        vo.setTreatmentGradeStr(TreatmentGradeEnum.getName(dto.getTreatmentGrade()));
        vo.setEnrollWay(dto.getEnrollWay());
        vo.setEnrollWayStr(EnrollWayEnum.getName(dto.getEnrollWay()));
        vo.setBeginPoliceWorkTime(DateUtil.convert2String(dto.getBeginPoliceWorkTime(), BizUserConstant.DateFormat));
        vo.setEffectiveDateOfTheContract(DateUtil.convert2String(dto.getEffectiveDateOfTheContrace(), BizUserConstant.DateFormat));
        vo.setRetirementDate(DateUtil.convert2String(dto.getRetirementDate(), BizUserConstant.DateFormat));
        vo.setDimissionType(dto.getDimssionType());
        vo.setDimissionTypeStr(DimssionTypeEnum.getName(dto.getDimssionType()));
        vo.setWorkUnit(dto.getWorkUnitCode());
        vo.setFirstGradeTime(DateUtil.convert2String(dto.getFirstGradeTime(), BizUserConstant.DateFormat));
        vo.setWorkCardBeginTime(DateUtil.convert2String(dto.getWorkCardBeginTime(), BizUserConstant.DateFormat));
        vo.setFirstContractBeginTime(DateUtil.convert2String(dto.getFirstContractBeginTime(), BizUserConstant.DateFormat));
        vo.setFirstContractEngTime(DateUtil.convert2String(dto.getFirstContractEngTime(), BizUserConstant.DateFormat));
        vo.setSecondContractBeginTime(DateUtil.convert2String(dto.getSecondContractBeginTime(), BizUserConstant.DateFormat));
        vo.setSecondContractEngTime(DateUtil.convert2String(dto.getSecondContractEngTime(), BizUserConstant.DateFormat));
        vo.setThirdContractBeginTime(DateUtil.convert2String(dto.getThirdContractBeginTime(), BizUserConstant.DateFormat));
        vo.setThirdContractEngTime(DateUtil.convert2String(dto.getThirdContractEngTime(), BizUserConstant.DateFormat));
        vo.setDueContract(dto.getDueContract());
        vo.setDueContractStr(DueContractEnum.getName(dto.getDueContract()));
        vo.setIcbcCardAccount(dto.getIcbcCardAccount());
        vo.setRuZhiZuLinTime(DateUtil.convert2String(dto.getRuZhiZuLinTime(), BizUserConstant.DateFormat));




        List<CompanyVO> companyVOList = companyService.findByCodeList(Lists.newArrayList(dto.getWorkUnitCode()));
        if (CollectionUtils.isNotEmpty(companyVOList)) {
            CompanyVO companyVO = companyVOList.get(0);
            vo.setWorkUnitName(companyVO.getName());
        }
        vo.setOrganizationUnit(dto.getOrganizationUnitCode());
        companyVOList = companyService.findByCodeList(Lists.newArrayList(dto.getOrganizationUnitCode()));
        if (CollectionUtils.isNotEmpty(companyVOList)) {
            CompanyVO companyVO = companyVOList.get(0);
            vo.setOrganizationUnitName(companyVO.getName());
        }
        vo.setJobCategory(dto.getJobCategory());
        vo.setJobCategoryStr(JobCategoryEnum.getName(dto.getJobCategory()));
        vo.setDuty(dto.getDuty());
        vo.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        vo.setBeginPoliceWorkTime(DateUtil.convert2String(dto.getBeginPoliceWorkTime(), BizUserConstant.DateFormat));
        vo.setContractExpirationDate(DateUtil.convert2String(dto.getContractExpirationDate(), BizUserConstant.DateFormat));
        vo.setDimissionDate(DateUtil.convert2String(dto.getDimssionDate(), BizUserConstant.DateFormat));
        vo.setDimissionReason(dto.getDimssionReason());

        // 个人经历
        List<PersonalExperienceDTO> personalExperienceList = personalExperienceManager.findByUserCode(dto.getCode());
        if(CollectionUtils.isNotEmpty(personalExperienceList)) {
            List<BizUserDetailVO.Experience> personalExperiences = Lists.newArrayList();
            for(PersonalExperienceDTO personalExperienceDTO : personalExperienceList) {
                Experience experience = new Experience();
                experience.setTimeStart(DateUtil.convert2String(personalExperienceDTO.getTimeStart(), BizUserConstant.DateFormat));
                experience.setTimeEnd(DateUtil.convert2String(personalExperienceDTO.getTimeEnd(), BizUserConstant.DateFormat));
                experience.setUnit(personalExperienceDTO.getUnit());
                experience.setDepartment(personalExperienceDTO.getCompany());
                experience.setDuty(personalExperienceDTO.getDuty());
                personalExperiences.add(experience);
            }
            vo.setPersonalExperience(personalExperiences);
        }

        // 家庭成员
        List<FamilyMemberDTO> familyMemberDTOList = familyMemberManager.findByUserCode(dto.getCode());
        if(CollectionUtils.isNotEmpty(familyMemberDTOList)) {
            List<BizUserDetailVO.FamilyMember> familyMembers = Lists.newArrayList();
            for(FamilyMemberDTO familyMemberDTO : familyMemberDTOList) {
                BizUserDetailVO.FamilyMember familyMember = new BizUserDetailVO.FamilyMember();
                familyMember.setName(familyMemberDTO.getName());
                familyMember.setRelation(familyMemberDTO.getRelation());
                familyMember.setCompany(familyMemberDTO.getCompany());
                familyMember.setDuty(familyMemberDTO.getDuty());
                familyMember.setIdentityCard(familyMemberDTO.getIdentityCard());
                familyMember.setPhone(familyMemberDTO.getPhone());
                familyMember.setPoliticalLandscape(familyMemberDTO.getPoliticalLandscapeCode());
                familyMembers.add(familyMember);
            }
            vo.setFamilyMember(familyMembers);
        }

        // 奖惩
        List<AwardDTO> awardDTOList = awardManager.findByUserCode(dto.getCode());
        if(CollectionUtils.isNotEmpty(awardDTOList)) {
            List<BizUserDetailVO.Award> awardList = Lists.newArrayList();
            for(AwardDTO awardDTO : awardDTOList) {
                BizUserDetailVO.Award award = new BizUserDetailVO.Award();
                award.setName(awardDTO.getName());
                award.setCompany(awardDTO.getCompany());
                award.setReason(awardDTO.getReason());
                award.setTime(DateUtil.convert2String(awardDTO.getTime(), BizUserConstant.DateFormat));
                awardList.add(award);
            }
            vo.setAward(awardList);
        }

        // 考核
        List<AssessmentDTO> assessmentDTOList = assessmentManager.findByUserCode(dto.getCode());
        if(CollectionUtils.isNotEmpty(assessmentDTOList)) {
            List<BizUserDetailVO.Assessment> assessmentList = Lists.newArrayList();
            for(AssessmentDTO assessmentDTO : assessmentDTOList) {
                BizUserDetailVO.Assessment assessment = new BizUserDetailVO.Assessment();
                assessment.setTime(DateUtil.convert2String(assessmentDTO.getTime(), BizUserConstant.DateFormat));
                assessment.setGrade(assessmentDTO.getGrade());
                assessment.setRemark(assessmentDTO.getRemark());
                assessmentList.add(assessment);
            }
            vo.setAssessment(assessmentList);
        }

        return vo;
    }

    /**
     * 超级查询
     * @param param
     * @throws Exception
     */
    public List<BizUserPageListVO> superPageList(SuperPageListParam param) throws Exception{

        List<BizUserDTO> bizUserDTOList = bizUserManager.findBySuperParam(param);
        if(CollectionUtils.isEmpty(bizUserDTOList)) {
            return Lists.newArrayList();
        }
        List<BizUserPageListVO> result = Lists.newArrayList();
        List<String> companyCodeList = Lists.newArrayList();
        for(BizUserDTO bizUserDTO : bizUserDTOList) {
            if (bizUserDTO.getCode().equals("admin")) {
                continue;
            }
            BizUserPageListVO bizUserPageListVO = new BizUserPageListVO();
            bizUserPageListVO.setCode(bizUserDTO.getCode());
            bizUserPageListVO.setName(bizUserDTO.getName());
            bizUserPageListVO.setCompanyCode(bizUserDTO.getWorkUnitCode());
            result.add(bizUserPageListVO);
            companyCodeList.add(bizUserDTO.getWorkUnitCode());
        }
        Map<String,CompanyVO> companyVOMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyCodeList)) {
            List<CompanyVO> companyVOList = companyService.findByCodeList(companyCodeList);
            if (CollectionUtils.isNotEmpty(companyVOList)) {
                for(CompanyVO companyVO : companyVOList) {
                    companyVOMap.put(companyVO.getCode(), companyVO);
                }
            }
        }
        for(BizUserPageListVO bizUserPageListVO : result) {
            CompanyVO companyVO = companyVOMap.get(bizUserPageListVO.getCompanyCode());
            if(companyVO != null) {
                bizUserPageListVO.setCompanyName(companyVO.getName());
            }
        }

        return result;
    }

    /**
     * 登录
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public BizUserLoginVO login(String account, String password) throws Exception{

        VerifyUtil.isNotNull(account, "", "账户为空");
        VerifyUtil.isNotNull(account, "", "密码为空");

        BizUserDTO bizUserDTO = bizUserManager.findByCodeAndPassword(account, password);
        if(bizUserDTO == null) {
            bizUserDTO = bizUserManager.findByPoliceCodeAndPassword(account, password);
        }
        if(bizUserDTO == null) {
            throw new BaseException("账号或密码错误");
        }

        UserVO userVO = userService.updateToken(bizUserDTO.getCode());

        BizUserLoginVO bizUserVO = BizUserConvertor.dto2vo(bizUserDTO);

        bizUserVO.setToken(userVO.getToken());

        return bizUserVO;
    }

    /**
     * 添加人员
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public BizUserDTO add(BizUserAddParam param) throws Exception {
        bizUserAddUserCheckService.check(param);
        BizUserDTO bizUserDTO = bizUserManager.findByIdentityCard(param.identityCard);
        if (bizUserDTO != null) {
            throw new BaseException(String.format("该身份证[%s]已存在",param.identityCard));
        }
        if(StringUtils.isNotEmpty(param.policeCode)) {
            bizUserDTO = bizUserManager.findByPoliceCode(param.policeCode);
            if (bizUserDTO != null) {
                throw new BaseException(String.format("该警号[%s]已存在",param.policeCode));
            }
        }

        userService.add(param.identityCard);
        bizUserDTO = bizUserManager.add(param);
        personalExperienceManager.add(param.identityCard, param.personalExperience);
        familyMemberManager.add(param.identityCard, param.familyMember);
        awardManager.add(param.identityCard, param.award);
        assessmentManager.add(param.identityCard, param.assessment);
        return bizUserDTO;
    }

    /**
     * 修改
     * @param param
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public void update(UpdateParam param) throws Exception {
        bizUserAddUserCheckService.check(param);
        if (StringUtils.isEmpty(param.getCode())) {
            throw new BaseException("人员Code为空");
        }
        BizUserDTO dto = bizUserManager.findByCode(param.getCode());
        if(dto == null) {
            throw new BaseException(String.format("该人员不存在[%s]", param.getCode()));
        }
        deleteByCode(param.code, false);
        bizUserManager.update(param);
        personalExperienceManager.add(param.identityCard, param.personalExperience);
        familyMemberManager.add(param.identityCard, param.familyMember);
        awardManager.add(param.identityCard, param.award);
        assessmentManager.add(param.identityCard, param.assessment);
    }

    /**
     * 删除人员
     * @param code
     */
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByCode(String code, boolean deleteUser) throws Exception{
        BizUserDTO dto = bizUserManager.findByCode(code);
        if(dto == null) {
            throw new BaseException(String.format("该人员不存在[%s]", code));
        }
        if(deleteUser) {
            bizUserManager.deleteByCode(code);
            userService.deleteByCode(code);
        }
        personalExperienceManager.deleteByUserCode(code);
        familyMemberManager.deleteByUserCode(code);
        awardManager.deleteByUserCode(code);
        assessmentManager.deleteByUserCode(code);
    }

    /**
     * 导入人员
     * @param inputStream
     */
    public void importUser(InputStream inputStream)throws Exception {
        List<BizUserAddParam> bizUserAddParamList = BizUserAddExcelReader.readExcel(inputStream);
        if(CollectionUtils.isNotEmpty(bizUserAddParamList)) {
            for(BizUserAddParam bizUserAddParam : bizUserAddParamList) {
                BizUserDTO bizUserDTO = bizUserManager.findByIdentityCard(bizUserAddParam.identityCard);
                if (bizUserDTO != null) {
                    throw new BaseException(String.format("该身份证[%s]已存在",bizUserAddParam.identityCard));
                }
                if(StringUtils.isNotEmpty(bizUserAddParam.policeCode)) {
                    bizUserDTO = bizUserManager.findByPoliceCode(bizUserAddParam.policeCode);
                    if (bizUserDTO != null) {
                        throw new BaseException(String.format("该身份证[%s]已存在",bizUserAddParam.identityCard));
                    }
                }
                bizUserAddUserCheckService.check(bizUserAddParam);
                userService.add(bizUserAddParam.identityCard);
                bizUserManager.add(bizUserAddParam);
            }
        }
    }

    /**
     * 导入相片
     * @param file
     * @throws Exception
     */
    public List<String> importImage(File file) throws Exception {
        if(StringUtils.isEmpty(diskStaticUrl)) {
            throw new BaseException("diskStaticUrl is null");
        }
        Map<String,String> imageCodeMap = ZipUtil.parseZipAndSaveImage(file, diskStaticUrl + "images/");
        if(MapUtils.isEmpty(imageCodeMap)) {
            throw new BaseException("该zip包中没有图片");
        }
        List<String> identityCardList = Lists.newArrayList();
        Map<String,String> identityCard2ImageCode = new HashMap<>();
        for(String oldName : imageCodeMap.keySet()) {
            String newName = imageCodeMap.get(oldName);
            String identityCard = oldName.substring(0,oldName.lastIndexOf("."));
            String imageCode = newName.substring(0,newName.lastIndexOf("."));
            identityCardList.add(identityCard);
            identityCard2ImageCode.put(identityCard, imageCode);
        }

        // 查询身份信息
        List<BizUserDTO> bizUserDTOList = bizUserManager.findByIdentityCardList(identityCardList);
        if(CollectionUtils.isEmpty(bizUserDTOList)) {
            throw new BaseException(String.format("经识别，zip中图片的身份证错误，找不到任何人员信息。身份证为：[%s]" , bizUserDTOList.toString()));
        }

        Map<String,BizUserDTO> identityCard2BizUserDTOMap = bizUserDTOList.stream().collect(Collectors.toMap(BizUserDTO::getIdentityCard, Function
            .identity()));

        // 更新
        List<String> successIdentityCardList = Lists.newArrayList();
        for(String identityCard : identityCard2BizUserDTOMap.keySet()) {
            String imageCode = identityCard2ImageCode.get(identityCard);
            BizUserDTO bizUserDTO = identityCard2BizUserDTOMap.get(identityCard);
            bizUserManager.updateImage(bizUserDTO.getCode(), imageCode);
            resourceService.add("/static/images", imageCode, "png", imageCode);
            successIdentityCardList.add(identityCard);
        }

        return successIdentityCardList;
    }

    //

    /**
     * 导出人员
     * @param code
     * @param inputStream
     * @return
     * @throws Exception
     */
    public File exportUser(String code, InputStream inputStream) throws Exception{
        if(StringUtils.isEmpty(code)) {
            throw new BaseException("code is null");
        }
        BizUserDetailVO vo = findByCode(code);
        if(vo == null) {
            throw new BaseException("找不到该人员，请重试");
        }
        if(StringUtils.isEmpty(diskStaticUrl)) {
            throw new BaseException("diskStaticUrl is null");
        }

        Map<String,Object> rules = new HashMap<>();
        rules.put("${name}",new TextDTO(vo.getName(),true));
        rules.put("${sex}",new TextDTO(vo.getSexStr(),true));
        rules.put("${birthday}",new TextDTO(vo.getBirthdate(),true));
        if(vo.getAge() != null) {
            rules.put("${age}",new TextDTO(String.valueOf(vo.getAge()),true));
        }else {
            rules.put("${age}",new TextDTO("",true));
        }
        rules.put("${nation}",new TextDTO(vo.getNationStr(),true));
        rules.put("${nativePlace}",new TextDTO(vo.getNativePlace(),true));
        rules.put("${politicalLandscape}",new TextDTO(vo.getPoliticalLandscapeStr(),true));
        rules.put("${education}",new TextDTO(vo.getEducationStr(),true));
        rules.put("${graducateInstitutions}",new TextDTO(vo.getGraduateInstitutions(),true));
        rules.put("${major}",new TextDTO(vo.getMajor(),true));
        rules.put("${quasiDrivingType}",new TextDTO(vo.getQuasiDrivingTypeStr(),true));
        rules.put("${maritalStatus}",new TextDTO(vo.getMaritalStatusStr(),true));
        rules.put("${identityCard}",new TextDTO(vo.getIdentityCard(),true));
        rules.put("${phone}",new TextDTO(vo.getPhone(),true));
        rules.put("${permanentResidenceAddress}",new TextDTO(vo.getPermanentResidenceAddress(),true));
        rules.put("${familyAddress}",new TextDTO(vo.getFamilyAddress(),true));
        if(StringUtils.isNotEmpty(vo.getHeadPicUrl())) {
            PicDTO picDTO = new PicDTO();
            picDTO.setFile(new File(diskStaticUrl + "images/" + vo.getHeadPicCode() + ".png"));
            picDTO.setWidth(150);
            picDTO.setHeight(210);
            picDTO.setFileName(vo.getHeadPicCode());
            rules.put("${headPic}",picDTO);
        }else {
            rules.put("${headPic}",new PicDTO());
        }
        rules.put("${personalType}",new TextDTO(vo.getPersonnelTypeStr(),true));
        rules.put("${organizationUnitCode}",new TextDTO(vo.getOrganizationUnitName(),true));
        rules.put("${authorizedStrengthType}",new TextDTO(vo.getAuthorizedStrengthTypeStr(),true));
        rules.put("${jobCategory}",new TextDTO(vo.getJobCategoryStr(),true));
        rules.put("${placeOfwork}",new TextDTO(vo.getPlaceOfWorkStr(),true));
        rules.put("${duty}",new TextDTO(vo.getDuty(),true));
        rules.put("${jobGrade}",new TextDTO(vo.getJobGradeStr(),true));
        rules.put("${enrollWay}",new TextDTO(vo.getEnrollWayStr(),true));
        rules.put("${socialSecurityNumber}",new TextDTO(vo.getSocialSecurityNumber(),true));
        rules.put("${beginWorkTime}",new TextDTO(vo.getBeginWorkTime(),true));
        rules.put("${beginPoliceWorkTime}",new TextDTO(vo.getBeginPoliceWorkTime(),true));
        rules.put("${effectiveDateOfContract}",new TextDTO(vo.getEffectiveDateOfTheContract(),true));
        rules.put("${contractExpirationDate}",new TextDTO(vo.getContractExpirationDate(),true));
        rules.put("${retirementDate}",new TextDTO(vo.getRetirementDate(),true));
        rules.put("${dimssionDate}",new TextDTO(vo.getDimissionDate(),true));
        rules.put("${dimissionReason}",new TextDTO(vo.getDimissionReason(),true));
        // 履历
        if(CollectionUtils.isEmpty(vo.getPersonalExperience())) {
            rules.put("${lvliRow}",new RowDTO());
        }else {
            List<List<RowCellDTO>> list = Lists.newArrayList();
            for(Experience experience : vo.getPersonalExperience()) {
                List<RowCellDTO> row = Lists.newArrayList();
                row.add(new RowCellDTO(experience.getTimeStart(),2500));
                row.add(new RowCellDTO(experience.getTimeEnd(),2500));
                row.add(new RowCellDTO(experience.getUnit(),2500));
                row.add(new RowCellDTO(experience.getDepartment(),2500));
                row.add(new RowCellDTO(experience.getDuty(),2500));
                list.add(row);
            }
            rules.put("${lvliRow}",new RowDTO(list));
        }
        // 家庭成员
        if(CollectionUtils.isEmpty(vo.getPersonalExperience())) {
            rules.put("${familyRow}",new RowDTO());
        }else {
            List<List<RowCellDTO>> list = Lists.newArrayList();
            for(FamilyMember familyMember : vo.getFamilyMember()) {
                List<RowCellDTO> row = Lists.newArrayList();
                row.add(new RowCellDTO(familyMember.getName(),800));
                row.add(new RowCellDTO(familyMember.getRelation(),800));
                row.add(new RowCellDTO(familyMember.getCompany(),800));
                row.add(new RowCellDTO(familyMember.getPhone(),800));
                row.add(new RowCellDTO(familyMember.getIdentityCard(),800));

                list.add(row);
            }
            rules.put("${familyRow}",new RowDTO(list));
        }

        String savePath = diskStaticUrl + "files/";
        try {
            String wordName = WordUtil.replaceWordAndSave(inputStream, savePath, rules);
            String wordPath = savePath + wordName;
            return new File(wordPath);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 导出收入证明
     * @param code
     * @param inputStream
     * @return
     * @throws Exception
     */
    public File exportIncomecertificate(String code, InputStream inputStream) throws Exception{
        if(StringUtils.isEmpty(code)) {
            throw new BaseException("code is null");
        }
        BizUserDetailVO vo = findByCode(code);
        if(vo == null) {
            throw new BaseException("找不到该人员，请重试");
        }
        if(StringUtils.isEmpty(diskStaticUrl)) {
            throw new BaseException("diskStaticUrl is null");
        }
        if(StringUtils.isEmpty(vo.getName())) {
            throw new BaseException("该人员缺少[姓名]字段，请补充完整个人信息。");
        }
        if(StringUtils.isEmpty(vo.getBeginPoliceWorkTime())) {
            throw new BaseException("该人员缺少[入职公安时间]字段，请补充完整个人信息。");
        }
        if(vo.getJobGrade() != JobGradeEnum.FIRST.getCode()) {
            throw new BaseException("只有[一级辅警]才可以导出收入证明");
        }

        Map<String,Object> rules = new HashMap<>();
        rules.put("${name}",new TextDTO(vo.getName(),false));
        rules.put("${identityCard}",new TextDTO(vo.getIdentityCard(),false));

        Date beginWorkTime = DateUtil.convert2Date(vo.getBeginPoliceWorkTime(), BizUserConstant.DateFormat);
        String beginWorkTimeStr = DateUtil.convert2String(beginWorkTime, BizUserConstant.DateYYYYNianMMyueFormat);
        rules.put("${beginWorkTime}",new TextDTO(beginWorkTimeStr,false));

        Date time = new Date();
        String timeStr = DateUtil.convert2String(time, BizUserConstant.DateYYYYNianMMyueddriFormat);
        rules.put("${exportTime}",new TextDTO(timeStr,false));

        String savePath = diskStaticUrl + "files/";
        try {
            String wordName = WordUtil.replaceWordAndSave(inputStream, savePath, rules);
            String wordPath = savePath + wordName;
            return new File(wordPath);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 导出在职证明
     * @param code
     * @param inputStream
     * @return
     * @throws Exception
     */
    public File exportonthejobcertificate(String code, InputStream inputStream) throws Exception{
        if(StringUtils.isEmpty(code)) {
            throw new BaseException("code is null");
        }
        BizUserDetailVO vo = findByCode(code);
        if(vo == null) {
            throw new BaseException("找不到该人员，请重试");
        }
        if(StringUtils.isEmpty(diskStaticUrl)) {
            throw new BaseException("diskStaticUrl is null");
        }
        if(StringUtils.isEmpty(vo.getName())) {
            throw new BaseException("该人员缺少[姓名]字段，请补充完整个人信息。");
        }
        if(StringUtils.isEmpty(vo.getFirstGradeTime())) {
            throw new BaseException("该人员缺少[任一级辅警起算时间]字段，请补充完整个人信息。");
        }
        if(StringUtils.isEmpty(vo.getBeginPoliceWorkTime())) {
            throw new BaseException("该人员缺少[入职公安时间]字段，请补充完整个人信息。");
        }
        if(StringUtils.isEmpty(vo.getSexStr())) {
            throw new BaseException("该人员缺少[性别]字段，请补充完整个人信息。");
        }

        Map<String,Object> rules = new HashMap<>();

        rules.put("${name}",new TextDTO(vo.getName(),false));

        rules.put("${identityCard}",new TextDTO(vo.getIdentityCard(),false));

        Date firstGradeTime = DateUtil.convert2Date(vo.getFirstGradeTime(), BizUserConstant.DateFormat);
        String firstGradeTimeStr = DateUtil.convert2String(firstGradeTime, BizUserConstant.DateYYYYNianMMyueFormat);
        rules.put("${beginWorkTime}",new TextDTO(firstGradeTimeStr,false));

        Date time = new Date();
        String timeStr = DateUtil.convert2String(time, BizUserConstant.DateYYYYNianMMyueddriFormat);
        rules.put("${exportTime}",new TextDTO(timeStr,false));

        rules.put("${sex}",new TextDTO(vo.getSexStr(),false));
        rules.put("${age}",new TextDTO(String.valueOf(vo.getAge()),false));

        String savePath = diskStaticUrl + "files/";
        try {
            String wordName = WordUtil.replaceWordAndSave(inputStream, savePath, rules);
            String wordPath = savePath + wordName;
            return new File(wordPath);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
