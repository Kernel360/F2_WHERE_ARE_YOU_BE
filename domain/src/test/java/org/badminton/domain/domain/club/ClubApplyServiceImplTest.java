package org.badminton.domain.domain.club;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.badminton.domain.common.exception.member.MemberAlreadyExistInClubException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClubApplyServiceImplTest {

	@Mock
	private ClubMemberStore clubMemberStore;

	@Mock
	private ClubMemberReader clubMemberReader;

	@Mock
	private ClubApplyReader clubApplyReader;

	@Mock
	private ClubApplyStore clubApplyStore;

	@Mock
	private ClubReader clubReader;

	@Mock
	private MemberReader memberReader;

	@InjectMocks
	private ClubApplyServiceImpl clubApplyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("가입 승인 테스트")
	void approveApplyingTest() {
		// Arrange
		Long clubApplyId = 1L;
		Club club = mock(Club.class);
		Member member = mock(Member.class);
		ClubApply clubApply = new ClubApply(club, member, "Reason");

		when(clubApplyReader.getClubApply(clubApplyId)).thenReturn(clubApply);
		when(clubMemberReader.checkIsClubMember(anyString(), anyString())).thenReturn(false);

		// Act
		ApproveApplyInfo result = clubApplyService.approveApplying(clubApplyId);

		// Assert
		verify(clubApplyReader, times(1)).getClubApply(clubApplyId);
		verify(clubApplyStore, times(1)).store(clubApply);
		verify(clubMemberStore, times(1)).store(any(ClubMember.class));
		assertNotNull(result);
		assertEquals(clubApply.getClubApplyId(), result.clubApplyId());
	}

	@Test
	@DisplayName("이미 존재하는 회원을 가입 승인 하려고 할 때 테스트")
	void approveApplyingWithExceptionWhenMemberAlreadyExistsTest() {
		// Arrange
		Long clubApplyId = 1L;
		Club club = mock(Club.class);
		Member member = mock(Member.class);
		ClubApply clubApply = new ClubApply(club, member, "Reason");

		// Mock values
		when(club.getClubToken()).thenReturn("clubToken");
		when(member.getMemberToken()).thenReturn("memberToken");
		when(clubApplyReader.getClubApply(clubApplyId)).thenReturn(clubApply);
		when(clubMemberReader.checkIsClubMember("memberToken", "clubToken")).thenReturn(true);

		// Act & Assert
		assertThrows(MemberAlreadyExistInClubException.class,
			() -> clubApplyService.approveApplying(clubApplyId));

		verify(clubApplyReader, times(1)).getClubApply(clubApplyId);
		verify(clubApplyStore, never()).store(any(ClubApply.class));
		verify(clubMemberStore, never()).store(any(ClubMember.class));
		verify(clubMemberReader, times(1)).checkIsClubMember("memberToken", "clubToken");
	}

	@Test
	@DisplayName("가입 신청 거절 테스트")
	void rejectApplyingTest() {
		// Arrange
		Long clubApplyId = 1L;
		Club club = mock(Club.class);
		Member member = mock(Member.class);
		ClubApply clubApply = new ClubApply(club, member, "Reason");

		when(clubApplyReader.getClubApply(clubApplyId)).thenReturn(clubApply);

		// Act
		RejectApplyInfo result = clubApplyService.rejectApplying(clubApplyId);

		// Assert
		verify(clubApplyReader, times(1)).getClubApply(clubApplyId);
		verify(clubApplyStore, times(1)).store(clubApply);
		assertNotNull(result);
		assertEquals(clubApply.getClubApplyId(), result.clubApplyId());
	}

	@Test
	@DisplayName("동호회 가입 신청 테스트")
	void applyClubTest() {
		// Arrange
		String memberToken = "memberToken";
		String clubToken = "clubToken";
		String applyReason = "Reason";

		Club club = mock(Club.class);
		Member member = mock(Member.class);

		when(clubReader.readClub(clubToken)).thenReturn(club);
		when(memberReader.getMember(memberToken)).thenReturn(member);
		doNothing().when(clubApplyReader).validateApply(clubToken, memberToken);

		clubApplyService.applyClub(memberToken, clubToken, applyReason);

		// Assert
		verify(clubReader, times(1)).readClub(clubToken);
		verify(memberReader, times(1)).getMember(memberToken);
		verify(clubApplyReader, times(1)).validateApply(clubToken, memberToken);
		verify(clubApplyStore, times(1)).store(any(ClubApply.class));

		// 캡처: 저장된 ClubApply 객체를 확인
		ArgumentCaptor<ClubApply> captor = ArgumentCaptor.forClass(ClubApply.class);
		verify(clubApplyStore).store(captor.capture());
		ClubApply capturedClubApply = captor.getValue();

		// ClubApply 객체 내부 값 검증
		assertNotNull(capturedClubApply);
		assertEquals(member, capturedClubApply.getMember()); // Member 객체 검증
		assertEquals(club, capturedClubApply.getClub());     // Club 객체 검증
		assertEquals(applyReason, capturedClubApply.getApplyReason()); // Reason 검증
	}

}
