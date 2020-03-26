package com.base.common.util;

import java.util.Collection;

import com.base.common.exception.BaseException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验器
 * @author:小M
 * @date:2019/1/12 12:25 PM
 */
public class VerifyUtil {

    public static void isTrue(Boolean b , String code, String errMsg , Object... args) throws Exception {
        if(b == null || b == false) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append(errMsg);
            if(args != null && args.length > 0) {
                for(Object arg : args) {
                    if(arg != null) {
                        errorMsg.append(",").append(String.valueOf(arg));
                    }
                }
            }
            throw new BaseException(code, errorMsg.toString());
        }
    }

    /**
     * 非空
     * @param o
     * @param errMsg
     * @param args
     * @throws Exception
     */
    public static void isNotNull(Object o , String errCode, String errMsg , Object... args) throws Exception{
        if(o == null) {
            isTrue(Boolean.FALSE , errCode, errMsg , args);
        }
    }

    /**
     * 非空 & 长度非0
     * @param o
     * @param errMsg
     * @param args
     * @throws Exception
     */
    public static void isNotEmpty(Object o , String errCode, String errMsg, Object... args) throws Exception{
        if(o == null) {
            isTrue(Boolean.FALSE , errCode, errMsg , args);
        }
        if(o instanceof String) {
            String s = (String)o;
            if(StringUtils.isEmpty(s)) {
                isTrue(Boolean.FALSE , errCode, errMsg , args);
            }
        }
        if(o instanceof Collection) {
            Collection c = (Collection)o;
            if(CollectionUtils.isEmpty(c)) {
                isTrue(Boolean.FALSE , errCode, errMsg , args);
            }
        }
        if(o.getClass().isArray()) {
            Object[] os = (Object[])o;
            if(ArrayUtils.isEmpty(os)){
                isTrue(Boolean.FALSE , errCode, errMsg , args);
            }
        }
    }

}
