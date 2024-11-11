package org.badminton.infrastructure.match.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.AbstractSinglesMatchStrategy;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FreeSinglesMatchStrategy extends AbstractSinglesMatchStrategy {

	private final SinglesMatchReader singlesMatchReader;
	private final SinglesMatchStore singlesMatchStore;

	public FreeSinglesMatchStrategy(SinglesMatchReader singlesMatchReader, SinglesMatchStore singlesMatchStore) {
		super(singlesMatchReader);
		this.singlesMatchReader = singlesMatchReader;
		this.singlesMatchStore = singlesMatchStore;
	}

	@Override
	public BracketInfo makeBracket(League league,
		List<LeagueParticipant> leagueParticipantList) {
		Collections.shuffle(leagueParticipantList);
		List<SinglesMatch> singlesMatches = makeSinglesMatches(leagueParticipantList, league, 1);
		singlesMatches.forEach(this::makeSetsInMatch);
		return BracketInfo.fromSingles(1, singlesMatches);
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		// 세트 스코어를 기록한다.
		singlesMatch.getSinglesSets()
			.get(setIndex - 1)
			.saveSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		// 승자에 따라 Match에 이긴 세트수를 업데이트해준다. 만약 2번을 모두 이긴 참가자가 있다면 해당 Match는 종료된다.
		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			singlesMatch.player1WinSet();
		} else {
			singlesMatch.player2WinSet();
		}

		singlesMatchStore.store(singlesMatch);
		return SetInfo.fromSinglesSet(matchId, setIndex, singlesMatch.getSinglesSets().get(setIndex - 1));
	}

	@Override
	public void checkDuplicateInitialBracket(LocalDateTime leagueAt, Long leagueId) {
		boolean isBracketEmpty = singlesMatchReader.checkIfBracketEmpty(leagueId);
		if (!isBracketEmpty && LocalDateTime.now().isBefore(leagueAt)) {
			singlesMatchStore.deleteSinglesBracket(leagueId);
		} else if (!isBracketEmpty && LocalDateTime.now().isAfter(leagueAt)) {
			// TODO: 경기 시작 시간이 지났는데, 이미 대진표가 있는데 또 생성하고 싶다면? -> 막을지, 안막을지
		}
	}

	// TODO: 리팩토링
	private void makeSetsInMatch(SinglesMatch singlesMatch) {
		//단식 게임 세트를 3개 생성
		SinglesSet set1 = new SinglesSet(singlesMatch, 1);
		SinglesSet set2 = new SinglesSet(singlesMatch, 2);
		SinglesSet set3 = new SinglesSet(singlesMatch, 3);

		singlesMatch.addSet(set1);
		singlesMatch.addSet(set2);
		singlesMatch.addSet(set3);

		singlesMatchStore.store(singlesMatch);
	}

	private List<SinglesMatch> makeSinglesMatches(List<LeagueParticipant> leagueParticipantList,
		League league, int roundNumber) {

		List<SinglesMatch> singlesMatches = new ArrayList<>();
		for (int i = 0; i < leagueParticipantList.size() - 1; i += 2) {
			SinglesMatch singlesMatch = new SinglesMatch(league, leagueParticipantList.get(i),
				leagueParticipantList.get(i + 1), roundNumber);
			singlesMatches.add(singlesMatch);
			singlesMatchStore.store(singlesMatch);
		}
		return singlesMatches;
	}
}

