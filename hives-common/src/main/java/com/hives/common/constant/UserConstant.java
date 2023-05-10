package com.hives.common.constant;

/**
 * @author zhangtao
 */
public class UserConstant {
    public enum EmailEnum {
        /**
         * 邮箱枚举
         */
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
        /**
         * 校验枚举
         */
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
        /**
         * 注册枚举
         */
        FAIL(0,"注册失败"),SUCCESS(1,"注册成功"),EXISTS(2,"邮箱已存在");

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
        /**
         * 登录枚举
         */
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

    public enum UdpWEnum{
        /**
         * 修改密码枚举
         */
        FAIL(0,"修改失败"),SUCCESS(1,"修改成功"),NOTEXIST(2,"用户不存在"),REPEAT(3,"密码重复");

        UdpWEnum(int code,String msg){
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

    public enum UdpPEnum{
        /**
         * 修改密码枚举
         */
        FAIL(0,"修改失败"),SUCCESS(1,"修改成功"),NOTEXIST(2,"用户不存在");

        UdpPEnum(int code,String msg){
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
