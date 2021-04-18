package com.liuyongyu;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
}
