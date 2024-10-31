package org.badminton.domain.domain.clubmember.info;

import java.util.List;
import java.util.stream.Collectors;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberMyPageInfo(
	Long clubId,

	Long clubMemberId,

	String clubName,

	ClubMember.ClubMemberRole role

) {
	public static ClubMemberMyPageInfo from(ClubMember clubMember) {
		if (clubMember == null) {
			return null;
		}
		return new ClubMemberMyPageInfo(clubMember.getClub().getClubId(), clubMember.getClubMemberId(),
			clubMember.getClub().getClubName(), clubMember.getRole());
	}

	public static List<ClubMemberMyPageInfo> from(List<ClubMember> clubMembers) {
		return clubMembers.stream()
			.map(ClubMemberMyPageInfo::from)
			.collect(Collectors.toList());
	}

}
