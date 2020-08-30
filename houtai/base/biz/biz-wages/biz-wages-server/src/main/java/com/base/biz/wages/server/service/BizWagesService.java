package com.base.biz.wages.server.service;

import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/8/30 11:54 AM
 */
public interface BizWagesService {

    void importThreeDetail(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception;
}
