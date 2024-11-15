package springproject.badmintonbatch.batch.processor;

import org.badminton.domain.domain.league.entity.League;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("leagueItemProcessor")
public class LeagueItemProcessor implements ItemProcessor<League, League> {
	@Override
	public League process(League item) throws Exception {
		// 상태를 검사하여 리턴한다.
		item.cancelLeague();
		return item;
	}
}
