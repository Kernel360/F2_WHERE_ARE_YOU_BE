package org.badminton.api.application.match;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.match.service.MatchResultService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyMatchFacade {
    private final MatchResultService matchResultService;

    public List<MatchResultInfo> getMyMatch(Long clubMemberId) {
        return matchResultService.getAllMatchResultsByClubMember(clubMemberId);
    }
}
