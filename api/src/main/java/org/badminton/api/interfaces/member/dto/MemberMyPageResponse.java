package org.badminton.api.interfaces.member.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.badminton.api.interfaces.clubmember.dto.ClubMemberMyPageResponse;
import org.badminton.api.interfaces.league.dto.LeagueRecordResponse;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unified member response")
public record MemberMyPageResponse(
	@Schema(description = "Member Token", example = "token")
	String memberToken,

	@Schema(description = "Member name", example = "김철수")
	String name,

	@Schema(description = "Email", example = "example@email.com")
	String email,

	@Schema(description = "Profile image URL", example = "https://example.com/profile.jpg")
	String profileImage,

	@Schema(description = "Tier", example = "GOLD")
	Member.MemberTier tier,

	@Schema(description = "League record information")
	LeagueRecordResponse leagueRecordResponse,

	@Schema(description = "ClubMember information")
	List<ClubMemberMyPageResponse> clubMemberMyPageResponses

) {
	public static MemberMyPageResponse toMemberMyPageResponse(Member member,
		LeagueRecordInfo leagueRecordInfo,
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos
	) {

		List<ClubMemberMyPageResponse> clubMemberMyPageResponses = clubMemberMyPageInfos.stream()
			.map(ClubMemberMyPageResponse::toClubMemberMyPageResponse)
			.collect(Collectors.toList());

		return new MemberMyPageResponse(
			member.getMemberToken(),
			member.getName(),
			member.getEmail(),
			member.getProfileImage(),
			member.getTier(),
			LeagueRecordResponse.toLeagueRecordResponse(leagueRecordInfo),
			clubMemberMyPageResponses
		);
	}

	public static MemberMyPageResponse toMemberMyPageResponse(Member member,
		LeagueRecordInfo leagueRecordInfo) {
		return new MemberMyPageResponse(
			member.getMemberToken(),
			member.getName(),
			member.getEmail(),
			member.getProfileImage(),
			member.getTier(),
			LeagueRecordResponse.toLeagueRecordResponse(leagueRecordInfo),
			null
		);
	}
}