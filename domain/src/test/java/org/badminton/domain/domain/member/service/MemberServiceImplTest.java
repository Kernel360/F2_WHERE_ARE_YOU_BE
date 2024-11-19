package org.badminton.domain.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MemberServiceImplTest {

	@Mock
	private MemberReader memberReader;

	@Mock
	private MemberStore memberStore;

	@InjectMocks
	private MemberServiceImpl memberService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("회원 마이페이지 정보 조회 테스트")
	void getMemberInfoTest() {
		String memberToken = "testToken";
		Member mockMember = mock(Member.class);
		LeagueRecordInfo mockLeagueRecordInfo = mock(LeagueRecordInfo.class);
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos = List.of(mock(ClubMemberMyPageInfo.class));

		when(memberReader.getMember(memberToken)).thenReturn(mockMember);

		MemberMyPageInfo result = memberService.getMemberInfo(memberToken, mockLeagueRecordInfo, clubMemberMyPageInfos);

		assertNotNull(result);
		verify(memberReader).getMember(memberToken);
	}

	@Test
	@DisplayName("회원 간단 정보 조회 테스트")
	void getSimpleMember() {
		String memberToken = "testToken";
		Member mockMember = mock(Member.class);
		SimpleMemberInfo expectedInfo = SimpleMemberInfo.from(mockMember);

		when(memberReader.getMember(memberToken)).thenReturn(mockMember);

		SimpleMemberInfo result = memberService.getSimpleMember(memberToken);

		assertNotNull(result);
		assertEquals(expectedInfo, result);
		verify(memberReader).getMember(memberToken);
	}

	@Test
	@DisplayName("회원 프로필 업데이트 테스트")
	void updateProfile() {
		String memberToken = "testToken";
		String newImageUrl = "newImageUrl";
		String newName = "newName";
		Member mockMember = mock(Member.class);

		when(memberReader.getMember(memberToken)).thenReturn(mockMember);

		MemberUpdateInfo expectedInfo = MemberUpdateInfo.fromMemberEntity(mockMember);
		MemberUpdateInfo result = memberService.updateProfile(memberToken, newImageUrl, newName);

		assertNotNull(result);
		assertEquals(expectedInfo, result);
		verify(memberReader).getMember(memberToken);
		verify(mockMember).updateMember(newImageUrl, newName);
		verify(memberStore).store(mockMember);
	}
}