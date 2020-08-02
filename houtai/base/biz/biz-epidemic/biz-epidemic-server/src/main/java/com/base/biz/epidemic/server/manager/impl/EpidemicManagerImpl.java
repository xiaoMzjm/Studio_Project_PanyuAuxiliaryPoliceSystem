package com.base.biz.epidemic.server.manager.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.base.biz.epidemic.client.common.EpidemicEnums.EpidemicStatusEnum;
import com.base.biz.epidemic.client.model.EpidemicDTO;
import com.base.biz.epidemic.server.dao.EpidemicDAO;
import com.base.biz.epidemic.server.manager.EpidemicManager;
import com.base.biz.epidemic.server.model.EpidemicDO;
import com.base.biz.epidemic.server.model.EpidemicSelectParam;
import com.base.biz.epidemic.server.model.convertor.EpidemicConvertor;
import com.base.common.exception.BaseException;
import com.base.common.util.UUIDUtil;
import com.google.j2objc.annotations.AutoreleasePool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/8/2 3:26 PM
 */
@Component
public class EpidemicManagerImpl implements EpidemicManager {
    @Autowired
    private EpidemicDAO epidemicDAO;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    public void add(String companyCode, Integer type, Integer location, String userCode, Date beginTime, Date endTime,
                    String detail, String leaderCode, Integer status) throws Exception {
        EpidemicDO epidemicDO = new EpidemicDO();
        epidemicDO.setCode(UUIDUtil.get());
        Date now = new Date();
        epidemicDO.setGmtCreate(now);
        epidemicDO.setGmtModified(now);
        epidemicDO.setCompanyCode(companyCode);
        epidemicDO.setType(type);
        epidemicDO.setLocation(location);
        epidemicDO.setBeginTime(beginTime);
        epidemicDO.setEndTime(endTime);
        epidemicDO.setDetail(detail);
        epidemicDO.setLeaderCode(leaderCode);
        epidemicDO.setStatus(status);
        epidemicDO.setUserCode(userCode);
        epidemicDAO.save(epidemicDO);
    }

    @Override
    public void update(String code, String companyCode, Integer type, Integer location, String userCode, Date beginTime,
                       Date endTime, String detail, String leaderCode, Integer status) throws Exception {
        EpidemicDO findEpidemicDO = epidemicDAO.findByCode(code);
        if(findEpidemicDO == null) {
            throw new BaseException("记录不存在:" + code);
        }
        EpidemicDO epidemicDO = new EpidemicDO();
        epidemicDO.setId(findEpidemicDO.getId());
        epidemicDO.setCode(findEpidemicDO.getCode());
        Date now = new Date();
        epidemicDO.setGmtModified(now);
        epidemicDO.setGmtCreate(findEpidemicDO.getGmtCreate());
        epidemicDO.setCompanyCode(companyCode);
        epidemicDO.setType(type);
        epidemicDO.setLocation(location);
        epidemicDO.setBeginTime(beginTime);
        epidemicDO.setEndTime(endTime);
        epidemicDO.setDetail(detail);
        epidemicDO.setLeaderCode(leaderCode);
        epidemicDO.setStatus(status);
        epidemicDO.setUserCode(userCode);
        epidemicDAO.save(epidemicDO);
    }

    @Override
    public List<EpidemicDTO> select(EpidemicSelectParam param) throws Exception {
        String sql = "select * from epidemic where 1=1 ";
        if(CollectionUtils.isNotEmpty(param.getCompanyCodeList())) {
            sql += " and company_code in " + inStrList(param.getCompanyCodeList());
        }
        if(CollectionUtils.isNotEmpty(param.getUserCodeList())) {
            sql += " and user_code in " + inStrList(param.getUserCodeList());
        }
        if(CollectionUtils.isNotEmpty(param.getTypeList())) {
            sql += " and type in " + inIntegerList(param.getTypeList());
        }
        if(CollectionUtils.isNotEmpty(param.getLocationList())) {
            sql += " and location in " + inIntegerList(param.getLocationList());
        }
        if(StringUtils.isNotEmpty(param.getBeginTime())) {
            sql += " and begin_time >= '" + param.getBeginTime()+"'";
        }
        if(StringUtils.isNotEmpty(param.getEndTime())) {
            sql += " and end_time <= '" + param.getEndTime()+"'";
        }
        if(CollectionUtils.isNotEmpty(param.getStatusList())) {
            sql += " and status in " + inIntegerList(param.getStatusList());
        }

        System.out.println("sql = " + sql);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(sql, EpidemicDO.class);
        List<EpidemicDO> bizUserDOList = query.getResultList();
        entityManager.close();

        return EpidemicConvertor.do2dtoList(bizUserDOList);
    }

    @Override
    public void updateStatus(String code, Integer status) throws Exception {
        EpidemicDO findEpidemicDO = epidemicDAO.findByCode(code);
        if(findEpidemicDO == null) {
            throw new BaseException("记录不存在:" + code);
        }
        findEpidemicDO.setStatus(status);
        epidemicDAO.save(findEpidemicDO);
    }

    @Override
    public void delete(String code) throws Exception {
        EpidemicDO findEpidemicDO = epidemicDAO.findByCode(code);
        if(findEpidemicDO == null) {
            throw new BaseException("记录不存在:" + code);
        }
        epidemicDAO.delete(findEpidemicDO);
    }

    @Override
    public EpidemicDTO selectByCode(String code) throws Exception {
        EpidemicDO findEpidemicDO = epidemicDAO.findByCode(code);
        if(findEpidemicDO == null) {
            return null;
        }
        return EpidemicConvertor.do2dto(findEpidemicDO);
    }

    private String inStrList(List<String> list){
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(String s : list) {
            sb.append("'" + s + "'").append(",");
        }
        String str = sb.toString();
        if(str.endsWith(",")) {
            str = str.substring(0,str.length()-1);
        }
        return "(" + str + ")";
    }

    private String inIntegerList(List<Integer> list){
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(Integer i : list) {
            sb.append("" + i + "").append(",");
        }
        String str = sb.toString();
        if(str.endsWith(",")) {
            str = str.substring(0,str.length()-1);
        }
        return "(" + str + ")";
    }
}
