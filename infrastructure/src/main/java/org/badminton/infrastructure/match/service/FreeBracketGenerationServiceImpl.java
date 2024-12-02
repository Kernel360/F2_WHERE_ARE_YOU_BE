package org.badminton.infrastructure.match.service;

import java.util.List;

import org.badminton.domain.common.exception.league.InvalidPlayerCountException;
import org.badminton.domain.common.exception.league.NotLeagueOwnerException;
import org.badminton.domain.common.exception.match.InvalidLeagueStatusToGenerateBracketException;
import org.badminton.domain.common.exception.match.LeagueRecruitingMustBeCompletedWhenBracketGenerationException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.LeagueStore;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.BracketGenerationService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.infrastructure.league.LeagueParticipantRepository;
import org.badminton.infrastructure.match.strategy.FreeDoublesMatchStrategy;
import org.badminton.infrastructure.match.strategy.FreeSinglesMatchStrategy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBracketGenerationServiceImpl implements BracketGenerationService {

	private final LeagueReader leagueReader;
	private final LeagueStore leagueStore;
	private final LeagueParticipantRepository leagueParticipantRepository;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final DoublesMatchStore doublesMatchStore;

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
			case SINGLES -> new FreeSinglesMatchStrategy(singlesMatchReader, singlesMatchStore, leagueReader);
			case DOUBLES -> new FreeDoublesMatchStrategy(doublesMatchReader, doublesMatchStore, leagueReader);
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
			leagueParticipantRepository.findAllByLeagueLeagueIdAndCanceledFalse(leagueId);
		if (leagueParticipantList.isEmpty()) {
			throw new InvalidPlayerCountException(leagueId, 0);
		}
		return leagueParticipantList;
	}
}
