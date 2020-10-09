package com.base.biz.user.server.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.authority.client.client.AuthorityClient;
import com.base.authority.client.client.UserRoleClient;
import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.model.UserRoleDTO;
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
import com.base.biz.user.server.service.BizUserAddUserCheckService;
import com.base.biz.user.server.service.BizUserService;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.common.util.VerifyUtil;
import com.base.common.util.WordUtil;
import com.base.common.util.WordUtil.*;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.client.CompanyClient;
import com.base.resource.client.client.ResourceClient;
import com.base.resource.client.model.ResourceVO;
import com.base.user.client.client.UserClient;
import com.base.user.client.model.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:小M
 * @date:2020/3/30 12:03 AM
 */
@Service
public class BizUserInnerServiceImpl implements BizUserService {

    private final static Logger logger = LoggerFactory.getLogger(BizUserInnerServiceImpl.class);

    private final static int forkJoinNum = 20;

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private BizUserAddUserCheckService bizUserAddUserCheckService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PersonalExperienceManager personalExperienceManager;
    @Autowired
    private FamilyMemberManager familyMemberManager;
    @Autowired
    private AwardManager awardManager;
    @Autowired
    private AssessmentManager assessmentManager;
    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ResourceClient resourceClient;
    @Autowired
    private AuthorityClient authorityClient;
    @Autowired
    private UserRoleClient userRoleClient;

    /**
     *
     * @param name
     * @param companyList
     * @return
     */
    @Override
    public List<BizUserPageListVO> findByNameAndCompanyCodeList(String userCode, String name, List<String> companyList) throws Exception{

        if(StringUtils.isEmpty(name) && CollectionUtils.isEmpty(companyList)) {
            throw new BaseException("请输入查询条件");
        }

        // 如果有了名称，忽略单位
        if(StringUtils.isNotEmpty(name)) {
            boolean hasAuth = authorityClient.hasAuthority(userCode, "UserListSelectAllCompany");
            BizUserDTO bizUserDTO = bizUserManager.findByCode(userCode);
            if(!hasAuth) {
                List<String> companyCodes = companyClient.findCompanyTree(bizUserDTO.getWorkUnitCode());
                companyList = companyCodes;
            }else {
                companyList = null;
            }
        }

        List<BizUserDTO> bizUserDTOList = bizUserManager.findByNameAndCompany(name, companyList);
        if(CollectionUtils.isEmpty(bizUserDTOList)) {
            return Lists.newArrayList();
        }
        List<BizUserPageListVO> result = Lists.newArrayList();
        List<String> companyCodeList = Lists.newArrayList();
        List<String> userCodeList = Lists.newArrayList();
        for(BizUserDTO bizUserDTO : bizUserDTOList) {
            if (bizUserDTO.getCode().equals("admin")) {
                continue;
            }
            BizUserPageListVO bizUserPageListVO = new BizUserPageListVO();
            bizUserPageListVO.setCode(bizUserDTO.getCode());
            bizUserPageListVO.setName(bizUserDTO.getName());
            bizUserPageListVO.setCompanyCode(bizUserDTO.getWorkUnitCode());
            bizUserPageListVO.setPoliceCode(bizUserDTO.getPoliceCode());
            result.add(bizUserPageListVO);
            companyCodeList.add(bizUserDTO.getWorkUnitCode());
            userCodeList.add(bizUserDTO.getCode());
        }
        Map<String,CompanyVO> companyVOMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyCodeList)) {
            List<CompanyVO> companyVOList = companyClient.findByCodeList(companyCodeList);
            if (CollectionUtils.isNotEmpty(companyVOList)) {
                for(CompanyVO companyVO : companyVOList) {
                    companyVOMap.put(companyVO.getCode(), companyVO);
                }
            }
        }
        Map<String, List<UserRoleDTO>> userRoleMap = null;
        if(CollectionUtils.isNotEmpty(userCodeList)) {
            userRoleMap = userRoleClient.selectByUserCodes(userCodeList);
        }
        for(BizUserPageListVO bizUserPageListVO : result) {
            CompanyVO companyVO = companyVOMap.get(bizUserPageListVO.getCompanyCode());
            if(companyVO != null) {
                bizUserPageListVO.setCompanyName(companyVO.getName());
            }
            if(userRoleMap != null) {
                List<UserRoleDTO> userRoleDTOList = userRoleMap.get(bizUserPageListVO.getCode());
                if(CollectionUtils.isNotEmpty(userRoleDTOList)) {
                    List<String> roles = userRoleDTOList.stream().map(UserRoleDTO::getRoleCode).collect(Collectors.toList());
                    bizUserPageListVO.setRoleCodeList(roles);
                }
            }
        }

        return result;
    }

    /**
     * 根据code查询详情
     * @param code
     * @return
     */
    @Override
    public BizUserDetailVO findByCode(String code) throws Exception{
        if(StringUtils.isBlank(code)) {
            throw new BaseException("Code为空");
        }
        BizUserDTO dto = bizUserManager.findByCode(code);
        return dto2vo(dto,null);
    }

    @Override
    public BizUserDetailVO dto2vo(BizUserDTO dto , Map<String,String> companyCode2NameMap){
        if(companyCode2NameMap == null) {
            companyCode2NameMap = new HashMap<>();
        }
        BizUserDetailVO vo = new BizUserDetailVO();
        vo.setName(dto.getName());
        String picName = dto.getPicUrl();
        Map<String,ResourceVO> resourceVOMap = resourceClient.findByNameList(Lists.newArrayList(picName));
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
        vo.setUserType(dto.getUserType());
        vo.setOrganizationUnit(dto.getOrganizationUnitCode());



        String companyName = companyCode2NameMap.get(dto.getWorkUnitCode());
        if(StringUtils.isNotEmpty(companyName)) {
            vo.setWorkUnitName(companyName);
        }else {
            companyName = "";
            String companyCode = dto.getWorkUnitCode();
            while (true) {
                List<CompanyVO> companyVOList = companyClient.findByCodeList(Lists.newArrayList(companyCode));
                if (CollectionUtils.isNotEmpty(companyVOList)) {
                    CompanyVO companyVO = companyVOList.get(0);
                    companyName = companyVO.getName() + "/" + companyName;
                    if(StringUtils.isNotEmpty(companyVO.getFatherCode())) {
                        companyCode = companyVO.getFatherCode();
                    }else {
                        break;
                    }
                }else {
                    break;
                }
            }
            if(companyName.endsWith("/")) {
                companyName = companyName.substring(0,companyName.length()-1);
            }
            vo.setWorkUnitName(companyName);
            companyCode2NameMap.put(dto.getWorkUnitCode(),companyName);
        }

        companyName = companyCode2NameMap.get(dto.getOrganizationUnitCode());
        if(StringUtils.isNotEmpty(companyName)) {
            vo.setOrganizationUnitName(companyName);
        }else {
            companyName = "";
            String companyCode = dto.getOrganizationUnitCode();
            while (true) {
                List<CompanyVO> companyVOList = companyClient.findByCodeList(Lists.newArrayList(companyCode));
                if (CollectionUtils.isNotEmpty(companyVOList)) {
                    CompanyVO companyVO = companyVOList.get(0);
                    companyName = companyVO.getName() + "/" + companyName;
                    if(StringUtils.isNotEmpty(companyVO.getFatherCode())) {
                        companyCode = companyVO.getFatherCode();
                    }else {
                        break;
                    }
                }else {
                    break;
                }
            }
            if(companyName.endsWith("/")) {
                companyName = companyName.substring(0,companyName.length()-1);
            }
            vo.setOrganizationUnitName(companyName);
            companyCode2NameMap.put(dto.getOrganizationUnitCode(),companyName);
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
                experience.setTimeStartDate(personalExperienceDTO.getTimeStart());
                experience.setTimeStart(DateUtil.convert2String(personalExperienceDTO.getTimeStart(), BizUserConstant.DateFormat));
                experience.setTimeEndDate(personalExperienceDTO.getTimeEnd());
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
        List<AssessmentDTO> assessmentDTOList = assessmentManager.listByUserCode(dto.getCode());
        if(CollectionUtils.isNotEmpty(assessmentDTOList)) {
            List<BizUserDetailVO.Assessment> assessmentList = Lists.newArrayList();
            for(AssessmentDTO assessmentDTO : assessmentDTOList) {
                BizUserDetailVO.Assessment assessment = new BizUserDetailVO.Assessment();
                assessment.setTime(DateUtil.convert2String(assessmentDTO.getTime(), BizUserConstant.DateFormat));
                assessment.setTimeDate(assessmentDTO.getTime());
                assessment.setGrade(Integer.valueOf(assessmentDTO.getGrade()));
                assessment.setRemark(assessmentDTO.getRemark());
                assessment.setUserCode(assessmentDTO.getUserCode());
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
    @Override
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
            List<CompanyVO> companyVOList = companyClient.findByCodeList(companyCodeList);
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
    @Override
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

        UserVO userVO = userClient.updateToken(bizUserDTO.getCode());

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
    @Override
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

        userClient.add(param.identityCard);
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
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(UpdateParam param) throws RuntimeException {
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
    @Override
    public void deleteByCode(String code, boolean deleteUser) throws RuntimeException{
        BizUserDTO dto = bizUserManager.findByCode(code);
        if(dto == null) {
            throw new BaseException(String.format("该人员不存在[%s]", code));
        }
        if(deleteUser) {
            bizUserManager.deleteByCode(code);
            userClient.deleteByCode(code);
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
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void importUser(InputStream inputStream)throws Exception {
        List<BizUserAddParam> bizUserAddParamList = BizUserAddExcelReader.readExcel(inputStream);
        if(CollectionUtils.isNotEmpty(bizUserAddParamList)) {
            for(BizUserAddParam bizUserAddParam : bizUserAddParamList) {
                bizUserAddUserCheckService.check(bizUserAddParam);
            }
            //for(BizUserAddParam bizUserAddParam : bizUserAddParamList) {
            //    importOneUser(bizUserAddParam);
            //}

            ImportUserForkJoinTask task = new ImportUserForkJoinTask(bizUserAddParamList);
            ForkJoinPool commonPool = ForkJoinPool.commonPool();
            commonPool.invoke(task);
            task.get();
        }
    }

    private void importOneUser(BizUserAddParam bizUserAddParam) throws RuntimeException{
        BizUserDTO bizUserDTO = bizUserManager.findByIdentityCard(bizUserAddParam.identityCard);
        // 如果已存在则更新
        if (bizUserDTO != null) {
            UpdateParam updateParam = new UpdateParam();
            BeanUtils.copyProperties(bizUserAddParam, updateParam);
            updateParam.setCode(bizUserDTO.getCode());
            update(updateParam);
        }
        else {
            if(StringUtils.isNotEmpty(bizUserAddParam.policeCode)) {
                bizUserDTO = bizUserManager.findByPoliceCode(bizUserAddParam.policeCode);
                if (bizUserDTO != null && !bizUserDTO.getIdentityCard().equals(bizUserDTO.getIdentityCard())) {
                    throw new BaseException(String.format("该警号[%s]已存在",bizUserAddParam.identityCard));
                }
            }
            userClient.add(bizUserAddParam.identityCard);
            bizUserManager.add(bizUserAddParam);
        }

    }

    class ImportUserForkJoinTask extends RecursiveTask<Void> {

        private List<BizUserAddParam> bizUserAddParamList ;

        public ImportUserForkJoinTask(List<BizUserAddParam> bizUserAddParamList) {
            this.bizUserAddParamList = bizUserAddParamList;
        }

        @Override
        protected Void compute(){
            if(CollectionUtils.isEmpty(bizUserAddParamList)) {
                return null;
            }
            if(bizUserAddParamList.size() < forkJoinNum) {
                for(BizUserAddParam bizUserAddParam : bizUserAddParamList) {
                    importOneUser(bizUserAddParam);
                }
                return null;
            }
            int mid = bizUserAddParamList.size() / 2;
            List<BizUserAddParam> left = bizUserAddParamList.subList(0,mid);
            List<BizUserAddParam> right = bizUserAddParamList.subList(mid,bizUserAddParamList.size());
            ImportUserForkJoinTask leftTask = new ImportUserForkJoinTask(left);
            ImportUserForkJoinTask rightTask = new ImportUserForkJoinTask(right);
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();

            return null;
        }
    }

    /**
     * 导入相片
     * @param file
     * @throws Exception
     */
    @Override
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
            resourceClient.add("/static/images", imageCode, "png", imageCode);
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
    @Override
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
            File file = new File(diskStaticUrl + "images/" + vo.getHeadPicCode() + ".png");
            if(file.exists()) {
                picDTO.setFile(file);
                picDTO.setWidth(133);
                picDTO.setHeight(177);
                picDTO.setFileName(vo.getHeadPicCode());
                rules.put("${headPic}",picDTO);
            }
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
    @Override
    public File exportIncomeCertificate(String code, InputStream inputStream) throws Exception{
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
    @Override
    public File exportOnTheJobCertificate(String code, InputStream inputStream) throws Exception{
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

    @Override
    public String exportSelectUser(InputStream fromFileInputStream, List<String> userCodes) throws Exception {

        if(CollectionUtils.isEmpty(userCodes)) {
            throw new RuntimeException("请先查询出人员，再导出");
        }

        List<BizUserDTO> bizUserDTOList = bizUserManager.findByCodes(userCodes);
        if(CollectionUtils.isEmpty(bizUserDTOList)) {
            throw new RuntimeException("查询不到人员，请重试");
        }

        Map<String,String> companyCode2NameMap = new HashMap<>();
        List<List<CellDTO>> list = Lists.newArrayList();
        for(BizUserDTO bizUserDTO : bizUserDTOList) {
            BizUserDetailVO vo = dto2vo(bizUserDTO,companyCode2NameMap);
            List<CellDTO> row = Lists.newArrayList();
            row.add(new CellDTO(vo.getName()));
            CellDTO birthdayCell = new CellDTO(vo.getBirthdate());
            birthdayCell.isDate = true;
            birthdayCell.dateFormat = BizUserConstant.DateFormat;
            row.add(birthdayCell);
            row.add(new CellDTO(vo.getNationStr()));
            row.add(new CellDTO(vo.getPoliticalLandscapeStr()));
            row.add(new CellDTO(vo.getGraduateInstitutions()));
            row.add(new CellDTO(vo.getPoliceCode()));
            row.add(new CellDTO(vo.getQuasiDrivingTypeStr()));
            row.add(new CellDTO(vo.getSpecialPeopleStr()));
            row.add(new CellDTO(vo.getQualification()));
            row.add(new CellDTO(vo.getPermanentResidenceAddress()));
            row.add(new CellDTO(vo.getFamilyAddress()));
            row.add(new CellDTO(vo.getSexStr()));
            row.add(new CellDTO(vo.getNativePlace()));
            row.add(new CellDTO(vo.getEducationStr()));
            row.add(new CellDTO(vo.getMajor()));
            row.add(new CellDTO(vo.getMaritalStatusStr()));
            row.add(new CellDTO(vo.getIdentityCard()));
            row.add(new CellDTO(vo.getPhone()));
            row.add(new CellDTO(vo.getPersonnelTypeStr()));
            row.add(new CellDTO(vo.getAuthorizedStrengthTypeStr()));
            row.add(new CellDTO(vo.getPlaceOfWorkStr()));
            row.add(new CellDTO(vo.getJobGradeStr()));
            row.add(new CellDTO(vo.getTreatmentGradeStr()));
            row.add(new CellDTO(vo.getEnrollWayStr()));
            row.add(new CellDTO(vo.getBeginWorkTime()));
            row.add(new CellDTO(vo.getEffectiveDateOfTheContract()));
            row.add(new CellDTO(vo.getWorkUnitName()));
            row.add(new CellDTO(vo.getOrganizationUnitName()));
            row.add(new CellDTO(vo.getJobCategoryStr()));
            row.add(new CellDTO(vo.getDuty()));
            row.add(new CellDTO(vo.getSocialSecurityNumber()));
            row.add(new CellDTO(vo.getBeginPoliceWorkTime()));
            row.add(new CellDTO(vo.getContractExpirationDate()));
            row.add(new CellDTO(vo.getDimissionDate()));
            row.add(new CellDTO(vo.getDimissionReason()));
            row.add(new CellDTO(vo.getWorkCardBeginTime()));
            row.add(new CellDTO(vo.getFirstGradeTime()));
            row.add(new CellDTO(vo.getFirstContractBeginTime()));
            row.add(new CellDTO(vo.getFirstContractEngTime()));
            row.add(new CellDTO(vo.getSecondContractBeginTime()));
            row.add(new CellDTO(vo.getSecondContractEngTime()));
            row.add(new CellDTO(vo.getThirdContractBeginTime()));
            row.add(new CellDTO(vo.getThirdContractEngTime()));
            row.add(new CellDTO(vo.getDueContractStr()));
            row.add(new CellDTO(vo.getIcbcCardAccount()));
            row.add(new CellDTO(vo.getRuZhiZuLinTime()));
            list.add(row);
        }

        String savePath = diskStaticUrl + "files/";
        String fileName = ExcelUtil.insertExcelAndSave(fromFileInputStream,1, 1, savePath, list, null);
        return savePath + fileName;
    }

    @Override
    public List<AuthorityVO> getAuthority(String userCode) throws Exception {
        if(Objects.equals("admin",userCode)) {
            return authorityClient.listAll();
        }
        return authorityClient.listByUserCode(userCode);
    }
}
