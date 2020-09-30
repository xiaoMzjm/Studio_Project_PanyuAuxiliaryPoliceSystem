package com.base.biz.assessment.server.service;

import java.util.List;

import com.base.biz.assessment.client.model.AssementPageListVO;

/**
 * @author:小M
 * @date:2020/9/30 4:39 PM
 */
public interface BizAssessmentService {

    List<AssementPageListVO> list();

    void complete(String code);
}
