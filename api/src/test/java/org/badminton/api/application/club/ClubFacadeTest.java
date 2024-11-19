package org.badminton.api.application.club;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubDetailsInfo;
import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class ClubFacadeTest {

	@InjectMocks
	private ClubFacade clubFacade;

	@Mock
	private ClubService clubService;

	@Mock
	private ClubMemberService clubMemberService;

	@Mock
	private ClubStatisticsService clubStatisticsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("동호회 전체 조회 테스트")
	void readAllClubsTest() {
		// Arrange
		int page = 0;
		int size = 10;
		String sort = "name";
		PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
		Page<ClubCardInfo> mockPage = new PageImpl<>(List.of(
			new ClubCardInfo(1L, "club_token_01", "clubName", "clubDescription", "clubImage", LocalDateTime.now(),
				LocalDateTime.now(), 3L, 3L, 3L)));
		when(clubService.readAllClubs(pageable)).thenReturn(mockPage);

		// Act
		Page<ClubCardInfo> result = clubFacade.readAllClubs(page, size, sort);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(clubService, times(1)).readAllClubs(pageable);
	}

	@Test
	@DisplayName("동호회 조회 테스트")
	void readClubTest() {
		// Arrange
		String clubToken = "club123";
		ClubSummaryInfo mockSummaryInfo = mock(ClubSummaryInfo.class);
		Map<Member.MemberTier, Long> memberCountByTier = Map.of(
			Member.MemberTier.GOLD, 10L,
			Member.MemberTier.SILVER, 20L,
			Member.MemberTier.BRONZE, 30L
		);
		int mockClubMemberCount = 60;

		when(clubService.readClub(clubToken)).thenReturn(mockSummaryInfo);
		when(mockSummaryInfo.clubId()).thenReturn(1L);
		when(mockSummaryInfo.clubToken()).thenReturn(clubToken);
		when(mockSummaryInfo.clubName()).thenReturn("Test Club");
		when(mockSummaryInfo.clubDescription()).thenReturn("This is a test club.");
		when(mockSummaryInfo.clubImage()).thenReturn("test-image.jpg");
		when(mockSummaryInfo.createdAt()).thenReturn(LocalDateTime.now());

		// Act
		ClubDetailsInfo result = ClubDetailsInfo.from(mockSummaryInfo, memberCountByTier, mockClubMemberCount);

		// Assert
		assertNotNull(result, "결과는 null이 아니어야 합니다.");
		assertEquals(1L, result.clubId());
		assertEquals("Test Club", result.clubName());
		assertEquals(10L, result.goldClubMemberCount());
		assertEquals(20L, result.silverClubMemberCount());
		assertEquals(30L, result.bronzeClubMemberCount());
		assertEquals(mockClubMemberCount, result.clubMemberCount());
	}

	@Test
	@DisplayName("동호회 검색 테스트")
	void searchClubsTest() {
		// Arrange
		String keyword = "badminton";
		int page = 0;
		int size = 10;
		String sort = "name";
		PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
		Page<ClubCardInfo> mockPage = new PageImpl<>(List.of(
			new ClubCardInfo(1L, "club_token_01", "clubName", "clubDescription", "clubImage", LocalDateTime.now(),
				LocalDateTime.now(), 3L, 3L, 3L)));
		when(clubService.searchClubs(keyword, pageable)).thenReturn(mockPage);

		// Act
		Page<ClubCardInfo> result = clubFacade.searchClubs(keyword, page, size, sort);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(clubService, times(1)).searchClubs(keyword, pageable);
	}

	@Test
	@DisplayName("동호회 생성 테스트")
	void createClubTest() {
		// Arrange
		ClubCreateCommand createCommand = mock(ClubCreateCommand.class);
		String memberToken = "member123";
		ClubCreateInfo mockClubCreateInfo = mock(ClubCreateInfo.class);
		when(clubService.createClub(createCommand)).thenReturn(mockClubCreateInfo);

		// Act
		ClubCreateInfo result = clubFacade.createClub(createCommand, memberToken);

		// Assert
		assertNotNull(result);
		verify(clubService, times(1)).createClub(createCommand);
		verify(clubMemberService, times(1)).clubMemberOwner(memberToken, mockClubCreateInfo);
		verify(clubStatisticsService, times(1)).createStatistic(mockClubCreateInfo);
	}

	@Test
	@DisplayName("동호회 업데이트 테스트")
	void updateClubTest() {
		// Arrange
		ClubUpdateCommand clubUpdateCommand = mock(ClubUpdateCommand.class);
		String clubToken = "club123";
		ClubUpdateInfo mockUpdateInfo = mock(ClubUpdateInfo.class);
		when(clubService.updateClub(clubUpdateCommand, clubToken)).thenReturn(mockUpdateInfo);

		// Act
		ClubUpdateInfo result = clubFacade.updateClubInfo(clubUpdateCommand, clubToken);

		// Assert
		assertNotNull(result);
		verify(clubService, times(1)).updateClub(clubUpdateCommand, clubToken);
	}

	@Test
	@DisplayName("동호회 삭제 테스트")
	void deleteClubTest() {
		// Arrange
		String clubToken = "club123";
		ClubDeleteInfo mockDeleteInfo = mock(ClubDeleteInfo.class);
		when(clubService.deleteClub(clubToken)).thenReturn(mockDeleteInfo);

		// Act
		ClubDeleteInfo result = clubFacade.deleteClubInfo(clubToken);

		// Assert
		assertNotNull(result);
		verify(clubMemberService, times(1)).deleteAllClubMembers(clubToken);
		verify(clubService, times(1)).deleteClub(clubToken);
	}

	@Test
	@DisplayName("동호회 지원자 조회 테스트")
	void readClubApplicantsTest() {
		// Arrange
		String clubToken = "club123";
		List<ClubApplicantInfo> mockApplicants = List.of(mock(ClubApplicantInfo.class));
		when(clubService.readClubApplicants(clubToken)).thenReturn(mockApplicants);

		// Act
		List<ClubApplicantInfo> result = clubFacade.readClubApplicants(clubToken);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(clubService, times(1)).readClubApplicants(clubToken);
	}
}
