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
public class BatchScheduler {

	private final JobLauncher jobLauncher;
	private final Job sendEmailJob;
	private final Job leagueManagerJob;
	private final Job leagueStatusUpdateJob;
	private final Job memberLiftJob;

	@Scheduled(fixedRate = 10000)
	public void updateLeagueStatus() {
		log.info("Updating league status");
		try {
			jobLauncher.run(leagueStatusUpdateJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(fixedRate = 10000)
	public void liftClubMember() {
		log.info("Lift Club Member");
		try {
			jobLauncher.run(memberLiftJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(fixedRate = 10000)
	public void runDeleteMemberJob() {
		log.info("Sending Email");
		try {
			jobLauncher.run(sendEmailJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(fixedRate = 10000)
	public void changeLeagueStatusJob() {
		log.info("change league status");
		try {
			jobLauncher.run(leagueManagerJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}
}
