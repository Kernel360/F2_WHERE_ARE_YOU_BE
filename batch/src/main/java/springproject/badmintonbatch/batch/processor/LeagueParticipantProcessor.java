package springproject.badmintonbatch.batch.processor;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class LeagueParticipantProcessor implements ItemProcessor<LeagueParticipant, LeagueParticipant> {

	@Override
	public LeagueParticipant process(LeagueParticipant item) throws Exception {
		if (LocalDateTime.now().isBefore(item.getLeague().getRecruitingClosedAt())) {
			return item;
		}
		return null;
	}
}
