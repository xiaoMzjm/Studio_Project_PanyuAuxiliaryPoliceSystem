package com.base.user.common;

/**
 * @author:小M
 * @date:2020/2/9 9:14 PM
 */
public class Constant {


    public enum ErrorCode {

        // token
        TOKEN_NULL("TOKEN_NULL", "Token为空"),
        TOKEN_ERROR("TOKEN_ERROR", "TOKEN错误"),
        TOKEN_EXPIRE("TOKEN_EXPIRE", "TOKEN过期"),

        // 注册
        REGISTER_ACCOUNT_NULL("ACCOUNT_NULL" , "账号为空"),
        REGISTER_PASSWORD_NULL("PASSWORD_NULL" , "密码为空"),
        REGISTER_REPEAT("REGISTER_REPEAT","账户已被注册"),

        // 登录
        LOGIN_ACCOUNT_NULL("ACCOUNT_NULL" , "账号为空"),
        LOGIN_PASSWORD_NULL("PASSWORD_NULL" , "密码为空"),
        LOGIN_INVALID_ACCOUNT_OR_PASSWORD("INVALID_ACCOUNT_OR_PASSWORD", "账号或密码错误"),

        // 用户
        CAN_NOT_FIND_USER_BY_ID("CAN_NOT_FIND_USER_BY_ID" , "通过id无法查询到用户")
        ;

        ErrorCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
