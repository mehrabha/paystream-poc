package com.mehrab.paystream.consumer;

import java.time.Instant;

import org.apache.commons.lang3.SystemUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mehrab.paystream.avro.Payment;
import com.mehrab.paystream.model.PaymentEntity;
import com.mehrab.paystream.repository.PaymentRepository;


/* Consumer Service for persisting records into database */

@Component
public class PaymentProcessor {

    @Autowired
    private PaymentRepository paymentRepository;

    @KafkaListener(topics = "payments")
    public void onEvent(ConsumerRecord<String, Payment> message) {
        Payment paymentData = message.value();

        PaymentEntity entry = new PaymentEntity();

        entry.setId(paymentData.getTransactionId());
        entry.setUserId(paymentData.getUserId());
        entry.setAmount(paymentData.getAmount());
        entry.setCurrency(paymentData.getCurrency());
        entry.setTimestamp(Instant.now());
        entry.setStatus("POSTED");

        paymentRepository.save(entry);
    }
}
