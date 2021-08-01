package main.java.com.lyy.uploadfile.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    /**
     * 过期时间一天
     */
    private static final long EXPIRE_TIME = 3600 * 24;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e631";

    /**
     * JWT存储值
     */
    private static final String JWT_STRING = "jwt_string";

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getTokenStr(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(JWT_STRING).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     * @return 加密的token
     */
    public static String sign(String string) {
        try {
//            过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//            私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            // 附带用户信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    //要往token中存储的东西
                    .withClaim(JWT_STRING, string)
//                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
