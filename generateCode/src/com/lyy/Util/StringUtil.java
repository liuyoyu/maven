package com.lyy.Util;

/**
 * author liuyongyu
 * date 2021/8/1
 */
public class StringUtil {
    /**
     * 首字母大些
     * @param word
     * @return
     */
    public static String upperCaseHeader(String word) {
        if (word == null || "".equals(word)) {
            return "";
        }
        String header = word.substring(0, 1).toUpperCase();
        String body = word.substring(1, word.length());
        return header + body;
    }

    /**
     * 下划线命名转驼峰命名
     * @param str
     * @return
     */
    public static String underScoreCaseToCamelCase(String str) {
        String[] words = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<words.length; i++) {
            if (i == 0) {
                sb.append(words[i].toLowerCase());
            } else {
                sb.append(upperCaseHeader(words[i].toLowerCase()));
            }
        }
        return sb.toString();
    }

    public static String geneJavaFilePath(String fileName, String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append(filePath).append("/").append(fileName).append(".java");
        return sb.toString();
    }
}
