package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubCreateInfo(
	Long clubId,
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	boolean isClubDeleted,
	List<ClubMember> clubMembers,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ClubCreateInfo toClubCreateInfo(Club club) {
		return new ClubCreateInfo(
			club.getClubId(),
			club.getClubToken(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.isClubDeleted(),
			club.getClubMembers(),
			club.getCreatedAt(),
			club.getModifiedAt());
	}

}
