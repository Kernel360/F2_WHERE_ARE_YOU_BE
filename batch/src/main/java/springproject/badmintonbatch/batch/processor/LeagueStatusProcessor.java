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

	@Override
	public League process(League item) {
		LocalDateTime now = LocalDateTime.now();

		switch (item.getLeagueStatus()) {
			case RECRUITING:
				handleRecruiting(item);
				break;
			case RECRUITING_COMPLETED:
				handleRecruitingCompleted(item, now);
				break;
			case PLAYING:
				handlePlaying(item, now);
				break;
		}

		return item;
	}

	private void handleRecruiting(League item) {
		if (!isParticipantCountValid(item)) {
			item.cancelLeague();
		} else {
			item.completeLeagueRecruiting();
		}
	}

	private void handleRecruitingCompleted(League item, LocalDateTime now) {
		if (isLeagueInProgress(item, now)) {
			item.startLeague();
		}
	}

	private void handlePlaying(League item, LocalDateTime now) {
		if (isLeagueOverdue(item, now)) {
			if (!areAllMatchesFinished(item)) {
				item.cancelLeague();
			} else {
				item.finishLeague();
			}
		}
	}

	private boolean isParticipantCountValid(League item) {
		return leagueParticipantReader.countParticipantMember(item.getLeagueId()) == item.getPlayerLimitCount();
	}

	private boolean isLeagueInProgress(League item, LocalDateTime now) {
		return item.getLeagueAt().isBefore(now);
	}

	private boolean isLeagueOverdue(League item, LocalDateTime now) {
		return item.getLeagueAt().plusHours(24).isBefore(now);
	}

	private boolean areAllMatchesFinished(League item) {
		return singlesMatchReader.allMatchesFinishedForLeague(item.getLeagueId())
			&& doublesMatchReader.allMatchesFinishedForLeague(item.getLeagueId());
	}
}

