package com.base.resource.client.service;

import java.util.*;
import com.base.resource.client.model.ResourceVO;

/**
 * @author:小M
 * @date:2020/4/5 9:23 PM
 */
public interface ResourceService {

    Map<String,ResourceVO> findByNameList(List<String> nameList);
}
