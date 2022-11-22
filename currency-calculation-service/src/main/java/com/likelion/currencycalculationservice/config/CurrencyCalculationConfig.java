package com.likelion.currencycalculationservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CurrencyCalculationConfig {
    @Bean
    @LoadBalanced // annotation này sẽ cân bằng tải cho mỗi requests
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
