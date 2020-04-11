package com.base.common.util;

import java.util.UUID;

/**
 * @author:小M
 * @date:2019/2/17 7:20 PM
 */
public class UUIDUtil {

    public static String get(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.get());
    }
}
