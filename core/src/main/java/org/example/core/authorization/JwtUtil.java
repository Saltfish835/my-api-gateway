package org.example.core.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String signingKey = "B*B^5Fe";


    /**
     * 生成jwt token
     * @param issuer
     * @param ttlMillis
     * @param claims
     * @return
     */
    public static String encode(String issuer, long ttlMillis, Map<String, Object> claims) {
        if(null == claims) {
            claims = new HashMap<>();
        }
        // 签发时间
        final long currentTimeMillis = System.currentTimeMillis();
        final Date now = new Date(currentTimeMillis);
        final JwtBuilder jwtBuilder = Jwts.builder()
                // 载荷部分
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(now)
                // 给谁的令牌
                .setSubject(issuer)
                // 生成签名的算法及密钥
                .signWith(SignatureAlgorithm.HS256, signingKey);
        // 设置过期时间
        if(ttlMillis >= 0) {
            long expMillis = currentTimeMillis + ttlMillis;
            final Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }
        return jwtBuilder.compact();
    }


    /**
     * 对Jwt token进行解码
     * @param token
     * @return
     */
    public static Claims decode(String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }
}
