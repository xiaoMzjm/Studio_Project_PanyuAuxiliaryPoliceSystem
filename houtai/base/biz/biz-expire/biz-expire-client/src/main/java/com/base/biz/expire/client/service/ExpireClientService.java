package com.base.biz.expire.client.service;

import java.util.Date;
import java.util.List;

import com.base.biz.expire.client.model.ExpireVO;
import com.sun.xml.internal.rngom.digested.DTextPattern;

/**
 * @author:小M
 * @date:2020/8/2 6:16 PM
 */
public interface ExpireClientService {

    List<ExpireVO> selectByCreateTime(Date start, Date end, Integer type) throws Exception;

    void add(String fileName, String fileUrl, Date time, int type) throws Exception;
}
