package com.dgut.cloud_disk.interceptor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CustomWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }
    @Bean
    public ManagerInterceptor managerInterceptor(){
        return new ManagerInterceptor();
    }
    @Bean
    public SupManagerInterceptor supManagerInterceptor(){
        return new SupManagerInterceptor();
    }
    @Bean
    public MyFilter myFilter(){return new MyFilter();}
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(userInterceptor()).addPathPatterns("/cloud/user/**").excludePathPatterns("/cloud/user/manager/**");
        registry.addInterceptor(managerInterceptor()).addPathPatterns("/cloud/user/manager/**").excludePathPatterns("/cloud/user/manager/sup/**");
        registry.addInterceptor(supManagerInterceptor()).addPathPatterns("/cloud/user/manager/sup/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
    @Bean
    public FilterRegistrationBean setMyFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(myFilter());
        filterRegistrationBean.addUrlPatterns("/cloud/user/*");
        filterRegistrationBean.setOrder(1);   //order的数值越小，在所有的filter中优先级越高
        return filterRegistrationBean;
    }
}
