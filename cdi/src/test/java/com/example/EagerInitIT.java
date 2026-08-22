package com.example;

import com.example.EagerBean;
import com.example.LazyBean;
import com.example.StartupBean;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
public class EagerInitIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "eager-init.war")
                .addClasses(EagerBean.class, StartupBean.class, LazyBean.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private EagerBean eagerBean;

    @Inject
    private StartupBean startupBean;

    @Inject
    private LazyBean lazyBean;

    @Test
    public void testInitializationOrder() {
        assertNotNull(eagerBean.getStartedAt(), "The @Eager bean should be initialized");
        assertNotNull(startupBean.getStartupTime(), "The Startup event should have been observed");
        assertNotNull(lazyBean.getStartedAt(), "The lazy bean should be initialized on injection");

        // @Eager bean is initialized no later than the Startup event
        assertFalse(eagerBean.getStartedAt().isAfter(startupBean.getStartupTime()),
                "The @Eager bean must be initialized no later than the Startup event");

        // The Startup event fires before the lazy bean is first injected
        assertTrue(startupBean.getStartupTime().isBefore(lazyBean.getStartedAt()),
                "The Startup event must fire before the lazy bean is initialized");

        // By transitivity, the @Eager bean is initialized before the lazy bean
        assertTrue(eagerBean.getStartedAt().isBefore(lazyBean.getStartedAt()),
                "The @Eager bean must be initialized before the lazy bean");
    }
}
