package org.badminton.infrastructure.match.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.exception.league.InvalidPlayerCountException;
import org.badminton.domain.common.exception.league.NotLeagueOwnerException;
import org.badminton.domain.common.exception.match.LeagueRecruitingMustBeCompletedWhenBracketGenerationException;
import org.badminton.domain.domain.league.LeagueReader;
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

@Service
@RequiredArgsConstructor
public class FreeBracketGenerationServiceImpl implements BracketGenerationService {

    private final LeagueReader leagueReader;
    private final LeagueParticipantRepository leagueParticipantRepository;
    private final SinglesMatchReader singlesMatchReader;
    private final DoublesMatchReader doublesMatchReader;
    private final SinglesMatchStore singlesMatchStore;
    private final DoublesMatchStore doublesMatchStore;

    @Override
    public void checkLeagueRecruitingStatus(Long leagueId) {
        League league = findLeague(leagueId);
        /*
         * 경기 상태가 COMPLETED 일 수 있는 상황
         * 1. 모집 마감 날짜가 지났고, 모집 인원이 채워짐
         * 2. 모집 마감 날짜가 지나지 않았지만, 경기 소유자가 모집 마감 상태로 변경(이때 최소 인원은 충족된다.)
         * 3. 모집 마감 날짜가 지나지 않았지만, 모집 인원이 채워짐
         */
        if (league.getLeagueStatus() != LeagueStatus.RECRUITING_COMPLETED) {
            throw new LeagueRecruitingMustBeCompletedWhenBracketGenerationException(leagueId, league.getLeagueStatus());
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
    public void startMatch(MatchStrategy matchStrategy, Long matchId) {
        matchStrategy.startMatch(matchId);
    }

    private League findLeague(Long leagueId) {
        return leagueReader.readLeagueById(leagueId);
    }

    private List<LeagueParticipant> findLeagueParticipantList(Long leagueId) {
        /*
         * LeagueStatus 가 COMPLETED 인데 League Participant 숫자가 0일 가능성은 없다.
         */
        List<LeagueParticipant> leagueParticipantList =
                leagueParticipantRepository.findAllByLeagueLeagueIdAndCanceledFalse(leagueId);
        if (leagueParticipantList.isEmpty()) {
            throw new InvalidPlayerCountException(leagueId, 0);
        }
        return leagueParticipantList;
    }
}
