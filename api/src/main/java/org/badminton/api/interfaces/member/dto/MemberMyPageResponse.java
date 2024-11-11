package org.badminton.api.interfaces.member.dto;

import java.util.List;

import org.badminton.api.interfaces.clubmember.dto.ClubMemberMyPageResponse;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;

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

	int winCount,

	int loseCount,

	int drawCount,

	int matchCount,

	@Schema(description = "ClubMember information")
	List<ClubMemberMyPageResponse> clubMemberMyPageResponses

) {
	public static MemberMyPageResponse from(MemberMyPageInfo info) {
		List<ClubMemberMyPageResponse> clubMemberMyPageResponses = null;

		if (info.clubMemberMyPageInfos() != null) {
			clubMemberMyPageResponses = info.clubMemberMyPageInfos().stream()
				.map((ClubMemberMyPageResponse::from))
				.toList();
		}

		return new MemberMyPageResponse(
			info.memberToken(),
			info.name(),
			info.email(),
			info.profileImage(),
			info.tier(),
			info.winCount(),
			info.loseCount(),
			info.drawCount(),
			info.matchCount(),
			clubMemberMyPageResponses
		);
	}
}