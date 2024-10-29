package org.badminton.infrastructure.league;

import java.util.Optional;

import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRecordRepository extends JpaRepository<LeagueRecord, Long> {
	Optional<LeagueRecord> findByMember(Member member);

	Optional<LeagueRecord> findByMemberMemberToken(String memberToken);

}