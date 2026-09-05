package com.eventflow;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class Account {
    private final UUID id;
    private BigDecimal balance;
    private final Currency currency;
    private AccountStatus status;

    public Account(
            UUID id,
            BigDecimal balance,
            Currency currency,
            AccountStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid source Id");
        }

        if (balance == null) {
            throw new IllegalArgumentException("Invalid balance");
        }

        if (currency == null) {
            throw new IllegalArgumentException("Invalid currency");
        }

        if (status == null) {
            throw new IllegalArgumentException("Invalid status");
        }

        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void debit(BigDecimal amount) {
        if (this.status == AccountStatus.FROZEN) {
            throw new IllegalStateException("The account is frozen");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount should be greater than zero");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount should be greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    void restoreBalance(BigDecimal previousBalance) {
        this.balance = previousBalance;
    }

    public void freeze() {
        this.status = AccountStatus.FROZEN;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }
}
