package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.league.LeagueStore;
import org.badminton.domain.domain.league.entity.League;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueItemWriter implements ItemWriter<League> {

	private final LeagueStore leagueStore;

	@Override
	public void write(Chunk<? extends League> items) {
		for (League league : items.getItems()) {
			log.info("저장");
			leagueStore.store(league);
		}
	}
}
