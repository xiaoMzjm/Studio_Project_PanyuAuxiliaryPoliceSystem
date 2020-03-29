package com.base.user.server.common;

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
        REGISTER_CODE_NULL("CODE_NULL" , "编码空"),
        REGISTER_REPEAT("REGISTER_REPEAT","账户已被注册"),

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
