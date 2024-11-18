package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.infrastructure.league.LeagueRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component("leagueStatusItemWriter")
@RequiredArgsConstructor
public class LeagueStatusWriter implements ItemWriter<League> {

	private final LeagueRepository leagueRepository;

	@Override
	public void write(Chunk<? extends League> chunk) {

		leagueRepository.saveAll(chunk);

	}
}
