package org.example.config;

import org.example.interception.LoginInterception;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: ZhaoXing

 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterception()).addPathPatterns("/**")
                .excludePathPatterns( //无需登录验证
                        "/user/login",
                        "/user/register",
                        "/doc.html/**",
//                        "/doc.html#/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html/**",
                        "/webjars/**", "/swagger/**", "/v3/**");
    }
}
