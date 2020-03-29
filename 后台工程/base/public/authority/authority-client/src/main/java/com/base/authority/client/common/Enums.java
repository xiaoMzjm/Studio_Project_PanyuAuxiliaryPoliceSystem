package com.base.authority.client.common;

/**
 * @author:小M
 * @date:2020/3/29 8:30 PM
 */
public class Enums {

    public enum AuthorityTypeEnum {
        Page("page", "页面"),
        Function("function", "功能")

        ;
        private String type;
        private String desc;

        AuthorityTypeEnum(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public static AuthorityTypeEnum getByStr(String type) {
            for (AuthorityTypeEnum authorityTypeEnum : AuthorityTypeEnum.values()) {
                if (authorityTypeEnum.getType().equals(type)) {
                    return authorityTypeEnum;
                }
            }
            return null;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
