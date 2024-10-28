package org.badminton.api.service.match;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.infrastructures.match.repository.DoublesMatchRepository;
import org.badminton.domain.infrastructures.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchResultService {

    private final SinglesMatchRepository singlesMatchRepository;
    private final DoublesMatchRepository doublesMatchRepository;

    public List<MatchResultResponse> getAllMatchResultsByClubMember(Long clubMemberId) {
        List<MatchResultResponse> allResults = new ArrayList<>();

        // 단식 경기 결과 가져오기
        List<SinglesMatch> singlesMatches = singlesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
        for (SinglesMatch match : singlesMatches) {
            allResults.add(MatchResultResponse.fromSinglesMatch(match, clubMemberId));
        }

        // 복식 경기 결과 가져오기
        List<DoublesMatch> doublesMatches = doublesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
        for (DoublesMatch match : doublesMatches) {
            allResults.add(MatchResultResponse.fromDoublesMatch(match, clubMemberId));
        }

        allResults.sort((r1, r2) -> r2.leagueAt().compareTo(r1.leagueAt()));
        return allResults;
    }
}
