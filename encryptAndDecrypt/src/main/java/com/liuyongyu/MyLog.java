package com.liuyongyu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLog {
    public static void info(String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.printf(">>> info: %s -- %s%n", sdf.format(new Date()), msg);
    }

    public static void info(String msg, Object...objs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String infoCnt = ">>> info: %s -- " + msg + "%n";
        Object[] params = new Object[objs.length + 1];
        params[0] = sdf.format(new Date());
        for (int i = 1; i < objs.length + 1; i++) {
            params[i] = objs[i - 1];
        }
        System.out.printf(infoCnt, params);
    }
}
