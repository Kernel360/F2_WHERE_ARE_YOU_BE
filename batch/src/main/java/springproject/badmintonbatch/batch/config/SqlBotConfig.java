package springproject.badmintonbatch.batch.config;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class SqlBotConfig {
	@Bean
	public Job insertSqlJob(
		Step createLeagueStep,
		Step participateLeagueStep,
		// Step doublesSetMatchStep,
		JobRepository jobRepository) {
		return new JobBuilder("insertSqlJob", jobRepository) // Job 이름 수정
			.start(createLeagueStep)
			.next(participateLeagueStep)
			.build();
	}

	// 오늘 리그 20개 생성
	@Bean
	public Step createLeagueStep(
		ItemReader<League> leagueReader,
		ItemProcessor<League, League> processor,
		ItemWriter<League> writer,
		JobRepository jobRepository,
		PlatformTransactionManager sqlTransactionManager) {
		return new StepBuilder("createLeagueStep", jobRepository)
			.<League, League>chunk(1, sqlTransactionManager)
			.reader(leagueReader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	// 오늘 리그 참가
	@Bean
	public Step participateLeagueStep(
		ItemReader<LeagueParticipant> leagueParticipantReader,
		ItemProcessor<LeagueParticipant, LeagueParticipant> processor,
		JobRepository jobRepository,
		ItemWriter<LeagueParticipant> leagueParticipantWriter,
		PlatformTransactionManager sqlTransactionManager) {
		return new StepBuilder("participateLeagueStep", jobRepository)
			.<LeagueParticipant, LeagueParticipant>chunk(1, sqlTransactionManager) // 제네릭 수정
			.reader(leagueParticipantReader)
			.processor(processor)
			.writer(leagueParticipantWriter)
			.faultTolerant()
			.skip(NullPointerException.class)
			.skipLimit(Integer.MAX_VALUE)
			.build();
	}

	@Bean
	public PlatformTransactionManager sqlTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}

