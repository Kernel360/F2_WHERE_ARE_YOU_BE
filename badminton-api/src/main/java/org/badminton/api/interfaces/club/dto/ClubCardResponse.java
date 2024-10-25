package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;
import java.util.Map;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.ClubMemberCountByTier;
import org.badminton.domain.domain.member.entity.Member;

public record ClubCardResponse(
	Long clubId,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	ClubMemberCountByTier clubMemberCountByTier
) {

	public static ClubCardResponse clubEntityToClubsCardResponse(Club club,
		Map<Member.MemberTier, Long> tierCounts) {
		return new ClubCardResponse(club.getClubId(), club.getClubName(), club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getModifiedAt(),
			ClubMemberCountByTier.ofClubMemberCountResponse(tierCounts)
		);
	}
}
