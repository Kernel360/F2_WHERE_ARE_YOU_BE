package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubUpdateInfo(
	Long clubId,
	String clubName,
	String clubDescription,
	String clubImage,
	boolean isClubDeleted,
	List<ClubMember> clubMembers,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ClubUpdateInfo toClubUpdateInfo(Club club) {
		return new ClubUpdateInfo(
			club.getClubId(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.isClubDeleted(),
			club.getClubMembers(),
			club.getCreatedAt(),
			club.getModifiedAt());
	}
}
