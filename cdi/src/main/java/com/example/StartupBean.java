package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;

import java.time.Instant;
import java.util.logging.Logger;

// Observes the CDI Startup event, fired once the application is initialized.
@ApplicationScoped
public class StartupBean {

    private static final Logger LOG = Logger.getLogger(StartupBean.class.getName());

    private Instant startupTime;

    public void onStartup(@Observes Startup event) {
        this.startupTime = Instant.now();
        LOG.info("Startup event observed at " + startupTime);
    }

    public Instant getStartupTime() {
        return startupTime;
    }
}
