package org.badminton.infrastructure.club;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@TestConfiguration
@ComponentScan(
        basePackages = "org.badminton.infrastructure.club",
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ClubRepository.class)
)
public class TestConfig {

    @Bean
    public ClubReaderImpl clubReaderImpl(ClubRepository clubRepository) {
        return new ClubReaderImpl(clubRepository);
    }
}
