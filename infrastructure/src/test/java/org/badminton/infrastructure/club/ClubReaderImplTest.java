//package org.badminton.infrastructure.club;
//
//import jakarta.transaction.Transactional;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import javax.sql.DataSource;
//import net.datafaker.Faker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//
//@Transactional
//@Rollback
//@ContextConfiguration(classes = {TestConfig.class})
//class ClubReaderImplTest {
//    @Autowired
//    private ClubRepository clubRepository;
//
//    private JdbcTemplate jdbcTemplate;
//    private Faker faker;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // 데이터베이스 연결 설정
//        DataSource dataSource = new DriverManagerDataSource(
//                "jdbc:mysql://localhost:3306/badminton", "root", "1234");
//
//        jdbcTemplate = new JdbcTemplate(dataSource);
//
//        // 테스트 데이터 삽입
////        jdbcTemplate.update(
////                "INSERT INTO club (clubToken, clubName, clubDescription, clubImage, isClubDeleted, createdAt, modifiedAt) VALUES (?, ?, ?, ?, ?,?,?)",
////                "club_token_2", "Test Club", "This is a test club description.", "test_image_url", false,
////                "2024-01-02 09:00:00", "2024-01-02 09:00:00");
//
//        // 테스트용 테이블 생성
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS club (" +
//                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(255) NOT NULL," +
//                "clubToken VARCHAR(255) UNIQUE NOT NULL," +
//                "isDeleted BOOLEAN DEFAULT FALSE" +
//                ");");
//
//        // JdbcTemplate 기반 ClubRepository 구현체 생성
//        clubRepository = new JdbcTemplateClubRepository(jdbcTemplate);
//        faker = new Faker();
//    }
//
//    // 클럽을 생성한다.
//
//    @Test
//    @Transactional
//    @DisplayName("클럽을 1000개 생성한다.")
//    void clubCreateThousand() {
//        Instant before = Instant.now();
//        String sql = "INSERT INTO club (clubToken, clubName, clubDescription, clubImage, isClubDeleted, createdAt, modifiedAt) VALUES (?, ?, ?, ?, ?,?,?)";
//        List<Object[]> batchArgs = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            String clubToken = "club_token_" + i + 3000;
//            String clubName = faker.company().name();
//            String clubDescription = faker.lorem().paragraph();
//            String clubImage = "image_" + faker.internet().uuid();
//            boolean isClubDeleted = faker.bool().bool();
//            String createdAt = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).toString();
//            String modifiedAt = createdAt;
//
//            batchArgs.add(new Object[]{
//                    clubToken,
//                    clubName,
//                    clubDescription,
//                    clubImage,
//                    isClubDeleted,
//                    createdAt,
//                    modifiedAt
//            });
//        }
//        jdbcTemplate.batchUpdate(sql, batchArgs);
//        Duration elapsed = Duration.between(before, Instant.now());
//        System.out.println("1000개의 클럽이 성공적으로 생성되었습니다.");
//        System.out.println((elapsed.getNano() / 100000) + " ms");
//
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("모든 클럽을 조회한다.")
//    void clubFindAllTest() {
//        Instant before = Instant.now();
//        for (int i = 0; i < 10000; i++) {
//            clubRepository.findByClubTokenAndIsClubDeletedFalse("club_token_" + i + 3000);
//        }
//        Duration elapsed = Duration.between(before, Instant.now());
//        System.out.println((elapsed.getNano() / 100000) + " ms");
//    }
//
//    //클럽을 삭제한다.
//    @Test
//    @Transactional
//    @DisplayName("클럽을 삭제한다.")
//    void deleteClub() {
//
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("unique를 붙여 성능을 측정한다.")
//    void tokenPerformanceTest() {
//
//    }
//}