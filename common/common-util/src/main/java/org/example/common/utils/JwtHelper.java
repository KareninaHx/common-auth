package org.example.common.utils;

/**
 * Created by 27 on 2023/10/3
 */

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 生成JSON Web令牌的工具类
 */
public class JwtHelper {
    // 设置过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    // 设置token密钥
    private static String tokenSignKey = "123456";

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                .setSubject("AUTH-USER") // 设置JWT 主题 主题通常用于标识令牌的用途或类型。
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间为设置为当前时间加上 tokenExpiration 的时间。
                .claim("userId", userId) //claim 用于声明 负载的一部分
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey) // 指定签名算法和密钥
                .compressWith(CompressionCodecs.GZIP) // 压缩算法
                .compact(); //生成最后的JWT令牌
        return token;
    }

    /*
    获取用户id
     */
    public static Long getUserId(String token) {
        try {
            //判断是否传入token
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody(); //获取 负载部分的信息
            Integer userId = (Integer) claims.get("userId"); //获取用户id
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    获取用户名
     */
    public static String getUsername(String token) {
        try {
            //判断传入token是否为空
            if (StringUtils.isEmpty(token)) return "";
            // 获取jwt令牌实例 并且指定加密密钥和解析jwt负载部分的信息
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();//获取其中的消息体
            return (String) claims.get("username");// 获取用户名
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeToken(String token) {
        //jwt token无需删除，客户端扔掉即可。
    }

    public static void main(String[] args) {
        //生成token
        String token = JwtHelper.createToken(1L, "admin");//"eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSCjAK0A0Ndg1S0lFKrShQsjI0MzY2sDQ3MTbQUSotTi3yTFGyMjKEsP0Sc1OBWp6unfB0f7NSLQDxzD8_QwAAAA.2eCJdsJXOYaWFmPTJc8gl1YHTRl9DAeEJprKZn4IgJP9Fzo5fLddOQn1Iv2C25qMpwHQkPIGukTQtskWsNrnhQ";//JwtHelper.createToken(7L, "admin");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}
