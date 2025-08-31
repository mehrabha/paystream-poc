package com.mehrab.paystream.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity {
    
    @Id
    @Column(length = 128)
    private String id;

    @Column(nullable = false, length = 64)
    private String userId;  // USERNAME

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, length = 3)
    @Size(min = 3, max = 3)
    private String currency;

    @Column
    private Instant timestamp;

    @Column(nullable = false, length = 16)
    private String status;  // eg. PROCESSED
}
