package org.badminton.domain.domain.league.vo;

import java.time.LocalDate;

import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.enums.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueSearchStrategy {
	private final LeagueReader leagueReader;

	public Page<League> getStrategy(
		AllowedLeagueStatus leagueStatus,
		Region region,
		LocalDate date,
		Pageable pageable
	) {
		if (leagueStatus.name().equals(LeagueStatus.ALL.name())) {
			return region.name().equals(Region.ALL.name())
				? leagueReader.readLeagueStatusIsAllAndRegionIsAll(date, pageable)
				: leagueReader.readLeagueStatusIsAllAndRegionIsNotAll(region, date, pageable);
		} else {
			return region.name().equals(Region.ALL.name())
				? leagueReader.readLeagueStatusIsNotAllAndRegionIsAll(leagueStatus, date, pageable)
				: leagueReader.readLeagueStatusIsNotAllAndRegionIsNotAll(leagueStatus, region, date, pageable);
		}
	}
}
