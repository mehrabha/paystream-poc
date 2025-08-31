package com.mehrab.paystream.controller;

import java.util.List;

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
import com.mehrab.paystream.model.PaymentEntity;
import com.mehrab.paystream.model.PaymentRequest;
import com.mehrab.paystream.model.PaymentResponse;
import com.mehrab.paystream.repository.PaymentRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentController {
    
    @Autowired
    private KafkaTemplate<String, Payment> kafka;

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${app.kafka.topic}")
    private String kafkaTopic;

    @PostMapping("/submit")
    public ResponseEntity<PaymentResponse> submit(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Map request to Kafka
        log.info("Recieved transaction={}, type={}", paymentRequest.getTransactionId(), paymentRequest.getTransactionType());

        Payment paymentAvro = Payment.newBuilder()
            .setTransactionId(paymentRequest.getTransactionId())
            .setUserId(paymentRequest.getUserName())
            .setAmount(paymentRequest.getAmount())
            .setCurrency(paymentRequest.getCurrency())
            .build();

        try {
            // Try to publish
            kafka.send(kafkaTopic, "pay-" + paymentRequest.getTransactionId(), paymentAvro);

            PaymentResponse response = new PaymentResponse("Payment Successfully Published!", "OK");
            return ResponseEntity.status(200).body(response);
        } catch(Exception e) {
            PaymentResponse response = new PaymentResponse(e.getMessage(), "EXCEPTION");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentEntity>> getTransactions() {

        try {
            List<PaymentEntity> transactions = paymentRepository.findAll();
            return ResponseEntity.status(200).body(transactions);
        } catch (Exception e) {
            log.info("Exception occured while trying to fetch transactions from database. Error={}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/_ping")
    public String ping() {
        return "SUCCESS!";
    }
}
