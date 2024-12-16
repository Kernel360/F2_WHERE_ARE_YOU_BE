// package org.badminton.api.application.club;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
//
// import org.badminton.api.BadmintonApplication;
// import org.badminton.domain.domain.statistics.ClubStatisticsService;
// import org.badminton.infrastructure.statistics.ClubStatisticsRepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.transaction.annotation.Transactional;
//
// @SpringBootTest(classes = BadmintonApplication.class)
// @ActiveProfiles("test")
// @Transactional
// public class ClubReadConcurrencyTest {
//
// 	@Autowired
// 	private ClubFacade clubFacade;
//
// 	@Autowired
// 	private ClubStatisticsRepository clubStatisticsRepository;
//
// 	@Autowired
// 	private ClubStatisticsService clubStatisticsService;
//
// 	@Test
// 	void 동시성_동호회_조회_테스트() throws InterruptedException {
// 		///given
// 		int numberOfThreads = 100;
// 		ExecutorService executorService = Executors.newFixedThreadPool(20);
// 		CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
// 		//when
// 		for (int i = 0; i < numberOfThreads; i++) {
// 			executorService.submit(() -> {
// 				try {
// 					clubFacade.readClub("club_token_1");
// 				} catch (Exception e) {
// 					e.printStackTrace();
// 				} finally {
// 					latch.countDown();
// 				}
// 			});
// 		}
// 		// latch.countDown();
// 		latch.await();
//
// 		Thread.sleep(10000);
// 		executorService.shutdown();
//
// 		//then
// 		int visitedCount = clubStatisticsRepository.findByClubClubToken("club_token_1").getVisitedCount();
// 		assertThat(visitedCount).isEqualTo(210);
// 	}
// }
