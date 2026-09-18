package com.eventflow.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {
    @NotNull
    private UUID sourceAccountId;

    @NotNull
    private UUID destinationAccountId;

    @NotNull
    private Currency currency;

    @NotNull
    @Positive
    private BigDecimal amount;

    public TransferRequest(
            UUID sourceAccountId,
            UUID destinationAccountId,
            Currency currency,
            BigDecimal amount) {

        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
