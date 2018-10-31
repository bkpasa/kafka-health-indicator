package com.bkpasa.actuator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@RestController
public class HelloController {

    private Counter callCounter = Metrics.counter("calls");

    @Autowired
    Counter simpleCounter;

    @GetMapping("/call")
    public Map<String, String> call() {
        callCounter.increment(5.0);
        simpleCounter.increment();
        return new HashMap<String, String>() {

            private static final long serialVersionUID = 1L;

            {
                put("hello", "world");
            }
        };
    }
}
