package org.badminton.api.application.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.league.service.LeagueRecordService;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.match.info.SinglesMatchResultInfo;
import org.badminton.domain.domain.match.service.MatchResultService;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.badminton.domain.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

class MemberFacadeTest {

    @Mock
    private MemberService memberService;

    @Mock
    private LeagueRecordService leagueRecordService;

    @Mock
    private ClubMemberService clubMemberService;

    @Mock
    private MatchResultService matchResultService;

    @InjectMocks
    private MemberFacade memberFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원 마이페이지 조회 API 테스트")
    void getMemberMyPageInfoTest() {
        String memberToken = "testToken";

        List<ClubMemberMyPageInfo> clubMemberMyPageInfos = new ArrayList<>();
        when(clubMemberService.getClubMembers(memberToken)).thenReturn(clubMemberMyPageInfos);

        LeagueRecord leagueRecord = mock(LeagueRecord.class);
        when(leagueRecordService.getLeagueRecord(memberToken)).thenReturn(leagueRecord);

        LeagueRecordInfo leagueRecordInfo = LeagueRecordInfo.from(leagueRecord);
        MemberMyPageInfo expectedInfo = new MemberMyPageInfo("testToken", "김철수", "abcd@naver.com", "www.naver.com",
                Member.MemberTier.BRONZE, 3, 2, 1, 6, null);
        when(memberService.getMemberInfo(memberToken, leagueRecordInfo, clubMemberMyPageInfos)).thenReturn(
                expectedInfo);

        MemberMyPageInfo result = memberFacade.getMemberMyPageInfo(memberToken);

        assertNotNull(result);
        assertEquals(expectedInfo, result);

        verify(clubMemberService).getClubMembers(memberToken);
        verify(leagueRecordService).getLeagueRecord(memberToken);
        verify(memberService).getMemberInfo(memberToken, leagueRecordInfo, clubMemberMyPageInfos);
    }

    @Test
    @DisplayName("회원 정보 간단 조회 API 테스트")
    void getSimpleMemberTest() {
        String memberToken = "testToken";
        SimpleMemberInfo expectedInfo = new SimpleMemberInfo(null, "member_token_1", "김철수", "abcd@naver.com",
                Member.MemberTier.BRONZE,
                "www.naver.com");

        when(memberService.getSimpleMember(memberToken)).thenReturn(expectedInfo);

        SimpleMemberInfo result = memberFacade.getSimpleMember(memberToken);

        assertNotNull(result);
        assertEquals(expectedInfo, result);

        verify(memberService).getSimpleMember(memberToken);
    }

    @Test
    @DisplayName("회원 정보 업데이트 API 테스트")
    void updateProfileTest() {
        String memberToken = "testToken";
        String imageUrl = "image_url";
        String name = "name";
        MemberUpdateInfo expectedInfo = new MemberUpdateInfo("testToken", null, "김철수", "abc@naver.com", "providerId",
                "profileImage");

        when(memberService.updateProfile(memberToken, imageUrl, name)).thenReturn(expectedInfo);

        MemberUpdateInfo result = memberFacade.updateProfile(memberToken, imageUrl, name);

        assertNotNull(result);
        assertEquals(expectedInfo, result);

        verify(memberService).updateProfile(memberToken, imageUrl, name);
    }

    @Test
    @DisplayName("회원이 가입되어 있는 동호회 조회 API 테스트")
    void getMyClubsTest() {
        String memberToken = "testToken";
        List<ClubCardInfo> expectedClubs = new ArrayList<>();

        when(clubMemberService.getClubsByMemberToken(memberToken)).thenReturn(expectedClubs);

        List<ClubCardInfo> result = memberFacade.getMyClubs(memberToken);

        assertNotNull(result);
        assertEquals(expectedClubs, result);

        verify(clubMemberService).getClubsByMemberToken(memberToken);
    }

    @Test
    @DisplayName("회원 매치 전적 조회 API 테스트")
    void getMemberMatchResultsTest() {
        String memberToken = "testToken";
        int page = 0;
        int size = 10;

        // Mock ClubMemberMyPageInfo
        ClubMemberMyPageInfo clubMember = mock(ClubMemberMyPageInfo.class);
        when(clubMember.clubMemberId()).thenReturn(1L); // 반환 타입은 Long
        when(clubMemberService.getClubMembers(memberToken)).thenReturn(
                List.of(clubMember)); // 반환 타입은 List<ClubMemberMyPageInfo>

        // Mock MatchResultInfo
        MatchResultInfo matchResult = mock(MatchResultInfo.class);
        when(matchResult.matchId()).thenReturn(1L);
        when(matchResult.leagueId()).thenReturn(10L);
        when(matchResult.matchType()).thenReturn(MatchType.SINGLES);
        when(matchResult.singlesMatch()).thenReturn(mock(SinglesMatchResultInfo.class));
        when(matchResult.matchStatus()).thenReturn(MatchStatus.FINISHED);
        when(matchResult.leagueAt()).thenReturn(LocalDateTime.now());

        // MatchResultService의 동작 설정
        when(matchResultService.getAllMatchResultsByClubMember(1L)).thenReturn(List.of(matchResult));

        // Perform the test
        Page<MatchResultResponse> result = memberFacade.getMemberMatchResults(memberToken, page, size);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(matchResult.matchId(), result.getContent().get(0).matchId());

        // Verify 호출 확인
        verify(clubMemberService).getClubMembers(memberToken);
        verify(matchResultService).getAllMatchResultsByClubMember(1L);
    }

}
