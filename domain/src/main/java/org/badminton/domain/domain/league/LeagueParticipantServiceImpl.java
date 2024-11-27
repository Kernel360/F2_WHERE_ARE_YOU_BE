package org.badminton.domain.domain.league;

import java.time.LocalDateTime;
import java.util.Objects;

import org.badminton.domain.common.exception.league.LeagueCannotBeCanceledException;
import org.badminton.domain.common.exception.league.LeagueOwnerCannotCancelLeagueParticipationException;
import org.badminton.domain.common.exception.league.LeagueParticipationDuplicateException;
import org.badminton.domain.common.exception.league.ParticipationLimitReachedException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueParticipantServiceImpl implements LeagueParticipantService {

	private final LeagueParticipantReader leagueParticipantReader;
	private final LeagueParticipantStore leagueParticipantStore;
	private final LeagueReader leagueReader;
	private final LeagueStore leagueStore;
	private final ClubMemberReader clubMemberReader;

	@Override
	@Transactional
	public IsLeagueParticipantInfo isParticipant(String memberToken, Long leagueId) {
		boolean isParticipant = leagueParticipantReader.isParticipant(memberToken, leagueId);
		return IsLeagueParticipantInfo.from(isParticipant);
	}

	@Override
	@Transactional
	public int countParticipantMember(Long leagueId) {
		return leagueParticipantReader.countParticipantMember(leagueId);
	}

	@Override
	public LeagueParticipantCancelInfo cancelLeagueParticipation(String memberToken, String clubToken, Long leagueId) {
		League league = leagueReader.readLeagueById(leagueId);
		if (Objects.equals(league.getLeagueOwnerMemberToken(), memberToken)) {
			throw new LeagueOwnerCannotCancelLeagueParticipationException(memberToken, leagueId);
		}
		if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
			throw new LeagueCannotBeCanceledException(leagueId, league.getRecruitingClosedAt());
		}
		if (league.getLeagueStatus() == LeagueStatus.PLAYING || league.getLeagueStatus() == LeagueStatus.CANCELED
			|| league.getLeagueStatus() == LeagueStatus.FINISHED) {
			throw new LeagueCannotBeCanceledException(leagueId, league.getLeagueStatus());
		}
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		LeagueParticipant leagueParticipant = leagueParticipantReader.findParticipant(leagueId,
			clubMember.getClubMemberId());
		var result = leagueParticipantStore.cancelStore(leagueParticipant);
		if (league.getLeagueStatus() == LeagueStatus.RECRUITING_COMPLETED) {
			league.reopenLeagueRecruiting();
		}
		leagueStore.store(league);
		return LeagueParticipantCancelInfo.from(result);
	}

	@Override
	@Transactional
	public LeagueParticipantInfo participantInLeague(String memberToken, String clubToken, Long leagueId) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		League league = leagueReader.readLeagueById(leagueId);
		if (leagueParticipantReader.isParticipant(memberToken, leagueId)) {
			throw new LeagueParticipationDuplicateException(leagueId, memberToken);
		}
		checkParticipantCount(league);
		return LeagueParticipantInfo.from(leagueParticipantStore.store(clubMember, league));
	}

	private void checkParticipantCount(League league) {
		if (league.getPlayerLimitCount() <= leagueParticipantReader.countParticipantMember(league.getLeagueId())) {
			throw new ParticipationLimitReachedException(league.getLeagueId());
		}
		if (league.getPlayerLimitCount() == leagueParticipantReader.countParticipantMember(league.getLeagueId()) + 1) {
			league.completeLeagueRecruiting();
		}
	}
}
