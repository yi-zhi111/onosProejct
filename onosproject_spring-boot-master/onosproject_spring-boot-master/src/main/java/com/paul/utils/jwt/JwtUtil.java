package com.paul.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author pxj
 * @date 2022-09-06 15:37
 */
@Component
public class JwtUtil {

    private long time =  1000 * 60 * 60 * 24 * 7;

    @Value("${jwt.secret-key}")
    private String signature;
    //生成token

    /**
     *
     * @param username
     * @param priority
     * @return
     */
    public String createToken(String username,int priority){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //一、header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //二、payload
                .claim("username",username)
                .claim("priority",priority)
                //主题
                .setSubject("user-login")
                //有效时间
                .setExpiration(new Date(System.currentTimeMillis() + time))
                //id
                .setId(UUID.randomUUID().toString())
                //三、signature
                .signWith(SignatureAlgorithm.HS256,signature)//签发算法，秘钥为signature
                .compact();
        return jwtToken;
    }

    //解析token
    public JSONObject checkToken(String token) {
        JSONObject jsonObject = new JSONObject();
        if (token == null) {
            jsonObject.put("flag", false);
            return jsonObject;
        }
            //解析token
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        //设置签名的秘钥
                        .setSigningKey(signature)
                        //设置需要解析的jwt
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                jsonObject.put("username", body.get("username"));
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        jsonObject.put("flag", false);
        return jsonObject;
        }

}
