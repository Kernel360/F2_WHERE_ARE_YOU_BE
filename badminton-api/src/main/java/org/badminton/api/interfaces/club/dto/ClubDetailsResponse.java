package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;
import java.util.Map;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.ClubMemberCountByTier;
import org.badminton.domain.domain.member.entity.Member;

public record ClubDetailsResponse(
	Long clubId,
	String clubName,
	String clubDescription,
	String clubImage,
	ClubMemberCountByTier clubMemberCountByTier,
	int clubMemberCount,
	LocalDateTime createdAt,
	boolean isClubMember
) {

	public static ClubDetailsResponse fromClubEntityAndMemberCountByTier(Club club,
		Map<Member.MemberTier, Long> memberCountByTier,
		boolean isClubMember, int clubMembersCount) {
		return new ClubDetailsResponse(
			club.getClubId(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			ClubMemberCountByTier.ofClubMemberCountResponse(memberCountByTier),
			clubMembersCount,
			club.getCreatedAt(),
			isClubMember);
	}
}
