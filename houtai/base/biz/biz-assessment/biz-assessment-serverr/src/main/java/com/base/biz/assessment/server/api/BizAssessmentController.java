package com.base.biz.assessment.server.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.biz.assessment.client.model.AssementPageListVO;
import com.base.biz.assessment.server.service.BizAssessmentService;
import com.base.common.annotation.ResultFilter;
import com.base.common.constant.Result;
import com.base.user.client.model.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 考核管理
 * @author:小M
 * @date:2020/9/20 12:52 AM
 */
@Controller
@ResponseBody
@RequestMapping(value = "/assessment", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(origins = "http://${crossorigin.ip}:8080")
public class BizAssessmentController {

    @Autowired
    private BizAssessmentService bizAssessmentService;

    /**
     * 获取考核列表
     * @return
     */
    @RequestMapping("/list")
    @ResultFilter
    @TokenFilter
    public String list(){
        List<AssementPageListVO> assementPageListVOList = bizAssessmentService.list();
        return JSON.toJSONString(Result.success(assementPageListVOList));
    }

    /**
     * 完成录入
     * @param complateReq
     * @return
     */
    @RequestMapping("/complete")
    @ResultFilter
    @TokenFilter
    public String complete(@RequestBody ComplateReq complateReq){
        bizAssessmentService.complete(complateReq.code);
        return JSON.toJSONString(Result.success(""));
    }

    static class ComplateReq{
        public String code;
    }

    /**
     * http://localhost:8888/wages/assessment/download?code=8-2020-08@1&token=795f69776062407ca8048f8b49ba36b5
     * 下载
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResultFilter
    @TokenFilter
    public void download(HttpServletResponse response, String code) throws Exception{
        //Assert.notNull(code , "code is null");
        //
        //String[] codeArray = code.split("@");
        //Assert.isTrue(codeArray.length == 2, "code错误");
        //String codeStr = codeArray[0];
        //Integer type = Integer.valueOf(codeArray[1]);
        //
        //ExpireVO expireVO = expireClient.getByCode(codeStr);
        //Assert.notNull(expireVO , "文件不存在");
        //
        //Integer index = null;
        //if(WagesChangeTypeEnum.CHANGE.getType().equals(type)) {
        //    index = 0;
        //}else if(WagesChangeTypeEnum.SYSTEM_CHANGE.getType().equals(type)) {
        //    index = 1;
        //}else {
        //    index = 2;
        //}
        //
        //// 名称
        //String[] nameArray = expireVO.getName().split("@");
        //String[] urlArray = expireVO.getFileUrl().split("@");
        //String fileName = nameArray[index] + ".xlsx";
        //fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        //String url = urlArray[index];
        //response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //
        //File file = new File(url);
        //if(!file.exists()) {
        //    throw new RuntimeException("文件不存在，请重新生成");
        //}
        //InputStream inputStream = new FileInputStream(file);
        //BufferedInputStream bis = new BufferedInputStream(inputStream);
        //
        //OutputStream os = response.getOutputStream();
        //
        //byte[] buff = new byte[1024];
        //int i = bis.read(buff);
        //while (i != -1) {
        //    os.write(buff, 0, buff.length);
        //    os.flush();
        //    i = bis.read(buff);
        //}
        //if (bis != null) {
        //    bis.close();
        //}
        //if(inputStream != null) {
        //    inputStream.close();
        //}
    }
}
