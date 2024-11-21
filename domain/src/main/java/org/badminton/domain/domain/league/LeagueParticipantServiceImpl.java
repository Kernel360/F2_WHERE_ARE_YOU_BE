package org.badminton.domain.domain.league;

import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.exception.league.LeagueCannotBeCanceledWhenIsNotRecruiting;
import org.badminton.domain.common.exception.league.LeagueOwnerCannotCancelLeagueParticipationException;
import org.badminton.domain.common.exception.league.LeagueParticipationDuplicateException;
import org.badminton.domain.common.exception.league.ParticipationLimitReachedException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueParticipantServiceImpl implements LeagueParticipantService {
    private final LeagueParticipantReader leagueParticipantReader;
    private final LeagueParticipantStore leagueParticipantStore;
    private final LeagueReader leagueReader;
    private final ClubMemberReader clubMemberReader;

    @Override
    @Transactional
    public boolean isParticipant(String memberToken, Long leagueId) {
        return leagueParticipantReader.isParticipant(memberToken, leagueId);
    }

    @Override
    @Transactional
    public int countParticipantMember(Long leagueId) {
        return leagueParticipantReader.countParticipantMember(leagueId);
    }

    @Override
    public LeagueParticipantCancelInfo participantLeagueCancel(String memberToken, String clubToken, Long leagueId) {
        League league = leagueReader.readLeagueById(leagueId);
        if (Objects.equals(league.getLeagueOwnerMemberToken(), memberToken)) {
            throw new LeagueOwnerCannotCancelLeagueParticipationException(memberToken, leagueId);
        }
        if (league.getLeagueStatus() != LeagueStatus.RECRUITING) {
            throw new LeagueCannotBeCanceledWhenIsNotRecruiting(leagueId, league.getLeagueStatus());
        }
        ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
        Long clubMemberId = clubMember.getClubMemberId();
        LeagueParticipant leagueParticipant = leagueParticipantReader.findParticipant(leagueId, clubMemberId);
        var result = leagueParticipantStore.cancelStore(leagueParticipant);
        return LeagueParticipantCancelInfo.from(result);
    }

    @Override
    public LeagueParticipantInfo participantInLeague(String memberToken, String clubToken, Long leagueId) {
        ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
        League league = leagueReader.readLeagueById(leagueId);
        if (leagueParticipantReader.isParticipant(memberToken, leagueId)) {
            throw new LeagueParticipationDuplicateException(leagueId, memberToken);
        }
        checkParticipantCount(league);
        return LeagueParticipantInfo.from(leagueParticipantStore.store(clubMember, league));
    }

    private void checkParticipantCount(League league) {
        if (league.getPlayerLimitCount() <= leagueParticipantReader.countParticipantMember(league.getLeagueId())) {
            throw new ParticipationLimitReachedException(league.getLeagueId());
        }
        if (league.getPlayerLimitCount() == leagueParticipantReader.countParticipantMember(league.getLeagueId()) - 1) {
            league.completeLeagueRecruiting();
        }
    }
}
