package com.base.biz.wages.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.base.biz.wages.client.model.WageListVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/8/30 11:54 AM
 */
public interface BizWagesDetailService {

    /**
     * 导入工资明细
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     * @throws Exception
     */
    void importDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception;

    /**
     * 查询工资明细报表
     * @param year
     * @param type
     * @return
     */
    List<WageListVO> list(Integer year, Integer type);
}
