package org.badminton.domain.common.policy;

import org.badminton.domain.common.exception.clubmember.ClubMemberIsNotAboveManagerException;
import org.badminton.domain.common.exception.clubmember.ClubMemberIsNotOwnerException;
import org.badminton.domain.common.exception.league.LeagueParticipationNotExistException;
import org.badminton.domain.common.exception.member.MemberNotJoinedClubException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClubMemberPolicy {

	private final ClubMemberReader clubMemberReader;
	private final LeagueParticipantReader leagueParticipantReader;

	public void validateClubMember(String memberToken, String clubToken) {
		if (!clubMemberReader.checkIsClubMember(memberToken, clubToken)) {
			throw new MemberNotJoinedClubException(memberToken, clubToken);
		}
	}

	public void validateClubOwner(String memberToken, String clubToken) {
		validateClubMember(memberToken, clubToken);
		ClubMember clubMember = clubMemberReader.getClubMember(clubToken, memberToken);
		if (!clubMember.getRole().equals(ClubMember.ClubMemberRole.ROLE_OWNER)) {
			throw new ClubMemberIsNotOwnerException(memberToken, clubToken, clubMember.getRole());
		}
	}

	public void validateAboveClubManager(String memberToken, String clubToken) {
		validateClubMember(memberToken, clubToken);
		ClubMember clubMember = clubMemberReader.getClubMember(clubToken, memberToken);
		if (clubMember.getRole().equals(ClubMember.ClubMemberRole.ROLE_USER)) {
			throw new ClubMemberIsNotAboveManagerException(memberToken, clubToken, clubMember.getRole());
		}
	}

	public void validateLeagueParticipant(Long leagueId, String memberToken) {
		if (!leagueParticipantReader.isParticipant(memberToken, leagueId)) {
			throw new LeagueParticipationNotExistException(leagueId, memberToken);
		}
	}
}
