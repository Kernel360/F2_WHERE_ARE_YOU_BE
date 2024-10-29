package org.badminton.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@EnableJpaRepositories(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@ComponentScan(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@EnableJpaAuditing
public class JpaConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
