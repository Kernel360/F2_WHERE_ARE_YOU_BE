package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.Map;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.ClubMemberCountByTier;
import org.badminton.domain.domain.member.entity.Member;

public record ClubCardInfo(
	Long clubId,
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	ClubMemberCountByTier clubMemberCountByTier
) {
	public static ClubCardInfo clubEntityToClubsCardResponse(Club club,
		Map<Member.MemberTier, Long> tierCounts) {
		return new ClubCardInfo(club.getClubId(), club.getClubToken(), club.getClubName(), club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getModifiedAt(),
			ClubMemberCountByTier.ofClubMemberCountResponse(tierCounts)
		);
	}
}
