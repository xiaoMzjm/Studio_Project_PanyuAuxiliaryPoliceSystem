package com.base.biz.wages.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.wages.server.model.WagesDO;

/**
 * @author:小M
 * @date:2020/8/30 11:54 AM
 */
public interface BizWagesManager {

    List<WagesDO> batchSave(List<WagesDO> wagesDOList);

    void deleteByIdentityList(List<String> identityList);

    List<WagesDO> listByIdentityList(List<String> identityList);

    List<WagesDO> listByTime(Date time);


}
