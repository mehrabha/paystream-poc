package com.mehrab.paystream.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    @NotBlank
    private String transactionId;

    @NotBlank
    private String userName;

    @NotNull
    private Double amount;

    private String transactionType;
    
    private String currency;
}
