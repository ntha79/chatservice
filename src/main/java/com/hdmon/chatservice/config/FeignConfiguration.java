package com.hdmon.chatservice.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.hdmon.chatservice")
public class FeignConfiguration {

}
