package org.badminton.infrastructure.match.strategy;

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
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.service.AbstractSinglesMatchStrategy;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.infrastructure.match.service.TournamentSinglesBracketCreator;
import org.badminton.infrastructure.match.service.TournamentSinglesEndSetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TournamentSinglesMatchStrategy extends AbstractSinglesMatchStrategy {

	private final SinglesMatchReader singlesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final TournamentSinglesBracketCreator tournamentSinglesBracketCreator;
	private final TournamentSinglesEndSetHandler tournamentSinglesEndSetHandler;

	public TournamentSinglesMatchStrategy(SinglesMatchReader singlesMatchReader, SinglesMatchStore singlesMatchStore,
		TournamentSinglesBracketCreator tournamentSinglesBracketCreator,
		TournamentSinglesEndSetHandler tournamentSinglesEndSetHandler) {
		super(singlesMatchReader, singlesMatchStore);
		this.singlesMatchReader = singlesMatchReader;
		this.singlesMatchStore = singlesMatchStore;
		this.tournamentSinglesBracketCreator = tournamentSinglesBracketCreator;
		this.tournamentSinglesEndSetHandler = tournamentSinglesEndSetHandler;
	}

	// 대진표 생성
	@Override
	public BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList) {

		// 전체 매치 배열 생성
		List<SinglesMatch> allMatches = new ArrayList<>();

		Collections.shuffle(leagueParticipantList);

		// 전체 라운드의 수 계산
		// leagueEntity 에 전체 라운드 수 저장
		int totalRounds = tournamentSinglesBracketCreator.getTotalRounds(league, leagueParticipantList);

		// 전체 매치 배열에 첫번재 라운드 생성 후 넣기
		// 전체 매치 배열에 첫번째 라운드 제외 모든 라운드 매치 생성 후 넣기
		// 전체 매치 스트림 -> 부전승이고, leagueParticipant1 이 null 이 아닌 매치들 대상으로 -> leagueParticipant1 다음 라운드의 매치로 이동
		tournamentSinglesBracketCreator.generateAllMatches(league, allMatches, leagueParticipantList, totalRounds);

		return BracketInfo.fromSingles(totalRounds, allMatches);
	}

	// 세트 종료 및 점수 저장
	@Override
	@Transactional
	public SetInfo.Main endSet(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {

		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);

		// endSet 해도 되는지 검증
		tournamentSinglesEndSetHandler.isEndSetAllowed(singlesMatch, setNumber, matchId);

		// 세트 종료 로직
		tournamentSinglesEndSetHandler.processEndSet(singlesMatch, setNumber, updateSetScoreCommand);

		// 변경한 부분 singlesMatchStore 로 저장
		singlesMatchStore.store(singlesMatch);

		return SetInfo.fromSinglesSet(matchId, setNumber, singlesMatch.getSinglesSets().get(setNumber - 1));
	}

	// 세트 조회
	@Override
	public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(1);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesSet);
	}

}
