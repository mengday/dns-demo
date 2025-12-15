package com.example.mystarter.config;

import com.example.mystarter.filter.GrayWebFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configurable
public class EnvAutoConfiguration {

    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean<GrayWebFilter> grayWebFilterRegistration() {
        FilterRegistrationBean<GrayWebFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GrayWebFilter());
        registration.addUrlPatterns("/*");
        registration.setName("grayWebFilter");
        registration.setOrder(-10000);

        return registration;
    }
}
