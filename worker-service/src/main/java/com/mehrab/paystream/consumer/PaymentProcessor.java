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

import lombok.extern.slf4j.Slf4j;


/* Consumer Service for persisting records into database */

@Component
@Slf4j
public class PaymentProcessor {

    @Autowired
    private PaymentRepository paymentRepository;

    @KafkaListener(topics = "payments")
    public void onEvent(ConsumerRecord<String, Payment> message) {
        log.info("Recieved Kafka event with Id={}", message.key());
        Payment paymentData = message.value();

        log.info("Processing transaction, messageId={}, transactionId={}", message.key(), paymentData.getTransactionId());

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
