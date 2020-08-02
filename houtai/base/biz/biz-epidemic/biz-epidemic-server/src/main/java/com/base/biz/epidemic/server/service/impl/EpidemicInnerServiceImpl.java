package com.base.biz.epidemic.server.service.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicStatusEnum;
import com.base.biz.epidemic.client.model.EpidemicDTO;
import com.base.biz.epidemic.client.model.EpidemicVO;
import com.base.biz.epidemic.server.manager.EpidemicManager;
import com.base.biz.epidemic.server.model.EpidemicDO;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;
import com.base.biz.epidemic.server.service.EpidemicInnerService;
import com.base.biz.expire.client.common.ExpireEnums.ExpireType;
import com.base.biz.expire.client.service.ExpireClientService;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.service.BizUserClientService;
import com.base.common.exception.BaseException;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.ExcelUtil.CellDTO;
import com.base.department.client.model.CompanyVO;
import com.base.department.client.service.CompanyClientService;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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


    @Override
    public void add(String companyCode, Integer type,
                    Integer location, String userCode,
                    String beginTime, String endTime,
                    String detail, String leaderCode) throws Exception {

        Date beginTimeDate = DateUtil.convert2Date(beginTime, "yyyy/MM/dd");
        Date endTimeDate = DateUtil.convert2Date(endTime, "yyyy/MM/dd");

        epidemicManager.add(companyCode, type,
            location, userCode,
            beginTimeDate, endTimeDate,
            detail, leaderCode, EpidemicStatusEnum.Edit.getStatus());

    }

    @Override
    public List<EpidemicVO> select(EpidemicSelectParam epidemicSelectParam) throws Exception {

        if(StringUtils.isNotEmpty(epidemicSelectParam.getUserName())) {
            List<BizUserDetailVO> bizUserDetailVOList = bizUserClientService.getByNameLike(epidemicSelectParam.getUserName());
            if(CollectionUtils.isNotEmpty(bizUserDetailVOList)) {
                epidemicSelectParam.setUserCodeList(bizUserDetailVOList.stream().map(BizUserDetailVO::getCode).collect(Collectors.toList()));
            }
        }

        List<EpidemicDTO> epidemicDTOList = epidemicManager.select(epidemicSelectParam);
        if (CollectionUtils.isEmpty(epidemicDTOList)) {
            return Lists.newArrayList();
        }

        List<String> userCodes = epidemicDTOList.stream().map(EpidemicDTO::getUserCode).collect(Collectors.toList());
        List<String> leaderCodes = epidemicDTOList.stream().map(EpidemicDTO::getLeaderCode).collect(
            Collectors.toList());
        userCodes.addAll(leaderCodes);
        Map<String, BizUserDetailVO> bizUserDetailVOMap = bizUserClientService.getByCodeList(userCodes);

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
                       String beginTime, String endTime, String detail, String leaderCode) throws Exception {
        Date beginTimeDate = DateUtil.convert2Date(beginTime, "yyyy/MM/dd");
        Date endTimeDate = DateUtil.convert2Date(endTime, "yyyy/MM/dd");

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
            detail, leaderCode, EpidemicStatusEnum.Edit.getStatus());
    }

    @Override
    public void createStatistics(InputStream zhengGongBan, InputStream shiJu , String dateStr) throws Exception {

        Date startTime = DateUtil.convert2Date(dateStr);
        Date endTime = DateUtil.addDays(startTime, 1);

        EpidemicSelectParam epidemicSelectParam = new EpidemicSelectParam();
        epidemicSelectParam.setBeginTime(DateUtil.convert2String(startTime, "yyyy/MM/dd"));
        epidemicSelectParam.setEndTime(DateUtil.convert2String(endTime, "yyyy/MM/dd"));
        epidemicSelectParam.setStatusList(Lists.newArrayList(EpidemicStatusEnum.Commit.getStatus()));
        List<EpidemicVO> epidemicVOList = select(epidemicSelectParam);

        List<List<ExcelUtil.CellDTO>> rules = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(epidemicVOList)) {
            Map<String,List<EpidemicVO>> map = new HashMap<>();
            for(EpidemicVO epidemicVO : epidemicVOList) {
                List<EpidemicVO> value = map.get(epidemicVO.getCompanyCode());
                if(value == null) {
                    value = Lists.newArrayList();
                }
                value.add(epidemicVO);
                map.put(epidemicVO.getCompanyCode() , value);
            }

            for(Map.Entry<String,List<EpidemicVO>> entry : map.entrySet()){
                String companyCode = entry.getKey();
                List<EpidemicVO> epidemicVOS = entry.getValue();
                String companyName = epidemicVOS.get(0).getCompanyName();

                for(EpidemicVO epidemicVO : epidemicVOS) {

                }

            }
        }


        String savePath = diskStaticUrl + "files/";
        String wordName = ExcelUtil.insertExcelAndSave(zhengGongBan, 5, 0, savePath, rules);

        String fileUrl = savePath + wordName;
        String fileName = "(政工办掌握)" + dateStr + "番禺分局队伍内防疫工作情况统计表(不需要报送)";
        expireClientService.add(fileName, fileUrl, startTime, ExpireType.Epidemic.getCode());
    }

    public int getDays(int year, int month) {
        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;

            }
        } else {
            // 闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                days = 29;
            else
                days = 28;
        }
        return days;
    }
}