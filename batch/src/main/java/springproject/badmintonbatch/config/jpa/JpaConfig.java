package springproject.badmintonbatch.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@EnableJpaRepositories(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@ComponentScan(basePackages = {"org.badminton.domain", "org.badminton.infrastructure"})
@EnableJpaAuditing
public class JpaConfig {
}
