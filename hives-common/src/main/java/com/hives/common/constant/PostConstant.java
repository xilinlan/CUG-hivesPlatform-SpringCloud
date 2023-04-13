package com.hives.common.constant;

/**
 * @author zhangtao
 */
public class PostConstant {
    public enum PostEnum{
        FAIL(0,"发布失败"),SUCCESS(1,"发布成功");
        PostEnum(int code,String msg){
            this.code=code;
            this.msg=msg;
        }

        private final int code;

        private final String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


}
