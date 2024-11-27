package org.badminton.domain.domain.league;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.badminton.domain.common.exception.league.InvalidDateTimeToParticipateInLeagueException;
import org.badminton.domain.common.exception.league.LeagueNotRecruitingException;
import org.badminton.domain.common.exception.league.LeagueOwnerCannotCancelLeagueParticipationException;
import org.badminton.domain.common.exception.league.LeagueParticipationCannotBeCanceledException;
import org.badminton.domain.common.exception.league.LeagueParticipationDuplicateException;
import org.badminton.domain.common.exception.league.ParticipationLimitReachedException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantDetailsInfo;
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
		validateLeagueOwner(league, memberToken);
		validateCancelAvailableTime(league);
		validateCancelAvailableLeagueStatus(league);
		ClubMember clubMember = clubMemberReader.getClubMember(clubToken, memberToken);
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
		League league = leagueReader.readLeagueById(leagueId);
		validateLeagueRecruiting(league);
		validateDuplicateLeagueParticipation(memberToken, leagueId);
		checkParticipantCount(league);
		ClubMember clubMember = clubMemberReader.getClubMember(clubToken, memberToken);
		return LeagueParticipantInfo.from(leagueParticipantStore.store(clubMember, league));
	}

	@Override
	public List<LeagueParticipantDetailsInfo> getLeagueParticipants(Long leagueId) {
		List<LeagueParticipant> leagueParticipants = leagueParticipantReader.findAllByLeagueIdAndCanceledFalse(
			leagueId);
		if (leagueParticipants == null) {
			return null;
		}
		return leagueParticipants.stream()
			.map(LeagueParticipantDetailsInfo::from)
			.toList();
	}

	private void validateCancelAvailableLeagueStatus(League league) {
		if (league.getLeagueStatus() == LeagueStatus.PLAYING || league.getLeagueStatus() == LeagueStatus.CANCELED
			|| league.getLeagueStatus() == LeagueStatus.FINISHED) {
			throw new LeagueParticipationCannotBeCanceledException(league.getLeagueId(), league.getLeagueStatus());
		}
	}

	private void validateCancelAvailableTime(League league) {
		if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
			throw new LeagueParticipationCannotBeCanceledException(league.getLeagueId(),
				league.getRecruitingClosedAt());
		}
	}

	private void validateLeagueOwner(League league, String memberToken) {
		if (Objects.equals(league.getLeagueOwnerMemberToken(), memberToken)) {
			throw new LeagueOwnerCannotCancelLeagueParticipationException(memberToken, league.getLeagueId());
		}
	}

	private void checkParticipantCount(League league) {
		if (league.getPlayerLimitCount() <= leagueParticipantReader.countParticipantMember(league.getLeagueId())) {
			throw new ParticipationLimitReachedException(league.getLeagueId());
		}
		if (league.getPlayerLimitCount() == leagueParticipantReader.countParticipantMember(league.getLeagueId()) + 1) {
			league.completeLeagueRecruiting();
		}
	}

	private void validateDuplicateLeagueParticipation(String memberToken, Long leagueId) {
		if (leagueParticipantReader.isParticipant(memberToken, leagueId)) {
			throw new LeagueParticipationDuplicateException(leagueId, memberToken);
		}
	}

	private void validateLeagueRecruiting(League league) {
		if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
			throw new InvalidDateTimeToParticipateInLeagueException(league.getLeagueId(),
				league.getRecruitingClosedAt());
		}

		if (league.getLeagueStatus() != LeagueStatus.RECRUITING) {
			throw new LeagueNotRecruitingException(league.getLeagueId(), league.getLeagueStatus());
		}
	}

}
