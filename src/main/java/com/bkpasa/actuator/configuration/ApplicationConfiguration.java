package com.bkpasa.actuator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bkpasa.health.indicator.KafkaHealthIndicator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class ApplicationConfiguration {

    @Bean
    HealthIndicator kafkaHealthIndicator() {
        return new KafkaHealthIndicator();
    }

    @Bean
    Counter simpleCounter(@Autowired MeterRegistry registry) {
        return registry.counter("simple.counter");
    }

}
