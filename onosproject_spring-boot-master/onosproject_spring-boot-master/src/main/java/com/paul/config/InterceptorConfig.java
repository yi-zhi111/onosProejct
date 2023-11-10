package com.paul.config;

import com.paul.interceptor.SampleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pxj
 * @date 2022-09-06 16:12
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private SampleInterceptor sampleInterceptor;

    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(sampleInterceptor).addPathPatterns("/testLogin");
        WebMvcConfigurer.super.addInterceptors(interceptorRegistry);
    }
}
