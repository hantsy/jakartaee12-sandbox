package com.example;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.logging.Logger;

// A non-eager @ApplicationScoped bean: its @PostConstruct runs lazily on first
// injection, so it is initialized strictly after an @Eager bean.
@ApplicationScoped
public class LazyBean {

    private static final Logger LOG = Logger.getLogger(LazyBean.class.getName());

    private Instant startedAt;

    @PostConstruct
    public void init() {
        this.startedAt = Instant.now();
        LOG.info("LazyBean initialized at " + startedAt);
    }

    public Instant getStartedAt() {
        return startedAt;
    }
}
