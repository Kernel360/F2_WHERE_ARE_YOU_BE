package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.Map;

import org.badminton.domain.domain.club.ClubMemberCountByTier;
import org.badminton.domain.domain.member.entity.Member;

public record ClubDetailsInfo(
	Long clubId,
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	ClubMemberCountByTier clubMemberCountByTier,
	int clubMemberCount,
	LocalDateTime createdAt,
	boolean isClubMember
) {

	public static ClubDetailsInfo fromClubEntityAndMemberCountByTier(ClubSummaryInfo clubSummaryInfo,
		Map<Member.MemberTier, Long> memberCountByTier,
		boolean isClubMember, int clubMembersCount) {
		return new ClubDetailsInfo(
			clubSummaryInfo.clubId(),
			clubSummaryInfo.clubToken(),
			clubSummaryInfo.clubName(),
			clubSummaryInfo.clubDescription(),
			clubSummaryInfo.clubImage(),
			ClubMemberCountByTier.ofClubMemberCountResponse(memberCountByTier),
			clubMembersCount,
			clubSummaryInfo.createdAt(),
			isClubMember
		);
	}
}