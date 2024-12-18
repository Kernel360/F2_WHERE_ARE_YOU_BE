package org.badminton.infrastructure.statistics;

import static org.badminton.domain.domain.statistics.QClubStatistics.*;

import java.util.List;

import org.badminton.domain.domain.club.entity.QClub;
import org.badminton.domain.domain.statistics.ClubStatisticsRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClubStatisticsRepositoryImpl implements ClubStatisticsRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Long> findAllClubId() {
		return queryFactory
			.select(clubStatistics.club.clubId)
			.from(clubStatistics)
			.where(isClubNotNull(clubStatistics.club))
			.fetch();
	}

	@Override
	@Transactional
	public void increaseClubVisitCount(String clubToken) {
		queryFactory.update(clubStatistics)
			.set(clubStatistics.visitedCount, clubStatistics.visitedCount.add(1))
			.where(clubStatistics.club.clubToken.eq(clubToken))
			.execute();
	}

	private BooleanExpression isClubNotNull(QClub club) {
		if (club == null) {
			return Expressions.FALSE;
		}
		return club.isNotNull();
	}
}
