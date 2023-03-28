package me.learnspring.consumerestendpoints.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableFeignClients(
        basePackages = "me.learnspring.consumerestendpoints.proxy")
public class ProjectConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
