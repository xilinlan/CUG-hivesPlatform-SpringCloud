package com.hives.common.exception;

/**
 * @Author: zhangtao
 * @Date: 2023/4/6 14:39
 */
public enum BizCodeEnume {
    /**
     * 系统未知异常
     */
    UNKOWN_EXCEPTION(10000,"系统未知异常"),
    /**
     * 参数格式校验失败
     */
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    /**
     * 未知异常
     */
    SYSTEM_BUSY_EXCEPTION(11000,"系统繁忙，请稍后再试！");


    private final int code;
    private final String msg;
    BizCodeEnume(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
