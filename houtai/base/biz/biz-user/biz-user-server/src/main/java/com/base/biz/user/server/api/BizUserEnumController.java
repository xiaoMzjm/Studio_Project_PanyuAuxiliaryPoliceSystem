package com.base.biz.user.server.api;

import java.util.*;
import com.alibaba.fastjson.JSON;

import com.base.biz.user.client.common.Enums.AuthorizedStrengthTypeEnum;
import com.base.biz.user.client.common.Enums.DimssionTypeEnum;
import com.base.biz.user.client.common.Enums.DrivingTypeEnum;
import com.base.biz.user.client.common.Enums.DueContractEnum;
import com.base.biz.user.client.common.Enums.EducationEnum;
import com.base.biz.user.client.common.Enums.EnrollWayEnum;
import com.base.biz.user.client.common.Enums.ExservicemanEnum;
import com.base.biz.user.client.common.Enums.JobCategoryEnum;
import com.base.biz.user.client.common.Enums.JobGradeEnum;
import com.base.biz.user.client.common.Enums.MaritalStatusEnum;
import com.base.biz.user.client.common.Enums.NationEnum;
import com.base.biz.user.client.common.Enums.PersonnelTypeEnum;
import com.base.biz.user.client.common.Enums.PlaceOfWorkEnum;
import com.base.biz.user.client.common.Enums.PoliticalLandscapeEnum;
import com.base.biz.user.client.common.Enums.SexEnum;
import com.base.biz.user.client.common.Enums.SpecialPeopleEnum;
import com.base.biz.user.client.common.Enums.TreatmentGradeEnum;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.model.TokenFilter;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:小M
 * @date:2020/4/6 1:20 PM
 */
@Api(description = "人员管理枚举")
@Controller
@ResponseBody
@RequestMapping(value = "/user/enum/" , produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "http://192.168.1.4:8080")
public class BizUserEnumController {

    @ResultFilter
    @TokenFilter
    @RequestMapping("all")
    public String enums(){

        Map<String,Object> map = new HashMap<>();
        map.put("nation", NationEnum.getAll());
        map.put("politicallandscape", PoliticalLandscapeEnum.getAll());
        map.put("drivingtype", DrivingTypeEnum.getAll());
        map.put("exserviceman", ExservicemanEnum.getAll());
        map.put("sex", SexEnum.getAll());
        map.put("education", EducationEnum.getAll());
        map.put("maritalstatus", MaritalStatusEnum.getAll());
        map.put("personneltype", PersonnelTypeEnum.getAll());
        map.put("authorizedstrengthype", AuthorizedStrengthTypeEnum.getAll());
        map.put("placeofwork", PlaceOfWorkEnum.getAll());
        map.put("jobgrade", JobGradeEnum.getAll());
        map.put("treatmentgrade", TreatmentGradeEnum.getAll());
        map.put("enrollway", EnrollWayEnum.getAll());
        map.put("dimssiontype", DimssionTypeEnum.getAll());
        map.put("jobcategory", JobCategoryEnum.getAll());
        map.put("specialpeople", SpecialPeopleEnum.getAll());
        map.put("duecontract", DueContractEnum.getAll());

        return JSON.toJSONString(Result.success(map));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("nation")
    public String nation(){
        return JSON.toJSONString(Result.success(NationEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("politicallandscape")
    public String politicallandscape(){
        return JSON.toJSONString(Result.success(PoliticalLandscapeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("drivingtype")
    public String drivingtype(){
        return JSON.toJSONString(Result.success(DrivingTypeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("exserviceman")
    public String exserviceman(){
        return JSON.toJSONString(Result.success(ExservicemanEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("sex")
    public String sex(){
        return JSON.toJSONString(Result.success(SexEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("education")
    public String education(){
        return JSON.toJSONString(Result.success(EducationEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("maritalstatus")
    public String maritalstatus(){
        return JSON.toJSONString(Result.success(MaritalStatusEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("personneltype")
    public String personneltype(){
        return JSON.toJSONString(Result.success(PersonnelTypeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("authorizedstrengthype")
    public String authorizedstrengthype(){
        return JSON.toJSONString(Result.success(AuthorizedStrengthTypeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("placeofwork")
    public String placeofwork(){
        return JSON.toJSONString(Result.success(PlaceOfWorkEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("jobgrade")
    public String jobgrade(){
        return JSON.toJSONString(Result.success(JobGradeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("treatmentgrade")
    public String treatmentgrade(){
        return JSON.toJSONString(Result.success(TreatmentGradeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("enrollway")
    public String enrollway(){
        return JSON.toJSONString(Result.success(EnrollWayEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("dimssiontype")
    public String dimssiontype(){
        return JSON.toJSONString(Result.success(DimssionTypeEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("jobcategory")
    public String jobcategory(){
        return JSON.toJSONString(Result.success(JobCategoryEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("specialpeople")
    public String specialpeople(){
        return JSON.toJSONString(Result.success(SpecialPeopleEnum.getAll()));
    }

    @ResultFilter
    @TokenFilter
    @RequestMapping("duecontract")
    public String duecontract(){
        return JSON.toJSONString(Result.success(DueContractEnum.getAll()));
    }

}
