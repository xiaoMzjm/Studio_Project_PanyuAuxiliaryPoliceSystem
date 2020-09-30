package com.base.biz.expire.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.expire.server.model.ExpireDO;

/**
 * @author:小M
 * @date:2020/9/30 11:40 PM
 */
public interface ExpireManager {

    void add(String code, String fileName, String fileUrl, Date time, String remark, int type);

    ExpireDO getByCode(String code);

    List<ExpireDO> listByTime(Date start, Date end, Integer type);

    List<ExpireDO> listByTypeOrderByTimeDesc(Integer type);

    Long delete(String code);
}
