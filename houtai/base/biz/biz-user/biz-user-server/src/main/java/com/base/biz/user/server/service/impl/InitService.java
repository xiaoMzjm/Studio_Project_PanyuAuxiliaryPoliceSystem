package com.base.biz.user.server.service.impl;

import javax.annotation.PostConstruct;

import com.base.authority.client.common.Enums.AuthorityTypeEnum;
import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.service.AuthorityService;
import com.base.biz.user.server.manager.impl.BizUserManagerImpl;
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
    private BizUserManagerImpl bizUserManager;
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
        bizUserManager.addSimple("admin","admin", "admin");
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
            vo = authorityService.add("角色权限绑定", "AuthorityManager", AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            vo = authorityService.add("角色管理", "RoleManager", AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("用户角色绑定", "RoleUserBindManager" , AuthorityTypeEnum.Page.getType(),"SystemManager");
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
        try {
            vo = authorityService.add("查询所有单位", "UserListSelectAllCompany", AuthorityTypeEnum.Function.getType(),"UserList");
        }catch (Exception e) {
        }
        try {
            vo = authorityService.add("删除人员", "UserListDeleteUser", AuthorityTypeEnum.Function.getType(),"UserList");
        }catch (Exception e) {}
        //try {
        //    vo = authorityService.add("导出收入证明", "UserListExportShouRu", AuthorityTypeEnum.Function.getType(),"UserList");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("导出在职证明", "UserListExportZaiZhi", AuthorityTypeEnum.Function.getType(),"UserList");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("修改他人资料", "UserListUpdateOthers", AuthorityTypeEnum.Function.getType(),"UserList");
        //}catch (Exception e) {}


        // 基础数据统计
        //try {
        //    vo = authorityService.add("年月报表管理", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("年度报表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("月度报表", "MonthStatistics" , AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        //}catch (Exception e) {}


        // 考核管理
        //try {
        //    vo = authorityService.add("考核管理", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("年度考核列表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        //}catch (Exception e) {}


        // 到期管理
        try {
            vo = authorityService.add("到期管理", "ExpireManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("工作证到期提醒", "ExpireEmployeeCard", AuthorityTypeEnum.Page.getType(),"ExpireManager");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("合同证到期提醒", "ExpireContract", AuthorityTypeEnum.Page.getType(),"ExpireManager");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("退休提醒", "ExpireRetire", AuthorityTypeEnum.Page.getType(),"ExpireManager");
        }catch (Exception e) {}


        // 工资管理
        //try {
        //    vo = authorityService.add("工资管理", "WagesManager", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("工资明细", "WagesDetail", AuthorityTypeEnum.Page.getType(),"WagesManager");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("工资报销封面", "WagesReimbursement", AuthorityTypeEnum.Page.getType(),"WagesManager");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("工资变动", "WagesChange", AuthorityTypeEnum.Page.getType(),"WagesManager");
        //}catch (Exception e) {}


        // 考核管理
        //try {
        //    vo = authorityService.add("年度考核管理", "AssessmentManager", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("年度考核列表", "AssessmentList", AuthorityTypeEnum.Page.getType(),"AssessmentManager");
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("年度考核结果登记", "AssessmentResult", AuthorityTypeEnum.Page.getType(),"AssessmentManager");
        //}catch (Exception e) {}


        // 轮岗管理
        //try {
        //    vo = authorityService.add("轮岗管理", "WorkShiftManger", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("轮岗人员列表", "WorkShiftList", AuthorityTypeEnum.Page.getType(),"WorkShiftManger");
        //}catch (Exception e) {}

        // 图表管理
        //try {
        //    vo = authorityService.add("图表管理", "ChartManger", AuthorityTypeEnum.Page.getType(),null);
        //}catch (Exception e) {}
        //try {
        //    vo = authorityService.add("图表列表", "ChartList", AuthorityTypeEnum.Page.getType(),"ChartManger");
        //}catch (Exception e) {}



        // 防疫管理
        try {
            vo = authorityService.add("防疫管理", "EpidemicManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            vo = authorityService.add("防疫登记和查询", "EpidemicListManager", AuthorityTypeEnum.Page.getType(),"EpidemicManager");
        }catch (Exception e) {
        }
        try {
            vo = authorityService.add("查询所有单位", "EpidemicListManagerSelectAllCompany", AuthorityTypeEnum.Function.getType(),"EpidemicListManager");
        }catch (Exception e) {}
        try {
            vo = authorityService.add("防疫汇总", "EpidemicCollectManager", AuthorityTypeEnum.Page.getType(),"EpidemicManager");
        }catch (Exception e) {}




    }


}
