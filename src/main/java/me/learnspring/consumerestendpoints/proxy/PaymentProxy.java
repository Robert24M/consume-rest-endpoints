package me.learnspring.consumerestendpoints.proxy;

import me.learnspring.consumerestendpoints.model.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payments",
            url = "${name.service.url}")
public interface PaymentProxy {

    @PostMapping("/payments")
    Payment createPayment(
            @RequestHeader String requestId,
            @RequestBody Payment payment
    );
}
