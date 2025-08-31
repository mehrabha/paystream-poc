package com.mehrab.paystream.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mehrab.paystream.repository.PaymentRepository;


/* Consumer Service for persisting records into database */

@Component
public class PaymentProcessor {

    @Autowired
    private PaymentRepository paymentRepository;
}
