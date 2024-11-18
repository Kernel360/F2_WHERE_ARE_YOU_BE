package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.Map;

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

	public static ClubDetailsInfo from(ClubSummaryInfo clubSummaryInfo,
		Map<Member.MemberTier, Long> memberCountByTier, int clubMembersCount) {
		return new ClubDetailsInfo(
			clubSummaryInfo.clubId(),
			clubSummaryInfo.clubToken(),
			clubSummaryInfo.clubName(),
			clubSummaryInfo.clubDescription(),
			clubSummaryInfo.clubImage(),
			memberCountByTier.get(Member.MemberTier.GOLD),
			memberCountByTier.get(Member.MemberTier.SILVER),
			memberCountByTier.get(Member.MemberTier.BRONZE),
			clubMembersCount,
			clubSummaryInfo.createdAt()
		);
	}
}