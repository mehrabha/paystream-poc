package com.mehrab.paystream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mehrab.paystream.avro.Payment;
import com.mehrab.paystream.model.PaymentRequest;
import com.mehrab.paystream.model.PaymentResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    @Autowired
    private KafkaTemplate<String, Payment> kafka;

    @Value("{app.kafka.topic}")
    private String kafkaTopic;

    @PostMapping("/submit")
    public ResponseEntity<PaymentResponse> submit(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Map request to Kafka

        Payment paymentAvro = Payment.newBuilder()
            .setUserId(paymentRequest.getUserName())
            .setAmount(paymentRequest.getAmount())
            .setCurrency(paymentRequest.getCurrency())
            .build();

        try {
            // Try to publish
            kafka.send(kafkaTopic, "pay-" + System.currentTimeMillis(), paymentAvro);

            PaymentResponse response = new PaymentResponse("Payment Successfully Submitted!", "OK");
            return ResponseEntity.status(200).body(response);
        } catch(Exception e) {
            PaymentResponse response = new PaymentResponse(e.getMessage(), "EXCEPTION");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/_ping")
    public String ping() {
        return "SUCCESS!";
    }
}
