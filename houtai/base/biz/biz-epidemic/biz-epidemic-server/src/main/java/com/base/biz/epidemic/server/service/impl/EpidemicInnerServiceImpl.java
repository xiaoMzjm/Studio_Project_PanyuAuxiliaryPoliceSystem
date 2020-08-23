package com.base.biz.epidemic.server.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.authority.client.service.AuthorityService;
import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicLocationEnum;
import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicStatusEnum;
import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicTypeEnum;
import com.base.biz.epidemic.client.model.EpidemicDTO;
import com.base.biz.epidemic.client.model.EpidemicStatisticsVO;
import com.base.biz.epidemic.client.model.EpidemicVO;
import com.base.biz.epidemic.server.manager.EpidemicManager;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;
import com.base.biz.epidemic.server.service.EpidemicInnerService;
import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.model.ExpireVO;
import com.base.biz.expire.client.service.ExpireClientService;
import com.base.biz.user.client.common.Enums.UserTypeEnum;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserClientService;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.common.util.WordUtil;
import com.base.common.util.WordUtil.TextDTO;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyClientService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author:小M
 * @date:2020/8/2 4:37 PM
 */
@Service
public class EpidemicInnerServiceImpl implements EpidemicInnerService {

    @Autowired
    private EpidemicManager epidemicManager;
    @Autowired
    private BizUserClientService bizUserClientService;
    @Autowired
    private CompanyClientService companyClientService;
    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;
    @Autowired
    private ExpireClientService expireClientService;
    @Autowired
    private AuthorityService authorityService;


    @Override
    public void add(String companyCode, Integer type,
                    Integer location, String userCode,
                    String beginTime, String endTime,
                    String detail, String leaderCode, String detailLocation) throws Exception {

        Date beginTimeDate = DateUtil.convert2Date(beginTime, "yyyy/MM/dd");
        Date endTimeDate = DateUtil.convert2Date(endTime, "yyyy/MM/dd");

        if(StringUtils.isEmpty(companyCode)) {
            throw new BaseException("单位必须填写");
        }
        if(type == null) {
            throw new BaseException("类型必须填写");
        }
        if(location == null) {
            throw new BaseException("地点必须填写");
        }
        if(StringUtils.isEmpty(userCode)) {
            throw new BaseException("人员必须填写");
        }
        if(StringUtils.isEmpty(beginTime)) {
            throw new BaseException("起始日期必须填写");
        }
        if(StringUtils.isEmpty(endTime)) {
            throw new BaseException("结束日期必须填写");
        }
        if(StringUtils.isEmpty(leaderCode)) {
            throw new BaseException("审批领导必须填写");
        }
        if(StringUtils.isEmpty(detailLocation)) {
            throw new BaseException("详细地址必须填写");
        }

        check(type,location);

        String[] userCodes = userCode.split(",");
        for(String code : userCodes) {
            epidemicManager.add(companyCode, type,
                location, code,
                beginTimeDate, endTimeDate,
                detail, leaderCode, detailLocation, EpidemicStatusEnum.Edit.getStatus());
        }
    }

    @Override
    public List<EpidemicVO> select(EpidemicSelectParam epidemicSelectParam) throws Exception {

        if(StringUtils.isNotEmpty(epidemicSelectParam.getUserName())) {
            List<BizUserDetailVO> bizUserDetailVOList = bizUserClientService.listByNameLike(epidemicSelectParam.getUserName());
            if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
                epidemicSelectParam.setUserCodeList(bizUserDetailVOList.stream().map(BizUserDetailVO::getCode).collect(Collectors.toList()));
            }
        }

        List<EpidemicDTO> epidemicDTOList = epidemicManager.select(epidemicSelectParam);
        return dtoToVo(epidemicDTOList);
    }

    @Override
    public List<EpidemicVO> selectCurrent(String userCode) throws Exception {

        EpidemicSelectParam epidemicSelectParam = new EpidemicSelectParam();
        String beginTime = DateUtil.getCurrentDateStr("yyyy-MM-dd 00:00:00");

        // 获取是否有查看全部的权限，没有的话，要设置该人所在的单位列表
        boolean selectAllAuth = authorityService.hasAuthority(userCode, "EpidemicListManagerSelectAllCompany");
        if(!selectAllAuth) {
            BizUserDetailVO bizUserDetailVO = bizUserClientService.getByUserCode(userCode);
            Assert.notNull(bizUserDetailVO, "查询不到用户" + userCode);
            List<String> companyCodeList = companyClientService.findCompanyTree(bizUserDetailVO.getWorkUnit());
            epidemicSelectParam.setCompanyCodeList(companyCodeList);
        }

        epidemicSelectParam.setBeginTime(beginTime);
        epidemicSelectParam.setEndTime(beginTime);
        List<EpidemicDTO> epidemicDTOList = epidemicManager.select(epidemicSelectParam);
        return dtoToVo(epidemicDTOList);
    }

    private void check(Integer type, Integer location) throws Exception{
        if(EpidemicTypeEnum.GeLiWeiShangBan.getType().equals(type)) {
            if(!(EpidemicLocationEnum.ZiXingGeLi.getLocation().equals(location) ||
                EpidemicLocationEnum.DanWeiJiZhong.getLocation().equals(location) ||
                EpidemicLocationEnum.WeiJianBuMen.getLocation().equals(location))) {
                throw new BaseException("'处于隔离观察期未上班'类型只能选择'自行隔离'或'单位集中'或'卫健部门'");
            }
        }else {
            if((EpidemicLocationEnum.ZiXingGeLi.getLocation().equals(location) ||
                EpidemicLocationEnum.DanWeiJiZhong.getLocation().equals(location) ||
                EpidemicLocationEnum.WeiJianBuMen.getLocation().equals(location))) {
                throw new BaseException("'因公外出'或'因私外出'只能选择'或'北京/湖北/境内中高风险地区/广东省内/境内其他地区/境外'");
            }
        }
    }

    private List<EpidemicVO> dtoToVo(List<EpidemicDTO> epidemicDTOList) throws Exception{
        if (CollectionUtils.isEmpty(epidemicDTOList)) {
            return Lists.newArrayList();
        }

        List<String> userCodes = epidemicDTOList.stream().map(EpidemicDTO::getUserCode).collect(Collectors.toList());
        List<String> leaderCodes = epidemicDTOList.stream().map(EpidemicDTO::getLeaderCode).collect(
            Collectors.toList());
        userCodes.addAll(leaderCodes);
        Map<String, BizUserDetailVO> bizUserDetailVOMap = bizUserClientService.listByCodeList(userCodes);

        List<String> companyCodeList = epidemicDTOList.stream().map(EpidemicDTO::getCompanyCode).collect(
            Collectors.toList());
        List<CompanyVO> companyVOList = companyClientService.findByCodeList(companyCodeList);
        Map<String, CompanyVO> companyVOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(companyVOList)) {
            companyVOMap = companyVOList.stream().collect(Collectors.toMap(CompanyVO::getCode, Function.identity()));
        }

        List<EpidemicVO> result = Lists.newArrayList();

        for (EpidemicDTO epidemicDTO : epidemicDTOList) {
            EpidemicVO epidemicVO = new EpidemicVO();
            BeanUtils.copyProperties(epidemicDTO, epidemicVO);
            String userCode = epidemicDTO.getUserCode();
            String leaderCode = epidemicDTO.getLeaderCode();
            String companyCode = epidemicDTO.getCompanyCode();
            epidemicVO.setDetailLocation(epidemicDTO.getDetailLocation());

            BizUserDetailVO userVO = bizUserDetailVOMap.get(userCode);
            if (userVO != null) {
                epidemicVO.setUserName(userVO.getName());
                epidemicVO.setUserType(userVO.getUserType());
            }

            BizUserDetailVO leaderVO = bizUserDetailVOMap.get(leaderCode);
            if (leaderVO != null) {
                epidemicVO.setLeaderName(leaderVO.getName());
            }

            CompanyVO companyVO = companyVOMap.get(companyCode);
            if (companyVO != null) {
                epidemicVO.setCompanyName(companyVO.getName());
            }

            epidemicVO.setBeginTime(DateUtil.convert2String(epidemicDTO.getBeginTime(), "yyyy/MM/dd"));
            epidemicVO.setEndTime(DateUtil.convert2String(epidemicDTO.getEndTime(), "yyyy/MM/dd"));

            epidemicVO.setGmtCreateStr(DateUtil.convert2String(epidemicDTO.getGmtCreate(), "yyyy/MM/dd"));
            result.add(epidemicVO);
        }

        return result;
    }

    @Override
    public void confirm(String code) throws Exception {
        EpidemicDTO epidemicDTO = epidemicManager.selectByCode(code);
        if (epidemicDTO == null) {
            throw new BaseException("差不到记录:" + code);
        }
        epidemicManager.updateStatus(code, EpidemicStatusEnum.Confirm.getStatus());
    }

    @Override
    public void delete(String code) throws Exception {
        EpidemicDTO epidemicDTO = epidemicManager.selectByCode(code);
        if (epidemicDTO == null) {
            throw new BaseException("查不到记录:" + code);
        }
        if (epidemicDTO.getStatus().equals(EpidemicStatusEnum.Commit.getStatus())) {
            throw new BaseException("已提交的记录不能删除");
        }
        epidemicManager.delete(code);
    }

    @Override
    public void update(String code, String companyCode, Integer type, Integer location, String userCode,
                       String beginTime, String endTime, String detail, String leaderCode, String detailLocation) throws Exception {
        Date beginTimeDate = DateUtil.convert2Date(beginTime, "yyyy/MM/dd");
        Date endTimeDate = DateUtil.convert2Date(endTime, "yyyy/MM/dd");

        check(type,location);

        EpidemicDTO findEpidemicDTO = epidemicManager.selectByCode(code);
        if (findEpidemicDTO == null) {
            throw new BaseException("记录不存在:" + code);
        }
        if (findEpidemicDTO.getStatus().equals(EpidemicStatusEnum.Commit.getStatus())) {
            throw new BaseException("已提交的记录不能修改");
        }

        epidemicManager.update(code, companyCode, type,
            location, userCode,
            beginTimeDate, endTimeDate,
            detail, leaderCode, detailLocation, EpidemicStatusEnum.Edit.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStatistics(InputStream zhengGongBan, InputStream shiJu , InputStream message, String dateStr, String remark) throws Exception {

        // 找出对应日期的记录
        Date date = DateUtil.convert2Date(dateStr , "yyyy/MM/dd");
        Date now = new Date();
        Date nowAddOne = DateUtil.addDays(now, 1);
        String nowAddOneStr = DateUtil.convert2String(nowAddOne, "yyyy/MM/dd");
        Date nowAddOne2 = DateUtil.convert2Date(nowAddOneStr, "yyyy/MM/dd");
        if(date.getTime() >= nowAddOne2.getTime()) {
            throw new BaseException("当前日期不能汇总");
        }

        List<EpidemicDTO> epidemicDTOListt = epidemicManager.selectInDate(date);
        List<EpidemicVO> epidemicVOList = this.dtoToVo(epidemicDTOListt);

        // 组装单位和记录的映射关系
        Map<String,List<EpidemicVO>> companyCode2EpidemicVOListMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(epidemicVOList)) {

            // 校验状态
            Integer hour = DateUtil.getHour(now);
            //if(hour >= 11) {
                epidemicManager.commitAll();
            //}

            for(EpidemicVO epidemicVO : epidemicVOList) {
                List<EpidemicVO> value = companyCode2EpidemicVOListMap.get(epidemicVO.getCompanyCode());
                if(value == null) {
                    value = Lists.newArrayList();
                }
                value.add(epidemicVO);
                companyCode2EpidemicVOListMap.put(epidemicVO.getCompanyCode() , value);
            }
        }

        // 查询出所有的父单位
        List<CompanyVO>  companyVOList = companyClientService.findAllFaterCompany();

        // 组装excel数组
        List<List<ExcelUtil.CellDTO>> rules = Lists.newArrayList();
        Integer a1 = 0,a2 = 0,a3 = 0,a4 = 0,a5 = 0,a6 = 0,a7 = 0,a8 = 0,a9 = 0,a10 = 0,
            a11 = 0,a12 = 0,a13 = 0,a14 = 0,a15 = 0,a16 = 0,a17 = 0,a18 = 0,a19 = 0,a20 = 0,
            a21 = 0,a22 = 0,a23 = 0,a24 = 0,a25 = 0,a26 = 0,a27 = 0,a28 = 0,a29 = 0,a30 = 0,
            a31 = 0,a32 = 0 ;
        String allDetailsGeLi = "";
        String allDetailsWaiChu = "";

        Integer allWaiChuPeopleNum = 0;
        Integer allWaiChuYinGongPeopleNum = 0;
        Integer allWaiChuYinSiPeopleNum = 0;

        Integer allBeiJingWaiChuNum = 0;
        Integer allBeiJingYinGongWaiChuNum = 0;
        Integer allBeiJingYinSiWaiChuNum = 0;

        Integer allHuBeiWaiChuNum = 0;
        Integer allHuBeiYinGongWaiChuNum = 0;
        Integer allHuBeiYinSiWaiChuNum = 0;

        Integer allGuangDongWaiChuNum = 0;
        Integer allGuangDongYinGongWaiChuNum = 0;
        Integer allGuangDongYinSiWaiChuNum = 0;

        Integer allJingNeiWaiChuNum = 0;
        Integer allJingNeiYinGongWaiChuNum = 0;
        Integer allJingNeiYinSiWaiChuNum = 0;

        Integer allJingWaiNum = 0;
        Integer allJingWaiYinGongNum = 0;
        Integer allJingWaiYinSiNum = 0;

        Integer allGeLiNum = 0;
        Integer allGeLiMinJingNum = 0;
        Integer allGeLiFuJingNum = 0;

        Integer i = 1;
        Integer j = 1;

        for(CompanyVO companyVO : companyVOList) {
            Integer z = 1;
            String companyCode = companyVO.getCode();
            String companyName = companyVO.getName();
            String details = "";
            List<EpidemicVO> epidemicVOS = companyCode2EpidemicVOListMap.get(companyCode);

            Integer c1 = 0,c2 = 0,c3 = 0,c4 = 0,c5 = 0,c6 = 0,c7 = 0,c8 = 0,c9 = 0,c10 = 0,
                c11 = 0,c12 = 0,c13 = 0,c14 = 0,c15 = 0,c16 = 0,c17 = 0,c18 = 0,c19 = 0,c20 = 0,
                c21 = 0,c22 = 0,c23 = 0,c24 = 0,c25 = 0,c26 = 0,c27 = 0,c28 = 0,c29 = 0,c30 = 0,
                c31 = 0,c32 = 0 ;

            if(!CollectionUtils.isEmpty(epidemicVOS)) {

                for(EpidemicVO epidemicVO : epidemicVOS) {

                    if(epidemicVO.getDetailLocation() == null) {
                        epidemicVO.setDetailLocation("");
                    }
                    String userTypeStr = UserTypeEnum.getName(epidemicVO.getUserType());
                    String detail = companyName + userTypeStr + epidemicVO.getUserName() + "从" + epidemicVO.getBeginTime() + "至" + epidemicVO.getEndTime() + "在" + EpidemicLocationEnum.getDesc(epidemicVO.getLocation()) + epidemicVO.getDetailLocation() + EpidemicTypeEnum.getDesc(epidemicVO.getType());
                    if(epidemicVO.getType() == EpidemicTypeEnum.GeLiWeiShangBan.getType()) {
                        if(StringUtils.isNotEmpty(epidemicVO.getDetail())) {
                            allDetailsGeLi += i.toString() + "." + detail + "(" + epidemicVO.getDetail() + ")" + "\n";
                        }else {
                            allDetailsGeLi += i.toString() + "." + detail + "\n";
                        }

                        i++;
                    }else {
                        allDetailsWaiChu += j.toString() + "." + detail + "\n";
                        j++;
                    }

                    details += z.toString() + "." + detail + "\n";
                    z++;

                    // 处于隔离观察期未上班
                    if(epidemicVO.getType() == EpidemicTypeEnum.GeLiWeiShangBan.getType()) {
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.ZiXingGeLi.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.MinJing.getCode()) {
                            c1++;
                            a1++;
                            allGeLiMinJingNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.ZiXingGeLi.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.FuJing.getCode()) {
                            c2++;
                            a2++;
                            allGeLiFuJingNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.DanWeiJiZhong.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.MinJing.getCode()) {
                            c3++;
                            a3++;
                            allGeLiMinJingNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.DanWeiJiZhong.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.FuJing.getCode()) {
                            c4++;
                            a4++;
                            allGeLiFuJingNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.WeiJianBuMen.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.MinJing.getCode()) {
                            c5++;
                            a5++;
                            allGeLiMinJingNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.WeiJianBuMen.getLocation() &&
                            epidemicVO.getUserType() == UserTypeEnum.FuJing.getCode()) {
                            c6++;
                            a6++;
                            allGeLiFuJingNum++;
                        }
                        c7 = c1 + c3 + c5;
                        a7 = a1 + a3 + a5;
                        c8 = c2 + c4 + c6;
                        a8 = a2 + a4 + a6;

                        allGeLiNum++;
                    }

                    // 因公外出 && 民警
                    if(epidemicVO.getType() == EpidemicTypeEnum.YinGongWaiChu.getType() &&
                        epidemicVO.getUserType() == UserTypeEnum.MinJing.getCode()) {
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.BeiJing.getLocation()) {
                            c9++;
                            a9++;
                            allBeiJingWaiChuNum++;
                            allBeiJingYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.HuBei.getLocation()) {
                            c10++;
                            a10++;
                            allHuBeiWaiChuNum++;
                            allHuBeiYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiZhongGaoFengXianDiQu.getLocation()) {
                            c11++;
                            a11++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.GuangDongShenNei.getLocation()) {
                            c12++;
                            a12++;
                            allGuangDongWaiChuNum++;
                            allGuangDongYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiQiTaDiQu.getLocation()) {
                            c13++;
                            a13++;
                            allJingNeiWaiChuNum++;
                            allJingNeiYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingWai.getLocation()) {
                            c14++;
                            a14++;
                            allJingWaiNum++;
                            allJingWaiYinGongNum++;
                        }
                        allWaiChuPeopleNum++;
                        allWaiChuYinGongPeopleNum++;
                    }
                    // 因公外出 && 辅警
                    if(epidemicVO.getType() == EpidemicTypeEnum.YinGongWaiChu.getType() &&
                        epidemicVO.getUserType() == UserTypeEnum.FuJing.getCode()) {
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.BeiJing.getLocation()) {
                            c15++;
                            a15++;
                            allBeiJingWaiChuNum++;
                            allBeiJingYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.HuBei.getLocation()) {
                            c16++;
                            a16++;
                            allHuBeiWaiChuNum++;
                            allHuBeiYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiZhongGaoFengXianDiQu.getLocation()) {
                            c17++;
                            a17++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.GuangDongShenNei.getLocation()) {
                            c18++;
                            a18++;
                            allGuangDongWaiChuNum++;
                            allGuangDongYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiQiTaDiQu.getLocation()) {
                            c19++;
                            a19++;
                            allJingNeiWaiChuNum++;
                            allJingNeiYinGongWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingWai.getLocation()) {
                            c20++;
                            a20++;
                            allJingWaiNum++;
                            allJingWaiYinGongNum++;
                        }
                        allWaiChuPeopleNum++;
                        allWaiChuYinGongPeopleNum++;
                    }
                    // 因私外出 && 民警
                    if(epidemicVO.getType() == EpidemicTypeEnum.YinSiWaiChu.getType() &&
                        epidemicVO.getUserType() == UserTypeEnum.MinJing.getCode()) {
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.BeiJing.getLocation()) {
                            c21++;
                            a21++;
                            allBeiJingWaiChuNum++;
                            allBeiJingYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.HuBei.getLocation()) {
                            c22++;
                            a22++;
                            allHuBeiWaiChuNum++;
                            allHuBeiYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiZhongGaoFengXianDiQu.getLocation()) {
                            c23++;
                            a23++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.GuangDongShenNei.getLocation()) {
                            c24++;
                            a24++;
                            allGuangDongWaiChuNum++;
                            allGuangDongYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiQiTaDiQu.getLocation()) {
                            c25++;
                            a25++;
                            allJingNeiWaiChuNum++;
                            allJingNeiYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingWai.getLocation()) {
                            c26++;
                            a26++;
                            allJingWaiNum++;
                            allJingWaiYinSiNum++;
                        }
                        allWaiChuPeopleNum++;
                        allWaiChuYinSiPeopleNum++;
                    }
                    // 因私外出 && 辅警
                    if(epidemicVO.getType() == EpidemicTypeEnum.YinSiWaiChu.getType() &&
                        epidemicVO.getUserType() == UserTypeEnum.FuJing.getCode()) {
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.BeiJing.getLocation()) {
                            c27++;
                            a27++;
                            allBeiJingWaiChuNum++;
                            allBeiJingYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.HuBei.getLocation()) {
                            c28++;
                            a28++;
                            allHuBeiWaiChuNum++;
                            allHuBeiYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiZhongGaoFengXianDiQu.getLocation()) {
                            c29++;
                            a29++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.GuangDongShenNei.getLocation()) {
                            c30++;
                            a30++;
                            allGuangDongWaiChuNum++;
                            allGuangDongYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingNeiQiTaDiQu.getLocation()) {
                            c31++;
                            a31++;
                            allJingNeiWaiChuNum++;
                            allJingNeiYinSiWaiChuNum++;
                        }
                        if(epidemicVO.getLocation() == EpidemicLocationEnum.JingWai.getLocation()) {
                            c32++;
                            a32++;
                            allJingWaiNum++;
                            allJingWaiYinSiNum++;
                        }
                        allWaiChuPeopleNum++;
                        allWaiChuYinSiPeopleNum++;
                    }
                }
            }

            List<ExcelUtil.CellDTO> list = Lists.newArrayList();
            list.add(new CellDTO(companyName));
            list.add(new CellDTO(String.valueOf(c1)));
            list.add(new CellDTO(String.valueOf(c2)));
            list.add(new CellDTO(String.valueOf(c3)));
            list.add(new CellDTO(String.valueOf(c4)));
            list.add(new CellDTO(String.valueOf(c5)));
            list.add(new CellDTO(String.valueOf(c6)));
            list.add(new CellDTO(String.valueOf(c7)));
            list.add(new CellDTO(String.valueOf(c8)));
            list.add(new CellDTO(String.valueOf(c9)));
            list.add(new CellDTO(String.valueOf(c10)));
            list.add(new CellDTO(String.valueOf(c11)));
            list.add(new CellDTO(String.valueOf(c12)));
            list.add(new CellDTO(String.valueOf(c13)));
            list.add(new CellDTO(String.valueOf(c14)));
            list.add(new CellDTO(String.valueOf(c15)));
            list.add(new CellDTO(String.valueOf(c16)));
            list.add(new CellDTO(String.valueOf(c17)));
            list.add(new CellDTO(String.valueOf(c18)));
            list.add(new CellDTO(String.valueOf(c19)));
            list.add(new CellDTO(String.valueOf(c20)));
            list.add(new CellDTO(String.valueOf(c21)));
            list.add(new CellDTO(String.valueOf(c22)));
            list.add(new CellDTO(String.valueOf(c23)));
            list.add(new CellDTO(String.valueOf(c24)));
            list.add(new CellDTO(String.valueOf(c25)));
            list.add(new CellDTO(String.valueOf(c26)));
            list.add(new CellDTO(String.valueOf(c27)));
            list.add(new CellDTO(String.valueOf(c28)));
            list.add(new CellDTO(String.valueOf(c29)));
            list.add(new CellDTO(String.valueOf(c30)));
            list.add(new CellDTO(String.valueOf(c31)));
            list.add(new CellDTO(String.valueOf(c32)));
            if(StringUtils.isNotEmpty(details) &&  details.endsWith("\n")) {
                details = details.substring(0,details.length() - 1);
            }
            CellDTO detailCell = new CellDTO(details);
            detailCell.horizontalAlignment = HorizontalAlignment.LEFT;
            list.add(detailCell);
            list.add(new CellDTO(""));

            rules.add(list);
        }

        Map<String,String> replacemap = new HashMap<>();
        replacemap.put("date",dateStr);
        String savePath = diskStaticUrl + "files/";
        String wordName = ExcelUtil.insertExcelAndSave(zhengGongBan, 5, 0, savePath, rules, replacemap);

        // 市局表
        List<List<ExcelUtil.CellDTO>> allRules = Lists.newArrayList();
        List<ExcelUtil.CellDTO> list = Lists.newArrayList();
        list.add(new CellDTO(String.valueOf(a1)));
        list.add(new CellDTO(String.valueOf(a2)));
        list.add(new CellDTO(String.valueOf(a3)));
        list.add(new CellDTO(String.valueOf(a4)));
        list.add(new CellDTO(String.valueOf(a5)));
        list.add(new CellDTO(String.valueOf(a6)));
        list.add(new CellDTO(String.valueOf(a7)));
        list.add(new CellDTO(String.valueOf(a8)));
        list.add(new CellDTO(String.valueOf(a9)));
        list.add(new CellDTO(String.valueOf(a10)));
        list.add(new CellDTO(String.valueOf(a11)));
        list.add(new CellDTO(String.valueOf(a12)));
        list.add(new CellDTO(String.valueOf(a13)));
        list.add(new CellDTO(String.valueOf(a14)));
        list.add(new CellDTO(String.valueOf(a15)));
        list.add(new CellDTO(String.valueOf(a16)));
        list.add(new CellDTO(String.valueOf(a17)));
        list.add(new CellDTO(String.valueOf(a18)));
        list.add(new CellDTO(String.valueOf(a19)));
        list.add(new CellDTO(String.valueOf(a20)));
        list.add(new CellDTO(String.valueOf(a21)));
        list.add(new CellDTO(String.valueOf(a22)));
        list.add(new CellDTO(String.valueOf(a23)));
        list.add(new CellDTO(String.valueOf(a24)));
        list.add(new CellDTO(String.valueOf(a25)));
        list.add(new CellDTO(String.valueOf(a26)));
        list.add(new CellDTO(String.valueOf(a27)));
        list.add(new CellDTO(String.valueOf(a28)));
        list.add(new CellDTO(String.valueOf(a29)));
        list.add(new CellDTO(String.valueOf(a30)));
        list.add(new CellDTO(String.valueOf(a31)));
        list.add(new CellDTO(String.valueOf(a32)));
        if(StringUtils.isEmpty(allDetailsGeLi)) {
            allDetailsGeLi = "一、隔离情况\n" + "无\n";
        }else {
            allDetailsGeLi = "一、隔离情况\n" + allDetailsGeLi;
        }
        if(StringUtils.isEmpty(allDetailsWaiChu)) {
            allDetailsWaiChu = "二、外出情况\n" + "无\n";
        }else {
            allDetailsWaiChu = "二、外出情况\n" + allDetailsWaiChu;
        }

        String allDetail = allDetailsGeLi + allDetailsWaiChu;
        if(StringUtils.isNotEmpty(allDetail) &&  allDetail.endsWith("\n")) {
            allDetail = allDetail.substring(0,allDetail.length() - 1);
        }
        CellDTO detailCell = new CellDTO(allDetail);
        detailCell.horizontalAlignment = HorizontalAlignment.LEFT;

        list.add(detailCell);
        list.add(new CellDTO(""));
        allRules.add(list);
        String wordName2 = ExcelUtil.insertExcelAndSave(shiJu, 5, 0, savePath, allRules, replacemap);


        // 短信word
        Map<String,Object> wordMap = new HashMap<>();
        String dateString = DateUtil.convert2String(date , "MM月dd日");
        wordMap.put("${date}",new TextDTO(dateString,false));
        wordMap.put("${allWaiChuPeopleNum}",new TextDTO(allWaiChuPeopleNum.toString(),false));
        wordMap.put("${allWaiChuYinGongPeopleNum}",new TextDTO(allWaiChuYinGongPeopleNum.toString(),false));
        wordMap.put("${allWaiChuYinSiPeopleNum}",new TextDTO(allWaiChuYinSiPeopleNum.toString(),false));
        wordMap.put("${allBeiJingWaiChuNum}",new TextDTO(allBeiJingWaiChuNum.toString(),false));
        wordMap.put("${allBeiJingYinGongWaiChuNum}",new TextDTO(allBeiJingYinGongWaiChuNum.toString(),false));
        wordMap.put("${allBeiJingYinSiWaiChuNum}",new TextDTO(allBeiJingYinSiWaiChuNum.toString(),false));
        wordMap.put("${allHuBeiWaiChuNum}",new TextDTO(allHuBeiWaiChuNum.toString(),false));
        wordMap.put("${allHuBeiYinGongWaiChuNum}",new TextDTO(allHuBeiYinGongWaiChuNum.toString(),false));
        wordMap.put("${allHuBeiYinSiWaiChuNum}",new TextDTO(allHuBeiYinSiWaiChuNum.toString(),false));
        wordMap.put("${allGuangDongWaiChuNum}",new TextDTO(allGuangDongWaiChuNum.toString(),false));
        wordMap.put("${allGuangDongYinGongWaiChuNum}",new TextDTO(allGuangDongYinGongWaiChuNum.toString(),false));
        wordMap.put("${allGuangDongYinSiWaiChuNum}",new TextDTO(allGuangDongYinSiWaiChuNum.toString(),false));
        wordMap.put("${allJingNeiWaiChuNum}",new TextDTO(allJingNeiWaiChuNum.toString(),false));
        wordMap.put("${allJingNeiYinGongWaiChuNum}",new TextDTO(allJingNeiYinGongWaiChuNum.toString(),false));
        wordMap.put("${allJingNeiYinSiWaiChuNum}",new TextDTO(allJingNeiYinSiWaiChuNum.toString(),false));
        wordMap.put("${allJingWaiNum}",new TextDTO(allJingWaiNum.toString(),false));
        wordMap.put("${allJingWaiYinGongNum}",new TextDTO(allJingWaiYinGongNum.toString(),false));
        wordMap.put("${allJingWaiYinSiNum}",new TextDTO(allJingWaiYinSiNum.toString(),false));
        wordMap.put("${allGeLiNum}",new TextDTO(allGeLiNum.toString(),false));
        wordMap.put("${allGeLiMinJingNum}",new TextDTO(allGeLiMinJingNum.toString(),false));
        wordMap.put("${allGeLiFuJingNum}",new TextDTO(allGeLiFuJingNum.toString(),false));
        String wordName3 = WordUtil.replaceWordAndSave(message, savePath, wordMap);

        // 保存数据库
        String fileUrl1 = savePath + wordName;
        String fileUrl2 = savePath + wordName2;
        String fileUrl3 = savePath + wordName3;
        String fileName1 = "(政工办掌握)" + dateStr + "番禺分局队伍内防疫工作情况统计表(不需要报送)";
        String fileName2 = "(报市局)"+dateStr+"番禺分局队伍内部防疫工作情况统计表";
        String fileName3 = dateStr+"短信";
        expireClientService.add(fileName1 + "@" + fileName2 + "@" + fileName3, fileUrl1 + "@" + fileUrl2 + "@" + fileUrl3, date, remark, ExpireType.Epidemic.getCode());
    }

    @Override
    public List<EpidemicStatisticsVO> selectStatistics(String date) throws Exception {
        Date monthBegin = DateUtil.convert2Date(date, "yyyy/MM");
        Date nextMonthBegin = DateUtil.addMonths(monthBegin,1);
        List<ExpireVO> expireVOList = expireClientService.selectByTime(monthBegin, nextMonthBegin, ExpireType.Epidemic.getCode());

        Integer dayNumOfMonth = DateUtil.getDayNumOfMonth(DateUtil.getYear(monthBegin),DateUtil.getMonth(monthBegin));

        Map<Integer,ExpireVO> day2ExpireVoMap = new HashMap<>();
        for(ExpireVO expireVO : expireVOList) {
            Integer day = DateUtil.getDay(expireVO.getTime());
            day2ExpireVoMap.put(day, expireVO);
        }

        List<EpidemicStatisticsVO> result = Lists.newArrayList();
        for(int i = 1 ; i <= dayNumOfMonth; i++) {
            ExpireVO expireVO = day2ExpireVoMap.get(i);
            String zhengGongFileName = "";
            String shiJuFileName = "";
            String zhengGongFileCode = "";
            String shiJuFileCode = "";
            String remark = "";
            if(expireVO != null) {
                String names = expireVO.getName();
                String[] nameArray = names.split("@");
                zhengGongFileName = nameArray[0];
                shiJuFileName = nameArray[1];
                zhengGongFileCode = expireVO.getCode() + "@1";
                shiJuFileCode = expireVO.getCode() + "@2";
                remark = expireVO.getRemark();
            }
            EpidemicStatisticsVO epidemicStatisticsVO = new EpidemicStatisticsVO();
            epidemicStatisticsVO.setDay(i);
            epidemicStatisticsVO.setZhengGongFileName(zhengGongFileName);
            epidemicStatisticsVO.setZhengGongFileCode(zhengGongFileCode);
            epidemicStatisticsVO.setShiJuFileName(shiJuFileName);
            epidemicStatisticsVO.setShiJuFileCode(shiJuFileCode);
            epidemicStatisticsVO.setRemark(remark);

            result.add(epidemicStatisticsVO);
        }
        return result;
    }

    @Override
    public EpidemicStatisticsVO selectStatisticsByDay(String date) throws Exception {
        Date dayBegin = DateUtil.convert2Date(date, "yyyy/MM/dd");
        Date nextDayBegin = DateUtil.addDays(dayBegin,1);
        List<ExpireVO> expireVOList = expireClientService.selectByTime(dayBegin, nextDayBegin, ExpireType.Epidemic.getCode());
        if(CollectionUtils.isNotEmpty(expireVOList)) {
            ExpireVO expireVO = expireVOList.get(0);
            String zhengGongFileName = "";
            String shiJuFileName = "";
            String zhengGongFileCode = "";
            String shiJuFileCode = "";
            String messageFileName = "";
            String messageFileCode = "";
            String remark = "";
            if(expireVO != null) {
                String names = expireVO.getName();
                String[] nameArray = names.split("@");
                zhengGongFileName = nameArray[0];
                shiJuFileName = nameArray[1];
                messageFileName = nameArray[2];

                zhengGongFileCode = expireVO.getCode() + "@1";
                shiJuFileCode = expireVO.getCode() + "@2";
                messageFileCode = expireVO.getCode() + "@3";

                remark = expireVO.getRemark();
            }
            EpidemicStatisticsVO epidemicStatisticsVO = new EpidemicStatisticsVO();
            epidemicStatisticsVO.setZhengGongFileName(zhengGongFileName);
            epidemicStatisticsVO.setZhengGongFileCode(zhengGongFileCode);
            epidemicStatisticsVO.setShiJuFileName(shiJuFileName);
            epidemicStatisticsVO.setShiJuFileCode(shiJuFileCode);
            epidemicStatisticsVO.setMessageFileName(messageFileName);
            epidemicStatisticsVO.setMessageFileCode(messageFileCode);
            epidemicStatisticsVO.setRemark(remark);
            return epidemicStatisticsVO;
        }
        return null;
    }

}