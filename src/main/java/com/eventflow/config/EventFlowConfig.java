package com.eventflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eventflow.AccountStore;
import com.eventflow.EventQueue;
import com.eventflow.ProcessorRegistration;
import com.eventflow.ResultStore;
import com.eventflow.TransferPayload;
import com.eventflow.TransferProcessor;
import com.eventflow.TransferResult;
import com.eventflow.Worker;
import com.eventflow.EventType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventFlowConfig {

    @Bean
    public AccountStore accountStore() {
        return new AccountStore();
    }

    @Bean
    public TransferProcessor transferProcessor(AccountStore accountStore) {
        return new TransferProcessor(accountStore);
    }

    @Bean
    public EventQueue eventQueue() {
        return new EventQueue();
    }

    @Bean
    public ResultStore resultStore() {
        return new ResultStore();
    }

    @Bean
    public ProcessorRegistration<TransferPayload, TransferResult> transferProcessorRegistration(
            TransferProcessor transferProcessor) {
        return new ProcessorRegistration<>(TransferPayload.class, transferProcessor);
    }

    @Bean
    public Map<EventType, ProcessorRegistration<?, ?>> registry(
            ProcessorRegistration<TransferPayload, TransferResult> transferProcessorRegistration) {
        Map<EventType, ProcessorRegistration<?, ?>> registryMap = new HashMap<>();
        registryMap.put(EventType.TRANSACTION_CREATED, transferProcessorRegistration);
        return registryMap;
    }

    @Bean
    public Worker worker(EventQueue eventQueue, ResultStore resultStore,
            Map<EventType, ProcessorRegistration<?, ?>> registry) {
        return new Worker(eventQueue, resultStore, registry);
    }

}
