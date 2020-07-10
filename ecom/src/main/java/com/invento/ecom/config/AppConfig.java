package com.invento.ecom.config;

import com.invento.ecom.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean  filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SecurityFilter customURLFilter = new SecurityFilter();

        registrationBean.setFilter(customURLFilter);
        registrationBean.addUrlPatterns("/product");
        registrationBean.addUrlPatterns("/searchProduct");
        registrationBean.addUrlPatterns("/deleteProduct");
        registrationBean.addUrlPatterns("/addProduct");
        registrationBean.addUrlPatterns("/editProduct");
        registrationBean.addUrlPatterns("/adminDashboard");
        registrationBean.addUrlPatterns("/customerDashboard");
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }
}
