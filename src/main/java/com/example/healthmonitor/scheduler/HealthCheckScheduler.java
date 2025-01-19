package com.example.healthmonitor.scheduler;

import com.example.healthmonitor.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckScheduler {

    private final HealthCheckService healthCheckService;

    @Value("${health.interval}")
    private int interval;

    public HealthCheckScheduler(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @Scheduled(fixedRateString = "${health.interval}000")
    public void performHealthCheck() {
        healthCheckService.checkHealth();
    }
}