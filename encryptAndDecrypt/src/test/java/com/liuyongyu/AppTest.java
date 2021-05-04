package com.liuyongyu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void RSQTest(){
        String text = "所有的结局都已写好\n" +
                "所有的泪水也都已启程\n" +
                "却忽然忘了是怎麽样的一个开始\n" +
                "在那个古老的不再回来的夏日\n" +
                "\n" +
                "无论我如何地去追索\n" +
                "年轻的你只如云影掠过\n" +
                "而你微笑的面容极浅极淡\n" +
                "逐渐隐没在日落後的群岚\n" +
                "\n" +
                "遂翻开那发黄的扉页\n" +
                "命运将它装订得极为拙劣\n" +
                "含著泪 我一读再读\n" +
                "却不得不承认\n" +
                "青春是一本太仓促的书";
//        System.out.println(text);
        System.out.println();
        RSA.KeyPairBase64 keyPairBase64 = RSA.generator();
        String pbcKey = keyPairBase64.getPublicKey();
        String prvKey = keyPairBase64.getPrivateKey();
        System.out.println("公钥："+ pbcKey);
        System.out.println("私钥：" + prvKey);
        System.out.println();

        System.out.println("公钥加密：");
        String pke = RSA.publicKeyEncrypt(text, pbcKey);
        System.out.println(pke);
        System.out.println("私钥解密：");
        String pkd = RSA.privateKeyDecrypt(pke, prvKey);
        System.out.println(pkd);
        System.out.println();

        System.out.println("私钥加密：");
        pke = RSA.privateKeyEncrypt(text, prvKey);
        System.out.println(pke);
        System.out.println("公钥解密：");
        pkd = RSA.publicKeyDecrypt(pke, pbcKey);
        System.out.println(pkd);
    }

    @Test
    public void stringToDoubleTest(){
        String coins = "12.0";
        double v = Double.valueOf(coins);
        System.out.println((int)v); // 12
    }

    @Test
    public void socketServerTest(){
        try {
            System.out.println("开启服务");
            ServerSocket server = new ServerSocket(8000);
            Socket accept = server.accept();

            //读入数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            System.out.printf("content: %s%n", reader.readLine());

            //写入数据
            PrintWriter writer = new PrintWriter(accept.getOutputStream());
            writer.println("server had accepted message");
            writer.flush();


            //关闭操作
            server.close();
            accept.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void socketClientTest(){
        try {
            System.out.println("开始访问");
            Socket client = new Socket("localhost", 8000);

            PrintWriter writer = new PrintWriter(client.getOutputStream());
            writer.println("client send a message");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.printf("client accept a message: %s%n", reader.readLine());


            client.close();
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EncryptServerTest(){
        Server encryptServer = new Server();
        encryptServer.start();
    }

    @Test
    public void DecryptClientTest(){
        Client decryptClient = new Client();
        decryptClient.start();
    }
}
