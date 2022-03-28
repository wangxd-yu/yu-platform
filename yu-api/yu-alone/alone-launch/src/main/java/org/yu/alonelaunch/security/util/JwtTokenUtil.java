package org.yu.alonelaunch.security.util;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import org.yu.alonelaunch.security.pojo.AuthenticationInfo;
import org.yu.alonelaunch.security.pojo.SecurityUser;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author wangxd
 * @date 2021-12-10 15:50
 */
public class JwtTokenUtil {

    private static String secretKey = "123456";
    private static String tokenHeader = "Bearer ";

    public static String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(tokenHeader)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static boolean validateToken(String token) {
        if (!JWTUtil.verify(token, secretKey.getBytes(StandardCharsets.UTF_8))) {
            throw new ValidateException("token 不合法");
        }
        JWTValidator.of(token).validateDate();
        return true;
    }

    public static String generateToken(Map<String, Object> info) {

        return JWTUtil.createToken(info, secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public static AuthenticationInfo generateAuthenticationInfo(SecurityUser securityUser) {
        long expiresIn = 60*60L;
        long expiration = System.currentTimeMillis() / 1000 + expiresIn;

        Map<String, Object> info = new HashMap<>(8);
        info.put(JWTPayload.EXPIRES_AT, expiration);
        //把用户ID设置到JWT中
        info.put("id", securityUser.getId());
        info.put("username", securityUser.getUsername());
        info.put("deptId", securityUser.getDeptId());
        info.put("tenantId", securityUser.getTenantId());
        info.put("roles", securityUser.getRoles());
        String token = generateToken(info);
        AuthenticationInfo authenticationInfo = AuthenticationInfo.builder()
                .token(token)
                .expiration(expiration)
                .expiresIn(expiresIn - 1)
                .tokenHead(tokenHeader)
                .build();
        return authenticationInfo;
    }

    public static SecurityUser getAuthentication(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(secretKey.getBytes(StandardCharsets.UTF_8));
        SecurityUser securityUser = SecurityUser.builder()
                .id(jwt.getPayload("id").toString())
                .username(jwt.getPayload("username").toString())
                .deptId(jwt.getPayload("deptId").toString())
                .tenantId(jwt.getPayload("tenantId") == null ? null : jwt.getPayload("tenantId").toString())
                .roles(new HashSet<>(JSONUtil.toList(jwt.getPayload("roles").toString(), String.class)))
                .build();
        return securityUser;
    }
}
