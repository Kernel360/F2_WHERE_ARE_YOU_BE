package org.badminton.infrastructure.match.repository;

import static org.badminton.domain.domain.match.entity.QSinglesMatch.*;

import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.QLeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.reader.SinglesMatchRepositoryCustom;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SinglesMatchRepositoryImpl implements SinglesMatchRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SinglesMatch> findAllCompletedByClubMemberId(Long clubMemberId) {
		QLeagueParticipant leagueParticipant1 = new QLeagueParticipant("leagueParticipant1");
		QLeagueParticipant leagueParticipant2 = new QLeagueParticipant("leagueParticipant2");

		return queryFactory
			.selectFrom(singlesMatch)
			.join(leagueParticipant1)
			.on(singlesMatch.leagueParticipant1.leagueParticipantId.eq(leagueParticipant1.leagueParticipantId))
			.join(leagueParticipant2)
			.on(singlesMatch.leagueParticipant2.leagueParticipantId.eq(leagueParticipant2.leagueParticipantId))
			.where(
				singlesMatch.matchStatus.eq(MatchStatus.FINISHED)
					.and(
						isClubMemberInParticipant(leagueParticipant1, clubMemberId)
							.or(isClubMemberInParticipant(leagueParticipant2, clubMemberId))
					)
			)
			.fetch();
	}

	private BooleanExpression isClubMemberInParticipant(QLeagueParticipant participant, Long clubMemberId) {
		return participant.clubMember != null ? participant.clubMember.clubMemberId.eq(clubMemberId) :
			Expressions.FALSE;
	}
}
