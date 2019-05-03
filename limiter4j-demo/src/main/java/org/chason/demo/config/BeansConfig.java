package org.chason.demo.config;

import org.chason.limter.filter.LimiterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring boot配置
 * Author: chason
 * Date: 2019/5/3 18:20
 **/
@Configuration
public class BeansConfig {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new LimiterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("limiterFilter");
        return registration;
    }

}
