package com.eventflow;

public enum TransferRejectionReason {
    INSUFFICIENT_FUNDS,
    ACCOUNT_FROZEN,
    TRANSFER_LIMIT_EXCEEDED,
    ACCOUNT_NOT_FOUND,
    CURRENCY_MISMATCH
}
