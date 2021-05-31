package com.lyy.uploadfile.Utils;

/**
 * 用于Service层和Controller层之间的数据传输
 * @param <T>
 */
public class Message<T> {
    private boolean success;
    private String msg;
    private T res;

    private Message(boolean success, String msg, T res) {
        this.success = success;
        this.msg = msg;
        this.res = res;
    }

    public static <T>Message success(String msg, T res){
        return new Message<>(true, msg, res);
    }

    public static <T>Message success(String msg){
        return Message.success(msg, null);
    }

    public static Message success(Page page){
        Message<Page> m = new Message<Page>(true, "搜索成功", page);
        return m;
    }

    public static <T>Message fail(String msg, T res){
        return new Message<>(false, msg, res);
    }

    public static <T>Message fail(String msg){
        return Message.fail(msg, null);
    }

    public boolean isSuccess(){
        return this.success;
    }

    public String msg(){
        return this.msg;
    }

    public T res(){
        return this.res;
    }

    public static class Page{
        public Object date;
        public int count;

        private Page(Object date, int count) {
            this.date = date;
            this.count = count;
        }

        public static Message setMsg(Object date, int count) {
            return Message.success(new Page(date, count));
        }
    }
}
