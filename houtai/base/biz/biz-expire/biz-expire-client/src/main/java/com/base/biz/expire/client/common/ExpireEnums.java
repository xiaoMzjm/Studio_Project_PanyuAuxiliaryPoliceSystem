package com.base.biz.expire.client.common;

/**
 * @author:小M
 * @date:2020/5/24 2:43 PM
 */
public class ExpireEnums {

    public enum ExpireType {
        EmployeeCard(1, "工作证"),
        Contract(2, "合同"),
        Retire(3, "退休")
        ;
        private Integer code;
        private String name;

        ExpireType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
