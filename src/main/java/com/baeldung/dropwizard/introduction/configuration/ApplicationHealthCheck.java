package com.baeldung.dropwizard.introduction.configuration;

import com.codahale.metrics.health.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationHealthCheck extends HealthCheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHealthCheck.class);
    @Override
    protected Result check() throws Exception {
        LOGGER.info("Health check passed");
        return Result.healthy();
    }
}
