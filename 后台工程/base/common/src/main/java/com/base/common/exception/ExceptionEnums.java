package com.base.common.exception;

/**
 * 错误枚举
 * @author:小M
 * @date:2019/1/12 12:31 PM
 */
public enum ExceptionEnums {

    SYSTEM_ERROR("SYSTEM_ERROR","系统错误"),

    /**
     * 广告
     */
    SAVE_AD_DO_IS_NULL("SAVE_AD_DO_IS_NULL" , "保存失败，advertisementDO为空"),
    SAVE_AD_BIZ_KEY_IS_NULL("SAVE_AD_BIZ_KEY_IS_NULL" , "保存失败，bizKey为空"),
    SAVE_AD_TYPE_IS_NULL("SAVE_AD_TYPE_IS_NULL" , "保存失败，type为空"),
    SAVE_AD_PIC_OR_VIDEO_IS_NULL("SAVE_AD_PIC_OR_VIDEO_IS_NULL" , "保存失败，图片或视频为空"),

    FIND_BIZ_KEY_IS_NULL("FIND_BIZ_KEY_IS_NULL","查询bizKey为空"),
    FIND_TYPE_IS_NULL("FIND_TYPE_IS_NULL","查询type为空"),

    /**
     * 建议和反馈
     */
    SAVE_BIZ_KEY_IS_NULL("SAVE_BIZ_KEY_IS_NULL" , "保存失败，bizKey为空"),
    SAVE_TYPE_IS_NULL("SAVE_TYPE_IS_NULL" , "保存失败，type为空"),
    SAVE_SUGGEST_IS_NULL("SAVE_SUGGEST_IS_NULL" , "保存失败，suggest为空"),
    SAVE_USER_ID_IS_NULL("SAVE_USER_ID_IS_NULL" , "保存失败，userId为空"),

        ;

    private String code;
    private String msg;

    ExceptionEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
