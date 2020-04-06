package com.base.biz.user.server.service;

import javax.annotation.PostConstruct;

import com.base.authority.client.common.Enums.AuthorityTypeEnum;
import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.service.AuthorityService;
import com.base.biz.user.server.manager.BizUserManager;
import com.base.user.client.model.UserVO;
import com.base.user.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:小M
 * @date:2020/4/6 7:54 PM
 */
@Service
public class InitService {

    @Autowired
    private UserService userService;
    @Autowired
    private BizUserManager bizUserManager;
    @Autowired
    private AuthorityService authorityService;


    @PostConstruct
    public void init() throws Exception{
        initAdmin();
        initAuthority();
    }

    /**
     * 初始化用户
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public void initAdmin() throws Exception{
        UserVO userVO = userService.findByCode("admin");
        if (userVO != null) {
            return;
        }
        userService.add("admin");
        bizUserManager.addSimple("admin","admin");
    }

    /**
     * 初始化权限
     * @throws Exception
     */
    private void initAuthority() throws Exception{
        AuthorityVO vo = null;


        // 系统管理
        try {
            vo = authorityService.add("系统管理", "SystemManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("角色管理", "RoleManager", AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("角色绑定管理", "RoleUserBindManager" , AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {}


        // 单位管理
        try {
            vo = authorityService.add("单位管理", "CompanyManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("单位列表", "CompanyList", AuthorityTypeEnum.Page.getType(),"CompanyManager");
        }catch (Exception e) {}


        // 人员管理
        try {
            vo = authorityService.add("人员管理", "UserManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("人员列表", "UserList", AuthorityTypeEnum.Page.getType(),"UserManager");
        }catch (Exception e) {}


        // 基础数据统计
        try {
            vo = authorityService.add("基础数据统计", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("年度报表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("月度报表", "MonthStatistics" , AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}


        // 考核管理
        try {
            vo = authorityService.add("考核管理", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("年度考核列表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}
    }


}
