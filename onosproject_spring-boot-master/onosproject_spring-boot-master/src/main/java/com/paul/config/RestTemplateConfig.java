package com.paul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author pxj
 * @date 2022-06-07 16:46
 */
@Configuration
public class RestTemplateConfig {

    @Value("${basicAuthentication.username}")
    private String username;

    @Value("${basicAuthentication.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().basicAuthentication(username,password).build();
    }
}
