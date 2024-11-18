package springproject.badmintonbatch.batch.processor;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component("leagueStatusItemProcessor")
@RequiredArgsConstructor
public class LeagueStatusProcessor implements ItemProcessor<League, League> {

	private final LeagueParticipantReader leagueParticipantReader;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;

	public League process(League item) {
		LocalDateTime now = LocalDateTime.now();

		if (!isParticipantCountValid(item)) {
			item.cancelLeague();
			return item;
		}

		item.completeLeagueRecruiting();

		if (isLeagueInProgress(item, now)) {
			item.startLeague();
		}

		if (isLeagueOverdue(item, now)) {
			if (!areAllMatchesFinished(item)) {
				item.cancelLeague();
				return item;
			}
			item.finishLeague();
		}

		return item;
	}

	private boolean isParticipantCountValid(League item) {
		return leagueParticipantReader.countParticipantMember(item.getLeagueId()) == item.getPlayerLimitCount();
	}

	private boolean isLeagueInProgress(League item, LocalDateTime now) {
		return item.getLeagueAt().isBefore(now) && item.getLeagueAt().plusHours(12).isAfter(now);
	}

	private boolean isLeagueOverdue(League item, LocalDateTime now) {
		return item.getLeagueAt().plusHours(24).isBefore(now);
	}

	private boolean areAllMatchesFinished(League item) {
		return singlesMatchReader.allMatchesFinishedForLeague(item.getLeagueId())
			&& doublesMatchReader.allMatchesFinishedForLeague(item.getLeagueId());
	}
}
