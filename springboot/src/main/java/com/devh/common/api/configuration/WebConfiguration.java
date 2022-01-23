package com.devh.common.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * <pre>
     * Description :
     *     API 서버로서 자원을 공유하도록 설정
     * ===============================================
     * Member fields :
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/06/03
     * </pre>
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                // .allowedOrigins("http://localhost:3000")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
