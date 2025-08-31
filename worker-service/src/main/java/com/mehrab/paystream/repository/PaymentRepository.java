package com.mehrab.paystream.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehrab.paystream.model.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String>{
    
}
