package io.jmix.tests;

import io.jmix.dynattrui.panel.DynamicAttributesPanel;
import io.jmix.dynattrui.panel.DynamicAttributesPanelLoader;
import io.jmix.tests.component.dynamicattributespanel.UiTestsDynamicAttributesPanel;
import io.jmix.ui.sys.registration.ComponentRegistration;
import io.jmix.ui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class JmixUiTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmixUiTestsApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix="main.datasource")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public ComponentRegistration uiTestsDynamicAttributesPanel() {
		return ComponentRegistrationBuilder.create(DynamicAttributesPanel.NAME)
				.withComponentClass(UiTestsDynamicAttributesPanel.class)
				.withComponentLoaderClass(DynamicAttributesPanelLoader.class)
				.build();
	}
}
