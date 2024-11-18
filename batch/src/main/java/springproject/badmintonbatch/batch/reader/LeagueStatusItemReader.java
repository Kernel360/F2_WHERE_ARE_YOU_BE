package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.infrastructure.league.LeagueRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component("leagueStatusItemReader")
@RequiredArgsConstructor
public class LeagueStatusItemReader implements ItemReader<League> {

	private final LeagueRepository leagueRepository;

	private List<League> leaguesToProcess;
	private int currentIndex = 0;

	@Override
	public League read() {
		if (leaguesToProcess == null) {
			LocalDateTime currentTime = LocalDateTime.now();
			leaguesToProcess = leagueRepository.findAllByRecruitingClosedAtAfter(currentTime).stream()
				.filter(league ->
					league.getLeagueStatus().equals(LeagueStatus.RECRUITING)
				).toList();
		}
		if (currentIndex >= leaguesToProcess.size()) {
			leaguesToProcess = null;
			currentIndex = 0;
			return null;
		}
		return leaguesToProcess.get(currentIndex++);
	}
}
