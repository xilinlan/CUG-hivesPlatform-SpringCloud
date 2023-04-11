package com.hives.common.constant;

public class UserConstant {
    public enum EmailEnum{
        ILLEGAL(0,"邮箱不合法"),SUCCESS(1,"成功发送"),EXISTS(2 ,"邮箱已存在");

        EmailEnum(int code,String msg){
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
    public enum ValidateEnum{
        FAIL(0,"校验失败"),SUCCESS(1,"校验成功");

        ValidateEnum(int code,String msg){
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

    public enum RegisterEnum{
        FAIL(0,"注册失败"),SUCCESS(1,"注册成功");

        RegisterEnum(int code,String msg){
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

    public enum LoginEnum{
        FAIL(0,"登录失败"),SUCCESS(1,"登录成功");

        LoginEnum(int code,String msg){
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
