package me.learnspring.consumerestendpoints.controllers;

import me.learnspring.consumerestendpoints.model.Payment;
import me.learnspring.consumerestendpoints.proxy.PaymentProxy;
import me.learnspring.consumerestendpoints.proxy.PaymentProxyRestTemplate;
import me.learnspring.consumerestendpoints.proxy.PaymentProxyWebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class PaymentsController {

    Logger logger = Logger.getLogger(PaymentsController.class.getName());
    private final PaymentProxy paymentProxy;
    private final PaymentProxyRestTemplate paymentProxyRestTemplate;
    private final PaymentProxyWebClient paymentProxyWebClient;

    public PaymentsController(PaymentProxy paymentProxy, PaymentProxyRestTemplate paymentProxyRestTemplate, PaymentProxyWebClient paymentProxyWebClient) {
        this.paymentProxy = paymentProxy;
        this.paymentProxyRestTemplate = paymentProxyRestTemplate;
        this.paymentProxyWebClient = paymentProxyWebClient;
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(
            @RequestBody Payment payment
    ) {
        logger.info("Received payment from client with value of " + payment.getAmount());

        String requestId = UUID.randomUUID().toString();

        logger.info("ID set by me " + requestId);
        Payment paymentReceivedFromRest = paymentProxy.createPayment(requestId, payment);

        logger.info("Data received from REST service: ID= " + paymentReceivedFromRest.getId() +
                "\nPayment Amount = " + paymentReceivedFromRest.getAmount());

        return ResponseEntity
                .accepted()
                .header("requestId", requestId)
                .body(paymentReceivedFromRest);
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> getPayment(
            @RequestBody Payment payment
    ) {
        payment.setId(UUID.randomUUID().toString());
        Payment paymentReceived = paymentProxyRestTemplate.createPayment(payment);
        return ResponseEntity
                .accepted()
                .header("paymentID", UUID.randomUUID().toString())
                .body(paymentReceived);
    }

    @PostMapping("/pay")
    public Mono<Payment> makePayment(
            @RequestBody Payment payment
    ) {
        String requestId = UUID.randomUUID().toString();
        return paymentProxyWebClient.createPayment(requestId,payment);
    }
}
