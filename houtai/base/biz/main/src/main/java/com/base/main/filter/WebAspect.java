package com.base.main.filter;

import com.alibaba.fastjson.JSON;

import com.base.common.constant.Result;
import com.base.common.exception.BaseException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2019/4/27 12:15 AM
 */
@Aspect
@Component
public class WebAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebAspect.class);

    // 声明切点
    @Pointcut("@annotation(com.base.common.annotation.ResultFilter)")
    public void annotationPointCut(){}

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object processResult = null;
        try {
            processResult = proceedingJoinPoint.proceed();
            return processResult;
        }
        catch (BaseException be) {
            be.printStackTrace();
            logger.error(be.getMessage(), logger);
            return JSON.toJSONString(Result.error(be.getErrorCode(), be.getMessage()));
        }
        catch (IllegalArgumentException ee) {
            ee.printStackTrace();
            logger.error(ee.getMessage(), logger);
            return JSON.toJSONString(Result.error(ee.getMessage()));
        }
        catch (Throwable e) {
            e.printStackTrace();
            logger.error(e.getMessage(), logger);
            return JSON.toJSONString(Result.error("System Error" , "系统异常"));
        }
    }
}
