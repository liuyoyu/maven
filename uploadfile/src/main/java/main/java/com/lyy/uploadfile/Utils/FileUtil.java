package main.java.com.lyy.uploadfile.Utils;

import java.text.DecimalFormat;

public class FileUtil {

    private static final long digit = 1024;
    private static final char[] unit = {'K', 'M', 'G', 'T'};

    public static String fileSize2String(long size){
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            return df.format((double) size) + "BT";
        } else if (size < 1048576) {
            return df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            return df.format((double) size / 1048576) + "MB";
        } else {
            return df.format((double) size / 1073741824) +"GB";
        }
    }
}
