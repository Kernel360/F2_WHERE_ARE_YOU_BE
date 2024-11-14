package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
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
		// 해당 리그의 매치가 없거나 매치가 전부 not start 인지 확인
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

		// 현재 인덱스의 리그 반환 후 인덱스 증가
		return targetLeague.get(currentIndex++);
	}

	private void updateMatchesToFinishedIfAllNotStarted(League league) {
		List<DoublesMatch> doublesMatches = doublesMatchRepository.findAllByLeague_LeagueId(league.getLeagueId());
		List<SinglesMatch> singlesMatches = singlesMatchRepository.findAllByLeague_LeagueId(league.getLeagueId());

		// 매치가 하나도 없는 경우 메서드 종료
		if (doublesMatches.isEmpty() && singlesMatches.isEmpty()) {
			return;
		}
		doublesMatches.forEach(DoublesMatch::finishedMatch);
		singlesMatches.forEach(SinglesMatch::finishedMatch);

		// 상태가 변경된 매치들을 저장
		doublesMatchRepository.saveAll(doublesMatches);
		singlesMatchRepository.saveAll(singlesMatches);
	}
}
