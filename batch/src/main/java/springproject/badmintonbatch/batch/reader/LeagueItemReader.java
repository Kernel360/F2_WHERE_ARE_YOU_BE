package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.infrastructure.league.LeagueRepository;
import org.badminton.infrastructure.match.repository.DoublesMatchRepository;
import org.badminton.infrastructure.match.repository.SinglesMatchRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LeagueItemReader implements ItemReader<League> {

	private final LeagueRepository leagueRepository;
	private final DoublesMatchRepository doublesMatchRepository;
	private final SinglesMatchRepository singlesMatchRepository;

	private int currentIndex = 0;

	@Override
	public League read() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
		List<League> targetLeague = leagueRepository.findAllByLeagueAtBefore(localDateTime).stream()
			.filter(league ->
				!league.getLeagueStatus().equals(LeagueStatus.CANCELED)
					&& !league.getLeagueStatus().equals(LeagueStatus.FINISHED)
			).toList();

		for (League league : targetLeague) {
			updateMatchesToFinishedIfAllNotStarted(league);
		}
		if (currentIndex >= targetLeague.size()) {
			currentIndex = 0;
		}

		if (targetLeague.isEmpty()) {
			return null;
		}

		return targetLeague.get(currentIndex++);
	}

	private void updateMatchesToFinishedIfAllNotStarted(League league) {
		List<DoublesMatch> doublesMatches = doublesMatchRepository.findAllByLeague_LeagueId(league.getLeagueId());
		List<SinglesMatch> singlesMatches = singlesMatchRepository.findAllByLeague_LeagueId(league.getLeagueId());

		if (doublesMatches.isEmpty() && singlesMatches.isEmpty()) {
			return;
		}

		doublesMatches.forEach(doublesMatch -> {
			doublesMatch.finishMatch();
			for (DoublesSet doublesSet : doublesMatch.getDoublesSets()) {
				doublesSet.close();
			}
		});
		singlesMatches.forEach(singlesMatch -> {
			singlesMatch.finishMatch();
			// 싱글스 매치의 모든 세트 종료
			for (SinglesSet singlesSet : singlesMatch.getSinglesSets()) {
				singlesSet.close();
			}
		});

		// 상태가 변경된 매치들을 저장
		doublesMatchRepository.saveAll(doublesMatches);
		singlesMatchRepository.saveAll(singlesMatches);
	}
}
