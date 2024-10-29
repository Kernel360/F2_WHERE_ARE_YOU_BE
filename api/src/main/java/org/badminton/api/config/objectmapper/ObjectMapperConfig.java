package org.badminton.api.config.objectmapper;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@Configuration
public class ObjectMapperConfig {
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			builder.propertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
			builder.failOnUnknownProperties(false);
			builder.failOnEmptyBeans(false);
		};
	}
}