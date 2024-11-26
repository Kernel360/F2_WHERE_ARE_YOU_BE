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
public class LeagueBatchConfig {

	@Bean
	public Job leagueManagerJob(
		Step cancelLeague,
		JobRepository jobRepository
	) {
		return new JobBuilder("leagueManagerJob", jobRepository)
			.start(cancelLeague)
			.build();
	}

	@Bean
	public Step cancelLeague(@Qualifier("leagueItemReader") ItemReader<League> reader,
		@Qualifier("leagueItemProcessor") ItemProcessor<League, League> processor,
		@Qualifier("leagueItemWriter") ItemWriter<League> writer, JobRepository jobRepository,
		PlatformTransactionManager leagueTransactionManager) {
		return new StepBuilder("cancelLeague", jobRepository)
			.<League, League>chunk(10, leagueTransactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean(name = "leagueTransactionManager")
	public PlatformTransactionManager leagueTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}

