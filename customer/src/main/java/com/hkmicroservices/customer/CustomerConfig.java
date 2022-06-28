package com.hkmicroservices.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    @Bean
    @LoadBalanced //Required if you are using RestTemplate to call instances registered with Eureka
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
