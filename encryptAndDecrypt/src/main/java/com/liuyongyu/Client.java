package com.liuyongyu;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String address = "localhost";
    private static final Integer port = 8000;
    private static String key = ""; //保存公钥
    private static String secretKey = "";   //保存密钥
    private static final String END = "\n$EOF";
    private static final String EOF = "$EOF";

    public void start(){
        try {

            init();

            Socket client = new Socket(address, port);

            PrintWriter writer = new PrintWriter(client.getOutputStream());
            String cnt = sendCnt();
            cnt = AES.encrypt(cnt, secretKey);
            writer.println(cnt + END);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder readCnt = new StringBuilder();
            String t = null;
            while (!EOF.equals(t = reader.readLine())) {
                readCnt.append(t);
            }
            MyLog.info(readCnt.toString());
            String res = AES.decrypt(readCnt.toString(), secretKey);
            responseCnt(res);

            client.close();
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化key
     * @throws IOException
     */
    private void init() throws IOException {
        while ("".equals(key)) {
            getKey();
        }
    }

    /**
     * 当密钥为空时，那么会先向服务器请求密钥（发送一个hello）
     * 并发送公钥
     * @return
     * @throws IOException
     */
    private void getKey() throws IOException {
        Socket client = new Socket(address, port);

        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.println("hello" + END);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String t = null;
        while (!EOF.equals(t = reader.readLine())) {
            sb.append(t);
        }
        key = sb.toString();    //获取到公钥
        MyLog.info("获取到公钥：%s", key);

        //发送密钥
        secretKey = AES.generator();
        MyLog.info("创建并发送密钥：%s", secretKey);
        writer = new PrintWriter(client.getOutputStream());
        writer.print(secretKey + END);
        writer.flush();

        client.close();
        writer.close();
        reader.close();
    }

    /**
     * 可以根据需求重写这个方法
     * @return
     */
    private String sendCnt(){
        return "客户端";
    }

    /**
     * 可以根据需求重写这个方法
     * 处理得到的消息
     * @param cnt
     * @return
     */
    private String responseCnt(String cnt){
        System.out.printf("收到服务端消息: %s%n", cnt);
        return "";
    }
}
