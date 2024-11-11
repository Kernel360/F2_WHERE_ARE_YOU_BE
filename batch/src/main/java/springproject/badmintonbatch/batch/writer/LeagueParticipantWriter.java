package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.infrastructure.league.LeagueParticipantRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LeagueParticipantWriter implements ItemWriter<LeagueParticipant> {

	private final LeagueParticipantRepository participantRepository;

	public LeagueParticipantWriter(LeagueParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	@Override
	public void write(Chunk<? extends LeagueParticipant> items) throws Exception {
		log.info("Writing {} participants", items.size());
		participantRepository.saveAll(items);
	}
}

