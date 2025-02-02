package org.badminton.domain.domain.club.vo;

import java.time.LocalDateTime;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.member.entity.Member;

public record ClubCache(
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	Long goldClubMemberCount,
	Long silverClubMemberCount,
	Long bronzeClubMemberCount
) {
	public static ClubCache from(Club club) {
		return new ClubCache(
			club.getClubToken(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getModifiedAt(),
			club.getClubMemberCountByTier().get(Member.MemberTier.GOLD),
			club.getClubMemberCountByTier().get(Member.MemberTier.SILVER),
			club.getClubMemberCountByTier().get(Member.MemberTier.BRONZE)
		);
	}

}
