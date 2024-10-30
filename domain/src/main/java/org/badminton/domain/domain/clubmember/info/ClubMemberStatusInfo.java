package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ClubMemberStatusInfo {

	private Long clubMemberId;
	private String role;
	private String status;

	public static ClubMemberStatusInfo from(ClubMember clubMember) {
		return new ClubMemberStatusInfo(
			clubMember.getClubMemberId(),
			clubMember.getRole().getDescription(),
			clubMember.getStatus().getDescription());
	}
}
