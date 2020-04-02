package com.base.biz.user.server.manager;

import java.util.Optional;

import com.base.biz.user.server.dao.BizUserDao;
import com.base.biz.user.server.model.BizUserConvertor;
import com.base.biz.user.server.model.BizUserDO;
import com.base.biz.user.server.model.BizUserDTO;
import com.base.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/3/30 12:25 AM
 */
@Component
public class BizUserManager {

    @Autowired
    private BizUserDao bizUserDao;

    /**
     * 根据账号密码查找用户
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public BizUserDTO findByAccountAndCode(String account, String password) throws Exception{

        BizUserDO bizUserDO = new BizUserDO();
        bizUserDO.setIdentityCard(account);
        bizUserDO.setPassword(password);

        Example<BizUserDO> example = Example.of(bizUserDO);
        Optional<BizUserDO> optional = bizUserDao.findOne(example);
        if (!optional.isPresent()) {
            throw new BaseException("账号或密码错误");
        }

        bizUserDO = optional.get();
        return BizUserConvertor.do2dto(bizUserDO);
    }








    public void setBizUserDao(BizUserDao bizUserDao) {
        this.bizUserDao = bizUserDao;
    }
}
