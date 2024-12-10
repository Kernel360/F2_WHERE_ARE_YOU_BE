package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.member.entity.Member;

public record ClubDetailsInfo(
	Long clubId,
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	Long goldClubMemberCount,
	Long silverClubMemberCount,
	Long bronzeClubMemberCount,
	int clubMemberCount,
	LocalDateTime createdAt
) {

	public static ClubDetailsInfo from(ClubSummaryInfo clubSummaryInfo, int clubMembersCount) {
		return new ClubDetailsInfo(
			clubSummaryInfo.clubId(),
			clubSummaryInfo.clubToken(),
			clubSummaryInfo.clubName(),
			clubSummaryInfo.clubDescription(),
			clubSummaryInfo.clubImage(),
			clubSummaryInfo.getClubMemberCountByTier().get(Member.MemberTier.GOLD),
			clubSummaryInfo.getClubMemberCountByTier().get(Member.MemberTier.SILVER),
			clubSummaryInfo.getClubMemberCountByTier().get(Member.MemberTier.BRONZE),
			clubMembersCount,
			clubSummaryInfo.createdAt()
		);
	}
}
