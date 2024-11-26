package springproject.badmintonbatch.batch.config;

import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
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
public class MemberLiftBatchConfig {

	@Bean
	public Job memberLiftJob(Step memberLiftStep, JobRepository jobRepository) {
		return new JobBuilder("memberLiftJob", jobRepository)
			.start(memberLiftStep).build();
	}

	@Bean
	public Step memberLiftStep(ItemReader<ClubMemberBanRecord> reader,
		ItemProcessor<ClubMemberBanRecord, ClubMemberBanRecord> processor,
		ItemWriter<ClubMemberBanRecord> writer, JobRepository jobRepository,
		PlatformTransactionManager transactionManager) {
		return new StepBuilder("memberLift", jobRepository)
			.<ClubMemberBanRecord, ClubMemberBanRecord>chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean(name = "memberLiftTransactionManager")
	PlatformTransactionManager memberLiftTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
