package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.AwardDao;
import com.base.biz.user.server.model.AwardDO;
import com.base.biz.user.server.model.BizUserAddParam.AddParamAward;
import com.base.biz.user.server.model.FamilyMemberDO;
import com.base.common.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/4/9 1:56 AM
 */
@Component
public class AwardManager {

    @Autowired
    private AwardDao awardDao;

    public void add(String userCode, List<AddParamAward> addParamAwardList) {
        if (CollectionUtils.isEmpty(addParamAwardList)) {
            return;
        }
        for (AddParamAward addParamAward : addParamAwardList) {
            AwardDO awardDO = new AwardDO();
            Date now = new Date();
            awardDO.setGmtCreate(now);
            awardDO.setGmtModified(now);
            awardDO.setUserCode(userCode);

            awardDO.setName(addParamAward.name);
            awardDO.setTime(DateUtil.convert2Date(addParamAward.time,BizUserConstant.DateFormat));
            awardDO.setReason(addParamAward.reason);
            awardDO.setCompany(addParamAward.company);
            awardDao.save(awardDO);
        }
    }
}
