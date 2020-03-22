package com.base.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:小M
 * @date:2020/3/22 5:45 PM
 */
public class LogUtil {

    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void Info(String msg) {
        logger.info(msg);
    }

    public static void Error(Exception e) {
        logger.error(e.getMessage(),e);
    }
}
