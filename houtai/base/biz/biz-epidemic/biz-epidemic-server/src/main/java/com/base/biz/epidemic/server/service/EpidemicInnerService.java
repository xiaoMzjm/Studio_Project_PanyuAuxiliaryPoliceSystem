package com.base.biz.epidemic.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.base.biz.epidemic.client.model.EpidemicStatisticsVO;
import com.base.biz.epidemic.client.model.EpidemicVO;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;

/**
 * @author:小M
 * @date:2020/8/2 4:36 PM
 */
public interface EpidemicInnerService {

    void add(String companyCode, Integer type,
             Integer location, String userCode,
             String beginTime, String endTime,
             String detail, String leaderCode) throws Exception;

    List<EpidemicVO> select(EpidemicSelectParam epidemicSelectParam) throws Exception;

    void confirm(String code) throws Exception;

    void delete(String code) throws Exception;

    void update(String code, String companyCode, Integer type,
             Integer location, String userCode,
             String beginTime, String endTime,
             String detail, String leaderCode) throws Exception;


    void createStatistics(InputStream zhengGongBan, InputStream shiJu , String date) throws Exception;

    List<EpidemicStatisticsVO> selectStatistics(String date) throws Exception;
}
