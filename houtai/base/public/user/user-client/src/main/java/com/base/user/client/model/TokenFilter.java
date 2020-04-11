package com.base.user.client.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:小M
 * @date:2019/2/17 7:36 PM
 */
@Target({ElementType.METHOD , ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenFilter {
}
