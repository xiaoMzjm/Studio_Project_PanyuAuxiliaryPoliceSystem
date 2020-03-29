package com.base.authority.server.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.base.authority.client.common.Enums.AuthorityTypeEnum;
import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.service.AuthorityService;
import com.base.authority.server.manager.AuthorityManager;
import com.base.authority.server.model.AuthorityConvertor;
import com.base.authority.server.model.AuthorityDTO;
import com.base.common.exception.BaseException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/3/29 8:48 PM
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityManager authorityManager;

    @PostConstruct
    public void init(){
        try {
            initAuthority();
        }catch (Exception e) {
        }
    }

    /**
     * 获取菜单树
     * @return
     */
    public List<AuthorityVO> listAll(){
        List<AuthorityDTO> authorityDTOList = authorityManager.findAll();
        List<AuthorityVO> companyVOList = AuthorityConvertor.dto2voList(authorityDTOList);
        companyVOList = this.buildTree(companyVOList , null);
        return companyVOList;
    }

    /**
     * 构建菜单树
     * @param companyVOList
     * @param fatherCode
     * @return
     */
    private List<AuthorityVO> buildTree(List<AuthorityVO> companyVOList, String fatherCode) {
        List<AuthorityVO> list = Lists.newArrayList();

        companyVOList.forEach(vo -> {
            if (Objects.equals(fatherCode, vo.getFatherCode())) {
                vo.setSub(buildTree(companyVOList, vo.getCode()));
                list.add(vo);
            }
        });

        return list;
    }

    /**
     * 添加权限
     * @param name
     * @param authorityType
     * @param fatherCode
     * @throws Exception
     */
    private AuthorityDTO add(String name, String code , String authorityType, String fatherCode) throws Exception{
        if (StringUtils.isEmpty(name)) {
            throw new BaseException("名称为空");
        }
        if (StringUtils.isEmpty(authorityType)) {
            throw new BaseException("类型为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new BaseException("编码为空");
        }
        AuthorityTypeEnum authorityTypeEnum = AuthorityTypeEnum.getByStr(authorityType);
        if (authorityTypeEnum == null) {
            throw new BaseException("类型错误");
        }
        AuthorityDTO authorityDTO = authorityManager.add(name, code, authorityTypeEnum, fatherCode);
        return authorityDTO;
    }



    /**
     * 初始化菜单
     * @throws Exception
     */
    private void initAuthority() throws Exception{
        AuthorityDTO dto = null;
        try {
            dto = add("系统管理", "SystemManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            dto = add("角色管理", "RoleManager", AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {}
        try {
            dto = add("角色绑定管理", "RoleUserBindManager" , AuthorityTypeEnum.Page.getType(),"SystemManager");
        }catch (Exception e) {}


        try {
            dto = add("单位管理", "CompanyManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            dto = add("单位列表", "CompanyList", AuthorityTypeEnum.Page.getType(),"CompanyManager");
        }catch (Exception e) {}



        try {
            dto = add("人员管理", "UserManager", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            dto = add("人员列表", "UserList", AuthorityTypeEnum.Page.getType(),"UserManager");
        }catch (Exception e) {}


        try {
            dto = add("基础数据统计", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            dto = add("年度报表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}
        try {
            dto = add("月度报表", "MonthStatistics" , AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}


        try {
            dto = add("考核管理", "BaseDataStatistics", AuthorityTypeEnum.Page.getType(),null);
        }catch (Exception e) {}
        try {
            dto = add("年度考核列表", "YearStatistics", AuthorityTypeEnum.Page.getType(),"BaseDataStatistics");
        }catch (Exception e) {}
    }










    public void setAuthorityManager(AuthorityManager authorityManager) {
        this.authorityManager = authorityManager;
    }
}
