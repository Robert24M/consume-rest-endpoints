package me.learnspring.consumerestendpoints.proxy;

import me.learnspring.consumerestendpoints.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

@Component
public class PaymentProxyRestTemplate {

    private final RestTemplate restTemplate;
    @Value("${name.service.url}")
    private String url;

    public PaymentProxyRestTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Payment createPayment(Payment payment) {
        String uri = url + "/payments";

        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", UUID.randomUUID().toString());
        HttpEntity<Payment> entity = new HttpEntity<>(payment,headers);

        ResponseEntity<Payment> response = restTemplate
                .exchange(uri, HttpMethod.POST,entity,Payment.class);

        return response.getBody();
    }
}
