package com.hives.common.constant;

/**
 * @Author: zhangtao
 * @Date: 2023/4/6 14:39
 */
public interface ResultStatusCode{

    /**
     * 获取返回状态码
     * @return code
     */
    int getCode();

    /**
     * 获取返回消息
     * @return msg
     */
    String getMsg();
}
