package org.badminton.domain.domain.league;

import java.util.List;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

public interface LeagueParticipantReader {
	boolean isParticipant(String memberToken, Long leagueId);

	int countParticipantMember(Long leagueId);

	LeagueParticipant findParticipant(Long leagueId, Long clubMemberId);

	List<LeagueParticipant> findAllByLeagueIdAndCanceledFalse(Long leagueId);

	List<LeagueParticipant> findAllByClubMemberIdAndCanceledFalse(Long clubMemberId);
}
