package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.infrastructure.league.LeagueRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LeagueItemWriter implements ItemWriter<League> {

	private final LeagueRepository leagueRepository;

	@Override
	public void write(Chunk<? extends League> chunk) throws Exception {
		//상태를 변경하고 저장한다.
		leagueRepository.saveAll(chunk);
	}
}
