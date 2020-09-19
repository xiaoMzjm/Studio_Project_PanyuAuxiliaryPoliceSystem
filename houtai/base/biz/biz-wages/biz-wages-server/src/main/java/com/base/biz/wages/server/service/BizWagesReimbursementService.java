package com.base.biz.wages.server.service;

import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/9/20 12:56 AM
 */
public interface BizWagesReimbursementService {

    void importReimbursement(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception;
}
