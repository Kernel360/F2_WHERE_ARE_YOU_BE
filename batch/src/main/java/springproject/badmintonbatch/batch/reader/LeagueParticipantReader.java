package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.infrastructure.clubmember.ClubMemberRepository;
import org.badminton.infrastructure.league.LeagueParticipantRepository;
import org.badminton.infrastructure.league.LeagueRepository;
import org.badminton.infrastructure.member.MemberRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueParticipantReader implements ItemReader<LeagueParticipant> {

	private final MemberRepository memberRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final LeagueRepository leagueRepository;
	private final LeagueParticipantRepository participantRepository;

	private int currentLeagueIndex = 0;
	private int currentMemberIndex = 0;

	@Override
	public LeagueParticipant read() {
		List<Member> members = memberRepository.findAll();
		List<League> leagues = leagueRepository.findByLeagueStatus(
			LeagueStatus.RECRUITING);

		while (currentLeagueIndex < leagues.size()) {
			League league = leagues.get(currentLeagueIndex);

			if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
				log.info("Recruiting closed for league: {}", league.getLeagueId());
				currentLeagueIndex++;
				currentMemberIndex = 0;
				continue;
			}

			int playerLimit = league.getPlayerLimitCount();
			int currentParticipants = participantRepository.countByLeague(league);
			int availableSlots = playerLimit - currentParticipants;

			if (availableSlots > 0 && currentMemberIndex < members.size()) {
				Member member = members.get(currentMemberIndex++);

				if (!isAlreadyParticipant(member, league)) {
					ClubMember existingClubMember = clubMemberRepository.findByClubClubIdAndMemberId(
						league.getClub().getClubId(), member.getId());

					if (existingClubMember == null) {
						ClubMember newClubMember = new ClubMember(league.getClub(), member,
							ClubMember.ClubMemberRole.ROLE_USER);
						clubMemberRepository.save(newClubMember);
						return new LeagueParticipant(newClubMember, league);
					} else {
						return new LeagueParticipant(existingClubMember, league);
					}
				}
			} else {
				currentLeagueIndex++;
				currentMemberIndex = 0;
			}
		}
		return null;
	}

	private boolean isAlreadyParticipant(Member member, League league) {
		return participantRepository.existsByMemberIdAndLeagueLeagueId(member.getId(), league.getLeagueId());
	}
}
