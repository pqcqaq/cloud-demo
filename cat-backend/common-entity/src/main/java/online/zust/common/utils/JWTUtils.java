package online.zust.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.zust.common.constant.JwtConfig;
import online.zust.common.exception.ServiceException;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author qcqcqc
 */
public class JWTUtils {

    private static final Logger logger = Logger.getLogger(JWTUtils.class.getName());

    public static <T> String createJWT(T playload) {
        //设置密钥及算法 a
        Algorithm algorithm = Algorithm.HMAC256(JwtConfig.secret);
        try {
            JWTCreator.Builder builder = JWT.create();
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(playload);
            builder.withExpiresAt(getAfterSecondsDate(JwtConfig.expire));
            builder.withClaim("info", s);
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ServiceException("JWT令牌生成失败");
        }
    }

    public static Boolean checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JWT.require(Algorithm.HMAC256(JwtConfig.secret)).build().verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static <T> T getPlayLoad(String token, Class<T> clazz) {
        try {
            String info = JWT.require(Algorithm.HMAC256(JwtConfig.secret)).build().verify(token).getClaim("info").asString();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(info, clazz);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ServiceException("JWT令牌解析失败");
        }
    }

    /**
     * 获取过期时间
     *
     * @param expire 过期时间 单位秒
     * @return Date
     */
    private static Date getAfterSecondsDate(long expire) {
        return new Date(System.currentTimeMillis() + expire * 1000);
    }

    public static String refresh(String token) {
        try {
            String info = JWT.require(Algorithm.HMAC256(JwtConfig.secret)).build().verify(token).getClaim("info").asString();
            return createJWT(info);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ServiceException("JWT令牌刷新失败");
        }
    }
}
