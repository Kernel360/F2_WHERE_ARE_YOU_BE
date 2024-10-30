package org.badminton.api.interfaces.clubmember.dto;

public record ClubMemberStatusResponse(
	Long clubMemberId,
	String role,
	String status
) {
}
