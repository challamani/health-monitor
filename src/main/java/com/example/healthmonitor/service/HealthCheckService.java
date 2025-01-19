package com.example.healthmonitor.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckService {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    private final RestTemplate restTemplate;
    private final Counter successCounter;
    private final Counter failureCounter;

    @Value("${health.endpoint}")
    private String endpoint;

    @Value("${health.expectedStatus}")
    private int expectedStatus;

    public HealthCheckService(MeterRegistry meterRegistry) {
        this.restTemplate = new RestTemplate();
        this.successCounter = meterRegistry.counter("healthchecks.success");
        this.failureCounter = meterRegistry.counter("healthchecks.failure");
    }

    public void checkHealth() {
        logger.info("Starting health check for endpoint: {}", endpoint);
        try {
            int status = restTemplate.getForEntity(endpoint, String.class).getStatusCodeValue();
            if (status == expectedStatus) {
                successCounter.increment();
                logger.info("Health check successful for endpoint: {}. Status code: {}", endpoint, status);
            } else {
                failureCounter.increment();
                logger.warn("Health check failed for endpoint: {}. Expected status: {}, but got: {}", endpoint, expectedStatus, status);
            }
        } catch (Exception e) {
            failureCounter.increment();
            logger.error("Exception occurred during health check for endpoint: {}. Error: {}", endpoint, e.getMessage());
        }
    }
}