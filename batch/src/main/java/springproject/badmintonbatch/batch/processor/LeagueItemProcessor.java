package springproject.badmintonbatch.batch.processor;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.League;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class LeagueItemProcessor implements ItemProcessor<League, League> {
	@Override
	public League process(League league) {
		if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
			league.completeLeagueRecruiting();
		}
		return league;
	}
}
