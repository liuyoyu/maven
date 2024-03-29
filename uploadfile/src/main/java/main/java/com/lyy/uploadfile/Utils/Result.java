package main.java.com.lyy.uploadfile.Utils;

import java.io.Serializable;

public class Result<T> implements Serializable {
    protected int code;
    protected String msg;
    protected T data;

    public enum Type{
        /*成功*/
        SUCCESS(0),
        /*警告*/
        WARN(301),
        /*错误*/
        ERROR(500);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value(){
            return value;
        }
    }

    public static Result message(Message message) {
        return message.isSuccess() ? Result.success(message.msg()) : Result.success(message.msg());
    }

    public static Result success(){
        return Result.success("操作成功");
    }

    public static Result success(String msg) {
        return Result.success(msg, null);
    }

    public static <T> Result<T> success(String msg, T data){
        return new Result<>(Type.SUCCESS, msg, data);
    }

    public static Result error(){
        return Result.error("操作失败");
    }

    public static Result error(String msg){
        return Result.error(msg, null);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(Type.ERROR, msg, data);
    }

    public static Result warn(String msg){
        return Result.warn(msg, null);
    }

    public static <T>Result<T> warn(String msg, T data) {
        return new Result<>(Type.WARN, msg, data);
    }

    public Result(){
    }

    public Result(Type type, String msg, T data) {
        this.code = type.value();
        this.msg = msg;
        this.data = data;
    }

    public int getcode() {
        return code;
    }

    public void setcode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
