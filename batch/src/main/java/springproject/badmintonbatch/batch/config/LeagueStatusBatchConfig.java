package springproject.badmintonbatch.batch.config;

import org.badminton.domain.domain.league.entity.League;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class LeagueStatusBatchConfig {

	@Bean
	public Job leagueStatusUpdateJob(Step updateLeagueStatusStep, JobRepository jobRepository) {
		return new JobBuilder("leagueStatusUpdateJob", jobRepository)
			.start(updateLeagueStatusStep).build();
	}

	@Bean
	public Step updateLeagueStatusStep(@Qualifier("leagueStatusItemReader") ItemReader<League> reader,
		@Qualifier("leagueStatusItemProcessor") ItemProcessor<League, League> processor,
		@Qualifier("leagueStatusItemWriter") ItemWriter<League> writer, JobRepository jobRepository,
		PlatformTransactionManager transactionManager) {
		return new StepBuilder("updateLeagueStatus", jobRepository)
			.<League, League>chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean
	PlatformTransactionManager leagueStatusTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
