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
    private static String key = "";

    public void start(){
        try {

            init();

            Socket client = new Socket(address, port);

            PrintWriter writer = new PrintWriter(client.getOutputStream());
            String cnt = sendCnt(); //todo
            cnt = RSA.publicKeyEncrypt(cnt, key);
            writer.println(cnt);
            writer.flush();
            client.shutdownOutput();  //关闭输出流

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder readCnt = new StringBuilder();
            String t = null;
            while ((t = reader.readLine()) != null) {
                readCnt.append(t);
            }

            String res = RSA.publicKeyDecrypt(readCnt.toString(), key);
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
            key = getKey();
        }
    }

    /**
     * 当密钥为空时，那么会先向服务器请求密钥（发送一个hello）
     * @return
     * @throws IOException
     */
    private String getKey() throws IOException {
        Socket client = new Socket(address, port);

        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.println("hello");
        writer.flush();
        client.shutdownOutput();

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String t = null;
        while ((t = reader.readLine()) != null) {
            sb.append(t);
        }
        client.close();
        writer.close();
        reader.close();
        return sb.toString();
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
