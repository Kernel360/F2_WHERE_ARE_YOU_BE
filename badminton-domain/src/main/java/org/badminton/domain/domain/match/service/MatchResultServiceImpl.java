package org.badminton.domain.domain.match.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.infrastructures.match.repository.DoublesMatchRepository;
import org.badminton.domain.infrastructures.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService {

    private final SinglesMatchRepository singlesMatchRepository;
    private final DoublesMatchRepository doublesMatchRepository;

    public List<MatchResultInfo> getAllMatchResultsByClubMember(Long clubMemberId) {
        List<MatchResultInfo> matchResultInfoList = new ArrayList<>();

        // 단식 경기 결과 가져오기
        List<SinglesMatch> singlesMatches = singlesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
        for (SinglesMatch match : singlesMatches) {
            matchResultInfoList.add(MatchResultInfo.fromSinglesMatch(match, clubMemberId));
        }

        // 복식 경기 결과 가져오기
        List<DoublesMatch> doublesMatches = doublesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
        for (DoublesMatch match : doublesMatches) {
            matchResultInfoList.add(MatchResultInfo.fromDoublesMatch(match, clubMemberId));
        }

        matchResultInfoList.sort((r1, r2) -> r2.leagueAt().compareTo(r1.leagueAt()));
        return matchResultInfoList;
    }
}
