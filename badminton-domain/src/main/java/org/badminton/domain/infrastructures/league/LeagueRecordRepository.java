package org.badminton.domain.infrastructures.league;

import java.util.Optional;

import org.badminton.domain.domain.clubmember.entity.ClubMemberEntity;
import org.badminton.domain.domain.league.entity.LeagueRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LeagueRecordRepository extends JpaRepository<LeagueRecordEntity, Long> {
    Optional<LeagueRecordEntity> findByClubMember(ClubMemberEntity clubMember);

    Optional<LeagueRecordEntity> findByClubMember_ClubMemberId(Long clubMemberId);

}