package com.base.biz.user.server.manager;

import java.util.List;

import com.base.biz.user.server.model.AwardDTO;
import com.base.biz.user.server.model.BizUserAddParam.AddParamAward;

/**
 * @author:小M
 * @date:2020/7/5 12:01 AM
 */
public interface AwardManager {

    void add(String userCode, List<AddParamAward> addParamAwardList);

    List<AwardDTO> findByUserCode(String userCode);

    void deleteByUserCode(String userCode);
}
