package com.example.ToYokoNa.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ManageAccountFilter> manageAccountFilter() {
        FilterRegistrationBean<ManageAccountFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new ManageAccountFilter());
        //  管理者権限が必要なURL
        bean.addUrlPatterns("/userManage");
        bean.addUrlPatterns("/userEdit/*");
        bean.addUrlPatterns("/userCreate");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<loginFilter> loginFilter() {
        FilterRegistrationBean<loginFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new loginFilter());
        bean.addUrlPatterns("/userEdit/*");
        bean.addUrlPatterns("/");
        bean.addUrlPatterns("/newMessage");
        bean.addUrlPatterns("/userCreate");
        bean.addUrlPatterns("/userManage");
        bean.setOrder(1);
        return bean;
    }
}