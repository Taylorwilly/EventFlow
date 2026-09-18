package com.eventflow.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventflow.SubmissionResponse;
import com.eventflow.dto.TransferRequest;
import com.eventflow.service.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public SubmissionResponse transferRequest(@Valid @RequestBody TransferRequest request) {
        return transferService.submitTransfer(request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getCurrency(),
                request.getAmount());
    }
}
