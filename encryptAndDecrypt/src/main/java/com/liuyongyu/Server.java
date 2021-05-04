package com.liuyongyu;

import javax.management.loading.MLet;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final Integer port = 8000;
    private static final RSA.KeyPairBase64 key = RSA.generator();

    public void start()  {
        MyLog.info("Server启动");
        try {
            while(true){
                ServerSocket server = new ServerSocket(port);
//                server.setSoTimeout(3000);
                Socket accept = server.accept();
                while (accept.isConnected() && !accept.isClosed()) {
                    MyLog.info("成功建立连接");
                    //读入数据
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    StringBuilder readCnt = new StringBuilder();
                    String t = null;
                    while ((t = reader.readLine()) != null) {
                        readCnt.append(t);
                    }
                    PrintWriter writer = null;

                    if ("hello".equals(readCnt.toString())) {
                        MyLog.info("提供公钥");
                        //写入数据
                        writer = new PrintWriter(accept.getOutputStream());
                        writer.println(key.getPublicKey());
                        writer.flush();
                    }else{
                        //解析数据
                        String data = RSA.privateKeyDecrypt(readCnt.toString(), key.getPrivateKey());
                        String res = this.service(data);
                        res = RSA.privateKeyEncrypt(res, key.getPrivateKey());
                        //写入数据
                        writer = new PrintWriter(accept.getOutputStream());
                        writer.println(res);
                        writer.flush();
                    }
                    accept.shutdownOutput();    //关闭输出流不然客户端可能会一直处于阻塞状态
                    reader.close();
                    writer.close();
                }
                MyLog.info("连接断开");
                //关闭操作
                accept.close();
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String service(String s){
        MyLog.info("收到客户端消息：%s", s);
        return "服务端";
    }
}
