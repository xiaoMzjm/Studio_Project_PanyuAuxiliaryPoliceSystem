package com.base.biz.epidemic.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.epidemic.client.model.EpidemicDTO;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/8/2 3:26 PM
 */
public interface EpidemicManager {

    void add(String companyCode, Integer type,
             Integer location, String userCode,
             Date beginTime, Date endTime,
             String detail, String leaderCode, Integer status) throws Exception;

    void update (String code, String companyCode, Integer type,
                 Integer location, String userCode,
                 Date beginTime, Date endTime,
                 String detail, String leaderCode, Integer status) throws Exception;

    List<EpidemicDTO> select(EpidemicSelectParam epidemicSelectParam) throws Exception;

    List<EpidemicDTO> selectInDate(Date date) throws Exception;

    void updateStatus(String code,Integer status) throws Exception;

    void commitAll() throws Exception;

    void delete(String code) throws Exception;

    EpidemicDTO selectByCode(String code) throws Exception;


}
