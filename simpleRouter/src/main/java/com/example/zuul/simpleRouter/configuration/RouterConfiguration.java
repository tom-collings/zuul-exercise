package com.example.zuul.simpleRouter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfiguration {

    @Bean
    public SimpleFilter getFilter() {
        return new SimpleFilter();
    }

}
