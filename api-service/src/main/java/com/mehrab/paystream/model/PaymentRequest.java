package com.mehrab.paystream.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    private String userName;
    private double amount;
    private String transactionType;
    private String currency;
}
