package org.badminton.infrastructure.league;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findByClubClubTokenAndLeagueId(String clubToken, Long leagueId);

    List<League> findAllByClubClubTokenAndLeagueAtBetween(String clubToken, LocalDateTime startOfMonth,
                                                          LocalDateTime endOfMonth);

    void deleteByLeagueId(Long leagueId);

    List<League> findAllByLeagueAtBetweenAndLeagueStatusNotIn(LocalDateTime startOfDay, LocalDateTime endOfDay,
                                                              List<LeagueStatus> excludedLeagueStatusList);
}
