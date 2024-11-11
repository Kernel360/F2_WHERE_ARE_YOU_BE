package springproject.badmintonbatch.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class LeagueScheduler {
	private final JobLauncher jobLauncher;
	private final Job insertSqlJob;

	@Scheduled(cron = "0 0 0 * * ?")
	public void runInsertLeague() {
		try {
			jobLauncher.run(insertSqlJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}
}
