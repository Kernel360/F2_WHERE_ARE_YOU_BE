package springproject.badmintonbatch.batch.config;

import org.badminton.domain.domain.mail.entity.Mail;
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
public class EmailBatchConfig {

	@Bean
	public Job sendEmailJob(Step sendEmailStep, JobRepository jobRepository) {
		return new JobBuilder("sendEmailJob", jobRepository)
			.start(sendEmailStep)
			.build();
	}

	@Bean
	public Step sendEmailStep(ItemReader<Mail> reader, ItemProcessor<Mail, Mail> processor,
		ItemWriter<Mail> writer, JobRepository jobRepository,
		PlatformTransactionManager emailTransactionManager) {
		return new StepBuilder("sendEmailStep", jobRepository)
			.<Mail, Mail>chunk(10, emailTransactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean(name = "emailTransactionManager")
	public PlatformTransactionManager emailTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}

