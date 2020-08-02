package com.base.biz.expire.server.service;

import java.io.InputStream;
import java.util.List;

import com.base.biz.expire.client.model.ExpireVO;

/**
 * @author:小M
 * @date:2020/8/2 6:19 PM
 */
public interface ExpireService {

    List<ExpireVO> getByTime(Integer year, Integer type) throws Exception;

    String createEmployeeCard(Integer year, Integer month, InputStream inputStream, Boolean replace ) throws Exception;

    String createContract(Integer year, Integer month, InputStream inputStream, Boolean replace) throws Exception;

    String createRetire(Integer year, Integer month, InputStream inputStream, Boolean replace) throws Exception;
}
