package com.eventflow;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class TestAccount extends Account {
    public TestAccount(
            UUID id,
            BigDecimal balance,
            Currency currency,
            AccountStatus status) {

        super(id, balance, currency, status);
    }

    @Override
    public void credit(BigDecimal amount) {
        throw new RuntimeException();

    }
}
