package springproject.badmintonbatch.batch.processor;

import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.entity.League;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component("leagueStatusItemProcessor")
@RequiredArgsConstructor
public class LeagueStatusProcessor implements ItemProcessor<League, League> {

	private final LeagueParticipantReader leagueParticipantReader;

	@Override
	public League process(League item) {
		if (leagueParticipantReader.countParticipantMember(item.getLeagueId()) == item.getPlayerLimitCount()) {
			item.completeLeagueRecruiting();
			return item;
		}
		
		item.cancelLeague();
		return item;
	}
}
