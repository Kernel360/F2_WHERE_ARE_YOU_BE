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

	@Scheduled(fixedRate = 60000)
	public void updateLeagueStatus() {
		log.info("리그 상태 업데이트");
		try {
			jobLauncher.run(leagueStatusUpdateJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(fixedRate = 1800000)
	public void liftClubMember() {
		log.info("클럽 맴버 정지 해제");
		try {
			jobLauncher.run(memberLiftJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(fixedRate = 300000)
	public void runDeleteMemberJob() {
		log.info("메일 보내기");
		try {
			jobLauncher.run(sendEmailJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	@Scheduled(cron = "0 0 0,6,12,18 * * ?")
	public void changeLeagueStatusJob() {
		log.info("리그 상태 변경");
		try {
			jobLauncher.run(leagueManagerJob, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}

	}
}
