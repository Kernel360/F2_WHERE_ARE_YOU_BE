package org.badminton.domain.domain.match.service;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService {

	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;

	public List<MatchResultInfo> getAllMatchResultsByClubMember(Long clubMemberId) {
		List<MatchResultInfo> matchResultInfoList = new ArrayList<>();

		// 단식 경기 결과 가져오기
		List<SinglesMatch> singlesMatches = singlesMatchReader.getSinglesMatchesByClubMember(clubMemberId);
		for (SinglesMatch match : singlesMatches) {
			matchResultInfoList.add(MatchResultInfo.fromSinglesMatch(match, clubMemberId));
		}

		// 복식 경기 결과 가져오기
		List<DoublesMatch> doublesMatches = doublesMatchReader.findAllCompletedByClubMemberId(clubMemberId);
		for (DoublesMatch match : doublesMatches) {
			matchResultInfoList.add(MatchResultInfo.fromDoublesMatch(match, clubMemberId));
		}

		matchResultInfoList.sort((r1, r2) -> r2.leagueAt().compareTo(r1.leagueAt()));
		return matchResultInfoList;
	}
}
