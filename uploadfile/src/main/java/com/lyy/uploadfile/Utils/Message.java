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

    public static <T>Message fail(String msg, T res){
        return new Message<>(false, msg, res);
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
}
