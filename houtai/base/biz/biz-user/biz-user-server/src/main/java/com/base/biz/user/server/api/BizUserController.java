package com.base.biz.user.server.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.authority.client.model.AuthorityVO;
import com.base.authority.client.service.AuthorityService;
import com.base.biz.user.client.model.BizUserDetailVO;
import com.base.biz.user.client.model.BizUserLoginVO;
import com.base.biz.user.client.model.BizUserPageListVO;
import com.base.biz.user.server.model.BizUserAddParam;
import com.base.biz.user.server.model.SuperPageListParam;
import com.base.biz.user.server.model.UpdateParam;
import com.base.biz.user.server.service.BizUserInnerSerivce;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.common.util.UUIDUtil;
import com.base.resource.client.common.Constant;
import com.base.user.client.common.UserConstant;
import com.base.user.client.model.TokenFilter;
import com.base.user.client.model.UserVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/3/30 12:49 AM
 */
@Api(description = "用户接口")
@Controller
@RequestMapping(value = "user", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://192.168.1.5:8080")
public class BizUserController {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizUserInnerSerivce bizUserService;

    @ApiOperation(value = "人员列表页面" ,  notes="人员列表页面")
    @GetMapping("")
    public String userListView(){
        return "userlist";
    }

    @ResultFilter
    @ApiOperation(value = "登录" ,  notes="登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletResponse response,
                        @RequestBody LoginParam param) throws Exception{
        String account = param.account;
        String password = param.password;
        BizUserLoginVO bizUserLoginVO = bizUserService.login(account, password);

        Cookie cookie = new Cookie("token",bizUserLoginVO.getToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return JSON.toJSONString(Result.success(bizUserLoginVO));
    }
    static class LoginParam{
        @ApiParam(name="账号",value="account",required=true)
        public String account;
        @ApiParam(name="密码",value="password",required=true)
        public String password;
    }



    @Autowired
    private AuthorityService authorityService;
    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "获取权限列表" ,  notes="获取权限列表")
    @RequestMapping(value = "/getauthority", method = RequestMethod.POST)
    @ResponseBody
    public String getAuthority(HttpServletRequest request){

        UserVO userVO = (UserVO)request.getAttribute(UserConstant.REQUEST_USER);
        List<AuthorityVO> authorityVOList = authorityService.listAll();

        return JSON.toJSONString(Result.success(authorityVOList));
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "添加人员" , notes = "添加人员")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody BizUserAddParam param) throws Exception{
        bizUserService.add(param);
        return JSON.toJSONString(Result.success(""));
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "普通查询人员" , notes = "普通查询人员")
    @RequestMapping(value = "/pagelist", method = RequestMethod.POST)
    @ResponseBody
    public String pagelist(@RequestBody PageListParam param) throws Exception{
        List<BizUserPageListVO> bizUserPageListVOList = bizUserService.findByNameAndCompanyCodeList(param.name, param.companyCodeList);
        return JSON.toJSONString(Result.success(bizUserPageListVOList));
    }
    static class PageListParam {
        @ApiParam(name="姓名",value="name")
        public String name;
        @ApiParam(name="单位code列表",value="companyCodeList")
        public List<String> companyCodeList;
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "查询个人详情" , notes = "查询个人详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public String detail(@RequestBody DetailParam param) throws Exception{
        BizUserDetailVO bizUserDetailVO = bizUserService.findByCode(param.code);
        return JSON.toJSONString(Result.success(bizUserDetailVO));
    }
    public static class DetailParam{
        @ApiParam(name="code",value="code")
        public String code;
    }


    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "删除人员" , notes = "删除人员")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestBody DeleteParam param)throws Exception {
        bizUserService.deleteByCode(param.code, true);
        return JSON.toJSONString(Result.success(""));
    }
    public static class DeleteParam{
        @ApiParam(name="code",value="code")
        public String code;
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "删除人员" , notes = "删除人员")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody UpdateParam param) throws Exception {
        bizUserService.update(param);
        return JSON.toJSONString(Result.success(""));
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "高级查询" , notes = "高级查询")
    @RequestMapping(value = "/superpagelist", method = RequestMethod.POST)
    @ResponseBody
    public String superPageList(@RequestBody SuperPageListParam param) throws Exception {
        List<BizUserPageListVO> bizUserPageListVOList = bizUserService.superPageList(param);
        return JSON.toJSONString(Result.success(bizUserPageListVOList));
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "下载导入模板" , notes = "下载导入模板")
    @RequestMapping(value = "/downloadimportdemo", method = RequestMethod.GET)
    @ResponseBody
    public void downloadImportDemo(HttpServletResponse response) throws Exception {


        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static/file";
        File file = new File(path + "/" + "导入模板.xlsx");
        String fileName = new String("导入模板.xlsx".getBytes("UTF-8"),"ISO-8859-1");

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        Long contentLength = file.length();
        response.setHeader("content-length", contentLength + "");

        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[1024];
        int i = bis.read(buff);
        while (i != -1) {
            os.write(buff, 0, buff.length);
            os.flush();
            i = bis.read(buff);
        }
        if (bis != null) {
            bis.close();
        }
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "导入人员" , notes = "导入人员")
    @RequestMapping(value = "/importuser", method = RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
    @ResponseBody
    public String importuser(@RequestParam(value = "file", required = false)MultipartFile file) throws Exception{
        if (file == null) {
            return JSON.toJSONString(Result.error("未上传文件"));
        }

        if(StringUtils.isEmpty(diskStaticUrl)) {
            return JSON.toJSONString(Result.error("ResourceStaticUrl is null"));
        }

        String oriName = file.getOriginalFilename();

        String diskPath = diskStaticUrl + "files";

        // 创建目录
        File pathFolder = new File(diskPath);
        if (!pathFolder.exists()) {
            pathFolder.mkdirs();
        }

        // 保存文件
        String url = "";
        try {
            String name = UUIDUtil.get();
            url = diskPath + "/" + name + oriName.substring(oriName.lastIndexOf("."),oriName.length());
            file.transferTo(new File(url));
            File f = new File(url);
            // 导入
            bizUserService.importUser(new FileInputStream(f));
        }finally {
            File f = new File(url);
            if(f.exists()) {
                f.delete();
            }
        }

        return JSON.toJSONString(Result.success(""));
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "导入相片" , notes = "导入相片")
    @RequestMapping(value = "/importimage", method = RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
    @ResponseBody
    public String importimage(@RequestParam(value = "file", required = false)MultipartFile file) throws Exception{
        if (file == null) {
            return JSON.toJSONString(Result.error("未上传文件"));
        }

        if(StringUtils.isEmpty(diskStaticUrl)) {
            return JSON.toJSONString(Result.error("ResourceStaticUrl is null"));
        }

        String oriName = file.getOriginalFilename();

        String diskPath = diskStaticUrl + "files";

        // 创建目录
        File pathFolder = new File(diskPath);
        if (!pathFolder.exists()) {
            pathFolder.mkdirs();
        }

        // 保存文件
        String url = "";
        List<String> successList = Lists.newArrayList();
        try {
            String name = UUIDUtil.get();
            url = diskPath + "/" + name + oriName.substring(oriName.lastIndexOf("."),oriName.length());
            file.transferTo(new File(url));
            File f = new File(url);
            successList = bizUserService.importImage(f);
        }finally {
            File f = new File(url);
            if(f.exists()) {
                f.delete();
            }
        }
        if(CollectionUtils.isNotEmpty(successList)) {
            return JSON.toJSONString(Result.success(String.format("成功导入了%d个头像，身份证如下：%s",successList.size(), successList.toString() )));
        }else {
            return JSON.toJSONString(Result.success(""));
        }
    }

    @TokenFilter
    @ResultFilter
    @ApiOperation(value = "导出个人简历" , notes = "导出个人简历")
    @RequestMapping(value = "/exportuser", method = RequestMethod.GET)
    @ResponseBody
    public String exportuser( String code, HttpServletResponse response) throws Exception{
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static/file";
        File file = new File(path + "/" + "个人简历.docx");
        File wordFile = null;
        try {
            wordFile = bizUserService.exportUser(code, file);

            if (wordFile != null && wordFile.exists()) {
                response.setHeader("Content-Disposition", "attachment; filename=" + new String("个人简历.docx".getBytes("UTF-8"),"ISO8859-1"));
                Long contentLength = wordFile.length();
                response.setHeader("content-length", contentLength + "");

                OutputStream os = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(wordFile));
                byte[] buff = new byte[1024];
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
                if (bis != null) {
                    bis.close();
                }
            }

        }finally {
            if (wordFile != null && wordFile.exists()) {
                wordFile.delete();
            }
        }

        return JSON.toJSONString(Result.success(""));

    }
}
