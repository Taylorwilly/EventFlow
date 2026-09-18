package com.eventflow.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventflow.service.TransferService;

@RestController
public class EventController {
    @GetMapping("/health")
    @ResponseBody
    public String hello() {
        return "EventFlow is running";
    }

    public EventController(TransferService transferService) {

    }
}
