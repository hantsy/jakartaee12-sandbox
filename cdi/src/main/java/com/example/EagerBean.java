package com.example;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Eager;

import java.time.Instant;
import java.util.logging.Logger;

// @Eager (CDI 5.0) marks an @ApplicationScoped bean for eager initialization:
// its @PostConstruct runs no later than the container's Startup event, instead
// of lazily on first injection.
@Eager
@ApplicationScoped
public class EagerBean {

    private static final Logger LOG = Logger.getLogger(EagerBean.class.getName());

    private Instant startedAt;

    @PostConstruct
    public void init() {
        this.startedAt = Instant.now();
        LOG.info("EagerBean eagerly initialized at " + startedAt);
    }

    public Instant getStartedAt() {
        return startedAt;
    }
}
