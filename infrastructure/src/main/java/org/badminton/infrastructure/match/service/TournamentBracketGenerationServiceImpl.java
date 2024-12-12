package org.badminton.infrastructure.match.service;

import java.util.List;

import org.badminton.domain.common.exception.league.InvalidLeagueParticipantCountException;
import org.badminton.domain.common.exception.league.NotLeagueOwnerException;
import org.badminton.domain.common.exception.match.InvalidLeagueStatusToGenerateBracketException;
import org.badminton.domain.common.exception.match.LeagueRecruitingMustBeCompletedWhenBracketGenerationException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.LeagueStore;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.service.BracketGenerationService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.infrastructure.match.strategy.TournamentDoublesMatchStrategy;
import org.badminton.infrastructure.match.strategy.TournamentSinglesMatchStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentBracketGenerationServiceImpl implements BracketGenerationService {

	private final LeagueReader leagueReader;
	private final LeagueStore leagueStore;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final DoublesMatchStore doublesMatchStore;
	private final LeagueParticipantReader leagueParticipantReader;
	private final TournamentSinglesEndSetHandler tournamentSinglesEndSetHandler;
	private final TournamentSinglesBracketCreator tournamentSinglesBracketCreator;
	private final TournamentDoublesEndSetHandler tournamentDoublesEndSetHandler;
	private final TournamentDoublesBracketCreator tournamentDoublesBracketCreator;

	@Override
	public void checkLeagueRecruitingStatus(Long leagueId) {
		League league = findLeague(leagueId);
		if (league.getLeagueStatus() == LeagueStatus.RECRUITING) {
			throw new LeagueRecruitingMustBeCompletedWhenBracketGenerationException(leagueId, league.getLeagueStatus());
		}
		if (league.getLeagueStatus() == LeagueStatus.FINISHED || league.getLeagueStatus() == LeagueStatus.CANCELED) {
			throw new InvalidLeagueStatusToGenerateBracketException(leagueId, league.getLeagueStatus());
		}
	}

	@Override
	public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
		League league = findLeague(leagueId);
		return switch (league.getMatchType()) {
			case SINGLES -> new TournamentSinglesMatchStrategy(singlesMatchReader, singlesMatchStore,
				tournamentSinglesBracketCreator, tournamentSinglesEndSetHandler);
			case DOUBLES -> new TournamentDoublesMatchStrategy(doublesMatchReader, doublesMatchStore,
				tournamentDoublesEndSetHandler, tournamentDoublesBracketCreator);
		};
	}

	@Override
	@Transactional
	public BracketInfo makeBracket(MatchStrategy matchStrategy, Long leagueId, String memberToken) {
		League league = findLeague(leagueId);
		if (!league.getLeagueOwnerMemberToken().equals(memberToken)) {
			throw new NotLeagueOwnerException(leagueId, memberToken);
		}
		matchStrategy.checkDuplicateInitialBracket(leagueId);
		List<LeagueParticipant> leagueParticipantList = findLeagueParticipantList(leagueId);

		return matchStrategy.makeBracket(findLeague(leagueId), leagueParticipantList);
	}

	@Override
	public void startMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId) {
		League league = findLeague(leagueId);
		league.startLeague();
		leagueStore.store(league);
		matchStrategy.startMatch(matchId);
	}

	private League findLeague(Long leagueId) {
		return leagueReader.readLeagueById(leagueId);
	}

	private List<LeagueParticipant> findLeagueParticipantList(Long leagueId) {

		List<LeagueParticipant> leagueParticipantList =
			leagueParticipantReader.findAllByLeagueIdAndCanceledFalse(leagueId);
		if (leagueParticipantList.isEmpty()) {
			throw new InvalidLeagueParticipantCountException(leagueId, 0);
		}
		return leagueParticipantList;
	}
}
