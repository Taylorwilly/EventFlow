package com.eventflow;

import java.util.Optional;

public class TransferResult implements EventResult {
    private final TransferOutcome outcome;
    private final TransferRejectionReason rejectionReason;

    public TransferResult(TransferOutcome outcome, TransferRejectionReason rejectionReason) {
        if (outcome == null) {
            throw new IllegalArgumentException("Transfer outcome is required");
        }

        if (outcome == TransferOutcome.REJECTED && rejectionReason == null) {
            throw new IllegalArgumentException("Rejection reason is required");
        }

        if (outcome == TransferOutcome.APPROVED && rejectionReason != null) {
            throw new IllegalArgumentException("Approved transfer cannot have a rejection reason");
        }

        this.outcome = outcome;
        this.rejectionReason = rejectionReason;
    }

    public TransferOutcome getOutcome() {
        return outcome;
    }

    public Optional<TransferRejectionReason> getRejectionReason() {
        return Optional.ofNullable(rejectionReason);
    }
}
