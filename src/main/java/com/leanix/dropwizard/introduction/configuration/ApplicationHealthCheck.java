package com.leanix.dropwizard.introduction.configuration;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ApplicationHealthCheck extends HealthCheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHealthCheck.class);

    private final SessionFactory sessionFactory;

    @Inject
    public ApplicationHealthCheck(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        LOGGER.info("PostgreSQL ApplicationHealthCheck initialized");
    }

    @Override
    protected Result check() throws Exception {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("SELECT 1").uniqueResult();
            transaction.commit();
            return Result.healthy();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Failed to connect to PostgreSQL", e);
            return Result.unhealthy("Cannot connect to PostgreSQL: " + e.getMessage());
        }
    }
}

