package com.atguigu.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author
 */
public class JwtUtils {

    // 设置过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 生成哈希签名的秘钥
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * @description 获取 token 字符串
     * @date 2022/9/1 17:24
     * @param id 用户 id
     * @param nickname 用户名
     * @return java.lang.String
     */
    public static String getJwtToken(String id, String nickname) {

        String JwtToken = Jwts.builder()
                // 设置 jwt 头部信息【令牌类型，加密算法】
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置 主题内容，【主题，过期时间，用户信息】
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", nickname)

                // 设置哈西签名
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }


    /**
     * @description 判断 token 字符串是否并在并合理
     * @date 2022/9/1 17:26
     * @param jwtToken
     * @return boolean
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * @description 根据 request 对象，判断 token 是否存在和 合理
     * @date 2022/9/1 17:27
     * @param request
     * @return boolean
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * @description 获取用户 id
     * @date 2022/9/1 17:27
     * @param request
     * @return java.lang.String
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}