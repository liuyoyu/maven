package com.lyy.uploadfile.Utils;

/**
 * 数据表格返回类
 */
public class PageData extends Result{
    private int count;

    public PageData(Type type, String msg, Object data, int count) {
        super(type, msg, data);
        this.count = count;
    }

    public static PageData success(Object data, int count){
        return PageData.success("搜索成功", data, count);
    }

    public static PageData success(String msg, Object data, int count){
        return new PageData(Type.SUCCESS, msg, data, count);
    }

    public static PageData message(Message msg) {
        Message.Page  page = (Message.Page)msg.res();
        return new PageData(Type.SUCCESS, msg.msg(), page.date, page.count);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
