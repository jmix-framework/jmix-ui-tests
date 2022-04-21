package io.jmix.tests.ui.test.webdav;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class WebDavContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of("jmix.webdav.auto-generate-unique-resource-uri=false")
                .applyTo(applicationContext.getEnvironment());
    }
}
