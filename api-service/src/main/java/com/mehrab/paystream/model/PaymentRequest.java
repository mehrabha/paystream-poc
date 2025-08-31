package com.mehrab.paystream.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    @NotBlank
    private String transactionId;

    @NotBlank
    private String userName;

    @NotBlank
    private double amount;

    private String transactionType;
    
    private String currency;
}
