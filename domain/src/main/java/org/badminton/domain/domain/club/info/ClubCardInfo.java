package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.badminton.domain.domain.member.entity.Member;

public record ClubCardInfo(
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
	public static ClubCardInfo from(Club club) {
		return new ClubCardInfo(
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

	public static ClubCardInfo from(ClubCache club) {
		return new ClubCardInfo(
			club.clubToken(),
			club.clubName(),
			club.clubDescription(),
			club.clubImage(),
			club.createdAt(),
			club.modifiedAt(),
			club.goldClubMemberCount(),
			club.silverClubMemberCount(),
			club.bronzeClubMemberCount()
		);
	}
}
