package com.paul.jwt;

import com.alibaba.fastjson.JSONObject;
import com.paul.utils.jwt.JwtUtil;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

/**
 * @author pxj
 * @date 2022-11-28 15:10
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtUtil jwtUtil;

    private final long time = 1000 * 60 * 60 * 24;
    @Value("${jwt.secret-key}")
    private String signature;

    //jwt加密
    @Test
    public void contestLoads18(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //一、header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //二、payload
                .claim("username","tome")
                .claim("role","admin")
                //主题
                .setSubject("jwt-test")
                //有效时间
                .setExpiration(new Date(System.currentTimeMillis() + time))
                //id
                .setId(UUID.randomUUID().toString())
                //三、signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        System.out.println(jwtToken);
    }

    //jwt解密
    @Test
    public void contestLoads19(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJ5dSIsInByaW9yaXR5IjowLCJzdWIiOiJ1c2VyLWxvZ2luIiwiZXhwIjoxNjYzMjMyNzkyLCJqdGkiOiI0NzI3OWI1OC1kOGZiLTRkNGEtYmQxNS1mNjQwNzkxNmNmMGUifQ.6zPCOEqrYPNQnWH9PIl2-LpTCxuk5kdRCwFfYHeE0Ew";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        System.out.println(body.get("username"));
        System.out.println(body.get("role"));
        System.out.println(body.getId());
        System.out.println(body.getSubject());
        System.out.println(body.getExpiration());

    }

    @Test
    public void contestLoads20(){
        JSONObject jsonObject = jwtUtil.checkToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJ5dSIsInByaW9yaXR5IjowLCJzdWIiOiJ1c2VyLWxvZ2luIiwiZXhwIjoxNjYzMjMyNzkyLCJqdGkiOiI0NzI3OWI1OC1kOGZiLTRkNGEtYmQxNS1mNjQwNzkxNmNmMGUifQ.6zPCOEqrYPNQnWH9PIl2-LpTCxuk5kdRCwFfYHeE0Ew");
        System.out.println(jsonObject.get("username"));
    }

    @Test
    public void contestLoads21(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJ5dSIsInByaW9yaXR5IjowLCJzdWIiOiJ1c2VyLWxvZ2luIiwiZXhwIjoxNjYzMjMyNzkyLCJqdGkiOiI0NzI3OWI1OC1kOGZiLTRkNGEtYmQxNS1mNjQwNzkxNmNmMGUifQ.6zPCOEqrYPNQnWH9PIl2-LpTCxuk5kdRCwFfYHeE0Ew";
        JSONObject jsonObject = new JSONObject();
        //解析token
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(signature)
                    //设置需要解析的jwt
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            jsonObject.put("username",body.get("username"));
            System.out.println(jsonObject.get("username"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
