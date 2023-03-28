package me.learnspring.consumerestendpoints.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
        basePackages = "me.learnspring.consumerestendpoints.proxy")
public class ProjectConfiguration {
}
