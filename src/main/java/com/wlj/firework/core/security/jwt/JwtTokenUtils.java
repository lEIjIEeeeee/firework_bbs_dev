package com.wlj.firework.core.security.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wlj.firework.core.modular.common.constant.PropertiesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtTokenUtils {

    public static String generateToken(JwtPayLoad jwtPayLoad) {
        Long expireSeconds = getExpireSeconds();
        final Date expiredDate = DateUtil.date(System.currentTimeMillis() + expireSeconds * 1000);
        return generateToken(jwtPayLoad.getUserId(), expiredDate, jwtPayLoad.toMap());
    }

    public static JwtPayLoad getJwtPayLoad(String token) {
        Claims claim = getClaimByToken(token);
        return JwtPayLoad.toBean(claim);
    }

    public static Boolean isTokenExpired(String token) {
        try {
            final Date expirationTime = getTokenExpirationTime(token);
            return expirationTime.before(DateUtil.date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public static Date getTokenExpirationTime(String token) {
        return getClaimByToken(token).getExpiration();
    }

    public static String generateToken(String userId, Date expiredDate, Map<String, Object> claims) {
        final Date createDate = DateUtil.date();
        String secret = getJwtSecret();
        if (claims == null) {
            return Jwts.builder()
                       .setSubject(userId)
                       .setIssuedAt(createDate)
                       .setExpiration(expiredDate)
                       .signWith(SignatureAlgorithm.HS512, secret)
                       .compact();
        } else {
            return Jwts.builder()
                       .setClaims(claims)
                       .setSubject(userId)
                       .setIssuedAt(createDate)
                       .setExpiration(expiredDate)
                       .signWith(SignatureAlgorithm.HS512, secret)
                       .compact();
        }
    }

    public static Claims getClaimByToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new IllegalArgumentException("token参数为空");
        }
        String jwtSecret = getJwtSecret();
        return Jwts.parser()
                   .setSigningKey(jwtSecret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    private static String getJwtSecret() {
        return SpringUtil.getProperty(PropertiesConstants.JWT_SECRET);
    }

    public static Long getExpireSeconds() {
        return (long) (86400 * 365);
    }

}
