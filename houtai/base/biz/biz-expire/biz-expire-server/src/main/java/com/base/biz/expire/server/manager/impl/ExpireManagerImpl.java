package com.base.biz.expire.server.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.base.biz.expire.server.dao.ExpireDAO;
import com.base.biz.expire.server.manager.ExpireManager;
import com.base.biz.expire.server.model.ExpireDO;
import com.base.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/5/24 3:02 PM
 */

@Component
public class ExpireManagerImpl implements ExpireManager {

    @Autowired
    private ExpireDAO expireDAO;

    @Override
    public void add(String code, String fileName, String fileUrl, Date time, String remark, int type){

        Date now = new Date();
        ExpireDO expireDO = getByCode(code);
        if(expireDO == null) {
            expireDO = new ExpireDO();
            expireDO.setGmtCreate(now);
        }

        expireDO.setCode(code);
        expireDO.setFileName(fileName);
        expireDO.setFileUrl(fileUrl);

        expireDO.setGmtModified(now);
        expireDO.setTime(time);
        expireDO.setType(type);
        expireDO.setRemark(remark);
        expireDAO.save(expireDO);
        return ;
    }

    @Override
    public ExpireDO getByCode(String code){
        ExpireDO expireDO = new ExpireDO();
        expireDO.setCode(code);
        Example<ExpireDO> example = Example.of(expireDO);
        Optional<ExpireDO> optional = expireDAO.findOne(example);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<ExpireDO> listByTime(Date start, Date end, Integer type) {
        return expireDAO.getByTime(start, end, type);
    }

    @Override
    public  List<ExpireDO> listByTypeOrderByTimeDesc(Integer type) {
        return expireDAO.findByTypeOrderByTimeDesc(type);
    }

    @Override
    public Long delete(String code) {
        return expireDAO.deleteByCode(code);
    }
}
