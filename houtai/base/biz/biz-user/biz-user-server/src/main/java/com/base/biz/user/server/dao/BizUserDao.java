package com.base.biz.user.server.dao;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.model.BizUserDO;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/30 12:19 AM
 */
@Repository
public interface BizUserDao extends JpaRepository<BizUserDO,Long> {

    @Query(nativeQuery = true, value="select * from biz_user where 1=1 and if(:name != '',name like CONCAT('%',:name,'%'),1=1)  "
        + "and work_unit_code in(:companyList) ")
    List<BizUserDO> findByNameAndCompanyList(@Param("name") String name, @Param("companyList") List<String> companyList);

    void deleteByCode(String code);

}
