package com.base.common.constant;

/**
 * @author:小M
 * @date:2018/4/29 22:10
 */
public class Result<T> {

    private Boolean isSuccess;

    private T data;

    private String message;

    private String errorCode;

    public static <T> Result <T> success(T t){
        Result result = new Result();
        result.setSuccess(true);
        result.setData(t);
        return result;
    }

    public static <T> Result<T> error(String errorMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(errorMsg);
        return result;
    }

    public static <T> Result<T> error(String errorCode , String errorMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(errorMsg);
        result.setErrorCode(errorCode);
        return result;
    }


    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
