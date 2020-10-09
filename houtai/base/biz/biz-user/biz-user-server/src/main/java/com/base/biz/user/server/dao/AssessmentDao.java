package com.base.biz.user.server.dao;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.model.AssessmentDO;
import com.base.biz.user.server.model.AwardDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/30 12:19 AM
 */
@Repository
public interface AssessmentDao extends JpaRepository<AssessmentDO,Long> {

    /**
     * 根据用户code删除记录
     * @param userCode
     */
    void deleteByUserCode(String userCode);

    /**
     * 根据时间查询
     * @return
     */
    List<AssessmentDO> findByTimeGreaterThanEqualAndTimeLessThan(Date timeStart, Date timeEnd);
}
