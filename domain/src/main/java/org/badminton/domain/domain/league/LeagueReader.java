package org.badminton.domain.domain.league;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.Region;

public interface LeagueReader {
    League readLeague(String clubToken, Long leagueId);

    League readLeagueById(Long leagueId);

    List<League> readLeagueByMonth(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<League> readLeagueByDate(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<League> readOngoingAndUpcomingLeagueByDate(AllowedLeagueStatus leagueStatus, Region region, LocalDate date);

    Integer getCountByClubId(Long clubId);
}
