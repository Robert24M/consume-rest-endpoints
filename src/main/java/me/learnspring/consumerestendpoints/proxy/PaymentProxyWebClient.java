package me.learnspring.consumerestendpoints.proxy;

import me.learnspring.consumerestendpoints.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentProxyWebClient {

    private final WebClient webClient;
    @Value("${name.service.url}")
    private String url;

    public PaymentProxyWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Payment> createPayment(
            String requestId,
            Payment payment) {
        return webClient.post()
                .uri(url + "/payments")
                .header("requestId", requestId)
                .body(Mono.just(payment), Payment.class)
                .retrieve()
                .bodyToMono(Payment.class);
    }
}
