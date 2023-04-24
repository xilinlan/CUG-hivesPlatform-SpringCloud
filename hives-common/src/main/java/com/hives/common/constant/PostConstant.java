package com.hives.common.constant;

/**
 * @author zhangtao
 */
public class PostConstant {
    public enum PostEnum{
        /**
         * post发布信息返回
         */
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

    public enum PostTypeEnum{
        /**
         * post发布信息返回
         */
        COMMON(0,"普通帖子"),VIDEO(1,"视频帖子");
        PostTypeEnum(int code,String msg){
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
