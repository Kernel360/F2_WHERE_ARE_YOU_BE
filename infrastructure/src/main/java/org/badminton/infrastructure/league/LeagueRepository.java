package org.badminton.infrastructure.league;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findByClubClubTokenAndLeagueId(String clubToken, Long leagueId);

    Optional<League> findByLeagueIdAndLeagueStatusNotContaining(Long leagueId, LeagueStatus leagueStatus);

    List<League> findAllByClubClubTokenAndLeagueAtBetween(String clubToken, LocalDateTime startOfMonth,
                                                          LocalDateTime endOfMonth);

    Integer countByClubClubIdAndLeagueStatus(Long clubId, LeagueStatus leagueStatus);

    @Query("SELECT l.matchGenerationType FROM League l WHERE l.leagueId = :leagueId")
    Optional<MatchGenerationType> getMatchGenerationTypeByLeagueId(@Param("leagueId") Long leagueId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM League l WHERE l.club.clubId = :clubId AND DATE(l.createdAt) = CURRENT_DATE")
    boolean existsByClubIdAndCreatedAtToday(@Param("clubId") Long clubId);

    List<League> findByLeagueStatus(LeagueStatus leagueStatus);

    Page<League> findAllByLeagueAtBetweenAndLeagueStatusAndAddressRegion(
            LocalDateTime leagueAt,
            LocalDateTime endOfDay,
            LeagueStatus leagueStatus,
            String addressRegion,
            Pageable pageable
    );

    Page<League> findAllByLeagueAtBetweenAndAddressRegion(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay,
            String nameByCode,
            Pageable pageable
    );

    Page<League> findAllByLeagueAtBetweenAndLeagueStatus(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay,
            LeagueStatus status,
            Pageable pageable
    );

    Page<League> findAllByLeagueAtBetween(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay,
            Pageable pageable
    );
}
