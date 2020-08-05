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
        + "and if(:companyList != '',work_unit_code in(:companyList),1=1) order by name asc")
    List<BizUserDO> findByNameAndCompanyList(@Param("name") String name, @Param("companyList") List<String> companyList);

    void deleteByCode(String code);


    @Query(nativeQuery = true, value="select * from biz_user where work_card_begin_time >= :start and work_card_begin_time < :end")
    List<BizUserDO> findByWorkCardBeginTime(@Param("start") Date start, @Param("end") Date end);

    @Query(nativeQuery = true, value="select * from biz_user where first_contract_eng_time >= :start and first_contract_eng_time < :end")
    List<BizUserDO> findByFirstContractEngTime(@Param("start") Date start, @Param("end") Date end);

    @Query(nativeQuery = true, value="select * from biz_user where second_contract_eng_time >= :start and second_contract_eng_time < :end")
    List<BizUserDO> findBySecondContractEngTime(@Param("start") Date start, @Param("end") Date end);

    @Query(nativeQuery = true, value="select * from biz_user where third_contract_eng_time >= :start and third_contract_eng_time < :end")
    List<BizUserDO> findByThirdContractEngTime(@Param("start") Date start, @Param("end") Date end);

    @Query(nativeQuery = true, value="select * from biz_user where birthdate >= :start and birthdate < :end and sex = :sex")
    List<BizUserDO> findByBirthDayAndSex(@Param("start") Date start, @Param("end") Date end, @Param("sex")Integer sex);


}
