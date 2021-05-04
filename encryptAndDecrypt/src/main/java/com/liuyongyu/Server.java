package com.liuyongyu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Integer port = 8000;
    private static final RSA.KeyPairBase64 key = RSA.generator();
    private static String secretKey = "";
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();
    private static final String END = "\n$EOF";
    private static final String EOF = "$EOF";

    public void start()  {
        MyLog.info("Server启动");
        try {
            ServerSocket server = new ServerSocket(port);
            while(true){
                Socket accept = server.accept();
                pool.submit(new MyThread(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyThread extends Thread {
        private Socket accept = null;

        public MyThread(Socket socket) {
            super();
            this.accept = socket;
        }

        @Override
        public void run() {
            MyLog.info("成功建立连接");
            try {
                BufferedReader reader = null;
                PrintWriter writer = null;
                while (accept.isConnected() && !accept.isClosed()) {
                    //读入数据
                    reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    StringBuilder readCnt = new StringBuilder();
                    String t = null;
                    while (!EOF.equals(t = reader.readLine())) {
                        readCnt.append(t);
                    }
                    writer = new PrintWriter(accept.getOutputStream());
                    if ("hello".equals(readCnt.toString())) {
                        MyLog.info("提供公钥: %s", key.getPublicKey());
                        //发送公钥
                        writer.println(key.getPublicKey() + END);
                        writer.flush();
                        //获取密钥
                        reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                        readCnt = new StringBuilder();
                        while (!EOF.equals(t = reader.readLine())) {
                            readCnt.append(t);
                        }
                        secretKey = readCnt.toString();
                        MyLog.info("获取到密钥: %s", secretKey);
                        accept.close();
                    } else {
                        if ("".equals(secretKey)){
                            writer = new PrintWriter(accept.getOutputStream());
                            writer.println("send \'hello\' first, please!!" + END);
                            writer.flush();
                            accept.close();
                            break;
                        }
                        //解析数据
                        String data = AES.decrypt(readCnt.toString(), secretKey);
                        String res = this.service(data);
                        res = AES.encrypt(res, secretKey);
                        //写入数据
                        writer = new PrintWriter(accept.getOutputStream());
                        writer.println(res + END);
                        writer.flush();
                    }
                }
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                MyLog.info("连接断开");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private String service(String s){
            MyLog.info("收到客户端消息：%s", s);
            return "服务端";
        }
    }

}
