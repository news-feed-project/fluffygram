package com.fluffygram.newsfeed.global.config;

import com.fluffygram.newsfeed.global.filter.ExceptionHandlerFilter;
import com.fluffygram.newsfeed.global.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean exceptionFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ExceptionHandlerFilter()); // Filter 등록
        filterRegistrationBean.setOrder(1); // Filter 순서 1 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL 에 Filter 적용

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter()); // Filter 등록
        filterRegistrationBean.setOrder(2); // Filter 순서 2 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL 에 Filter 적용

        return filterRegistrationBean;
    }
}
