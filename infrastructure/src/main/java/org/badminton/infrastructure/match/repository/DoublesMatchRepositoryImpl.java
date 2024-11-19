package org.badminton.infrastructure.match.repository;

import static org.badminton.domain.domain.match.entity.QDoublesMatch.doublesMatch;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.QLeagueParticipant;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DoublesMatchRepositoryImpl implements DoublesMatchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DoublesMatch> findAllCompletedByClubMemberId(Long clubMemberId) {
        QLeagueParticipant leagueParticipant1 = new QLeagueParticipant("leagueParticipant1");
        QLeagueParticipant leagueParticipant2 = new QLeagueParticipant("leagueParticipant2");
        QLeagueParticipant leagueParticipant3 = new QLeagueParticipant("leagueParticipant3");
        QLeagueParticipant leagueParticipant4 = new QLeagueParticipant("leagueParticipant4");

        return queryFactory
                .selectFrom(doublesMatch)
                .join(leagueParticipant1)
                .on(doublesMatch.team1.leagueParticipant1.leagueParticipantId.eq(
                        leagueParticipant1.leagueParticipantId))
                .join(leagueParticipant2)
                .on(doublesMatch.team1.leagueParticipant2.leagueParticipantId.eq(
                        leagueParticipant2.leagueParticipantId))
                .join(leagueParticipant3)
                .on(doublesMatch.team2.leagueParticipant1.leagueParticipantId.eq(
                        leagueParticipant3.leagueParticipantId))
                .join(leagueParticipant4)
                .on(doublesMatch.team2.leagueParticipant2.leagueParticipantId.eq(
                        leagueParticipant4.leagueParticipantId))
                .where(
                        doublesMatch.matchStatus.eq(MatchStatus.FINISHED)
                                .and(
                                        isClubMemberInParticipant(leagueParticipant1, clubMemberId)
                                                .or(isClubMemberInParticipant(leagueParticipant2, clubMemberId))
                                                .or(isClubMemberInParticipant(leagueParticipant3, clubMemberId))
                                                .or(isClubMemberInParticipant(leagueParticipant4, clubMemberId))
                                )
                )
                .fetch();
    }

    private BooleanExpression isClubMemberInParticipant(QLeagueParticipant participant, Long clubMemberId) {
        return participant.clubMember != null ? participant.clubMember.clubMemberId.eq(clubMemberId) :
                Expressions.FALSE;
    }

}
