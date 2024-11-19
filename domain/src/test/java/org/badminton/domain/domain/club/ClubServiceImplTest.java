package org.badminton.domain.domain.club;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.badminton.domain.common.exception.club.ClubNameDuplicateException;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ClubServiceImplTest {

	@InjectMocks
	private ClubServiceImpl clubService;

	@Mock
	private ClubReader clubReader;

	@Mock
	private ClubStore clubStore;

	@Mock
	private ClubApplyReader clubApplyReader;

	@Test
	@DisplayName("동호회 전체 조회 테스트")
	void readAllClubsTest() {
		// Arrange
		var pageable = PageRequest.of(0, 10);
		Page<Club> mockClubs = new PageImpl<>(List.of(new Club("Club1", "Description1", "Image1")));
		when(clubReader.readAllClubs(pageable)).thenReturn(mockClubs);

		// Act
		Page<ClubCardInfo> result = clubService.readAllClubs(pageable);

		// Assert
		assertNotNull(result);
		verify(clubReader, times(1)).readAllClubs(pageable);
	}

	@Test
	@DisplayName("동호회 검색 테스트")
	void searchClubTest() {
		// Arrange
		String keyword = "Club";
		var pageable = PageRequest.of(0, 10);

		Club mockClub = new Club("Club1", "Description1", "Image1");
		Page<Club> mockClubs = new PageImpl<>(List.of(mockClub));

		// Mock clubReader behavior
		when(clubReader.keywordSearch(keyword, pageable)).thenReturn(mockClubs);

		// Act
		Page<ClubCardInfo> result = clubService.searchClubs(keyword, pageable);

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.getContent().size());
		verify(clubReader, times(1)).keywordSearch(keyword, pageable);
	}

	@Test
	@DisplayName("동호회 생성 테스트")
	void createClubTest() {
		// Arrange
		ClubCreateCommand createCommand = new ClubCreateCommand("New Club", "Description", "Image");
		Club mockClub = new Club("New Club", "Description", "Image");
		when(clubReader.UniqueClubName("New Club")).thenReturn(false);
		when(clubStore.store(any(Club.class))).thenReturn(mockClub);

		// Act
		ClubCreateInfo result = clubService.createClub(createCommand);

		// Assert
		assertNotNull(result);
		verify(clubReader, times(1)).UniqueClubName("New Club");
		verify(clubStore, times(1)).store(any(Club.class));
	}

	@Test
	@DisplayName("동호회 생성 시 이름 중복 예외 처리 테스트")
	void createClubDuplicateTest() {
		// Arrange
		ClubCreateCommand createCommand = new ClubCreateCommand("Existing Club", "Description", "Image");
		when(clubReader.UniqueClubName("Existing Club")).thenReturn(true);

		// Act & Assert
		assertThrows(ClubNameDuplicateException.class, () -> clubService.createClub(createCommand));
		verify(clubReader, times(1)).UniqueClubName("Existing Club");
		verify(clubStore, never()).store(any(Club.class));
	}

	@Test
	@DisplayName("동호회 수정 테스트")
	void updateClubTest() {
		// Arrange
		String clubToken = "club123";
		ClubUpdateCommand updateCommand = new ClubUpdateCommand("Updated Name", "Updated Description", "Updated Image");
		Club mockClub = mock(Club.class);
		when(clubReader.readExistingClub(clubToken)).thenReturn(mockClub);
		when(clubStore.store(mockClub)).thenReturn(mockClub);

		// Act
		ClubUpdateInfo result = clubService.updateClub(updateCommand, clubToken);

		// Assert
		assertNotNull(result);
		verify(clubReader, times(1)).readExistingClub(clubToken);
		verify(clubStore, times(1)).store(mockClub);
	}

	@Test
	@DisplayName("동호회 삭제 테스트")
	void deleteClubTest() {
		// Arrange
		String clubToken = "club123";
		Club mockClub = mock(Club.class);
		when(clubReader.readClub(clubToken)).thenReturn(mockClub);
		when(clubStore.store(mockClub)).thenReturn(mockClub);

		// Act
		ClubDeleteInfo result = clubService.deleteClub(clubToken);

		// Assert
		assertNotNull(result);
		verify(clubReader, times(1)).readClub(clubToken);
		verify(clubStore, times(1)).store(mockClub);
	}

	@Test
	@DisplayName("동호회 조회 테스트")
	void readClubTest() {
		// Arrange
		Long clubId = 1L;
		Club mockClub = new Club("Club1", "Description1", "Image1");
		when(clubReader.readClubByClubId(clubId)).thenReturn(mockClub);

		// Act
		ClubCardInfo result = clubService.readClubById(clubId);

		// Assert
		assertNotNull(result);
		verify(clubReader, times(1)).readClubByClubId(clubId);
	}

	@Test
	@DisplayName("동호회 지원자 조회 테스트")
	void readClubApplicantsTest() {
		// Arrange
		String clubToken = "club123";
		List<ClubApplicantInfo> mockApplicants = List.of(
			new ClubApplicantInfo(1L, "name", Member.MemberTier.BRONZE, "applyReason",
				ClubApply.ApplyStatus.PENDING));
		when(clubApplyReader.getClubApplyByClubToken(clubToken, ClubApply.ApplyStatus.PENDING))
			.thenReturn(mockApplicants);

		// Act
		List<ClubApplicantInfo> result = clubService.readClubApplicants(clubToken);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(clubApplyReader, times(1)).getClubApplyByClubToken(clubToken, ClubApply.ApplyStatus.PENDING);
	}
}
