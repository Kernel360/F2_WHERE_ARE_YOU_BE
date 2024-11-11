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
		// 모든 회원과 리그 데이터를 가져옴
		List<Member> members = memberRepository.findAll();
		List<League> leagues = leagueRepository.findByLeagueStatus(
			LeagueStatus.RECRUITING); // 모집 상태가 RECRUITING인 리그만 가져옴

		// 현재 리그 인덱스부터 처리 시작
		while (currentLeagueIndex < leagues.size()) {
			League league = leagues.get(currentLeagueIndex);

			// 모집 종료 시간이 현재 시간보다 미래인지 확인
			if (LocalDateTime.now().isAfter(league.getRecruitingClosedAt())) {
				log.info("Recruiting closed for league: {}", league.getLeagueId());
				currentLeagueIndex++;
				currentMemberIndex = 0; // 멤버 인덱스 초기화 후 다음 리그로 이동
				continue;
			}

			// 리그의 최대 인원과 현재 참가 인원을 비교하여 남은 슬롯 확인
			int playerLimit = league.getPlayerLimitCount();
			int currentParticipants = participantRepository.countByLeague(league);
			int availableSlots = playerLimit - currentParticipants;

			// 리그에 참가 가능한 슬롯이 남아있는지 확인
			if (availableSlots > 0 && currentMemberIndex < members.size()) {
				Member member = members.get(currentMemberIndex++);

				// 이미 리그에 참가한 멤버가 아닌 경우 처리
				if (!isAlreadyParticipant(member, league)) {
					ClubMember existingClubMember = clubMemberRepository.findByClubClubIdAndMemberId(
						league.getClub().getClubId(), member.getId());

					if (existingClubMember == null) {
						// 멤버가 클럽에 가입되어 있지 않으면 가입 처리 후 리턴
						ClubMember newClubMember = new ClubMember(league.getClub(), member,
							ClubMember.ClubMemberRole.ROLE_USER);
						clubMemberRepository.save(newClubMember);
						return new LeagueParticipant(newClubMember, league);
					} else {
						// 이미 클럽에 가입되어 있는 경우 리그 참가자로 리턴
						return new LeagueParticipant(existingClubMember, league);
					}
				}
			} else {
				// 현재 리그가 가득 찼거나 모든 멤버를 검사한 경우 다음 리그로 이동
				currentLeagueIndex++;
				currentMemberIndex = 0; // 멤버 인덱스 초기화
			}
		}
		return null; // 모든 리그와 멤버가 처리된 경우 null 반환
	}

	// 리그에 이미 참가한 멤버인지 확인하는 메서드
	private boolean isAlreadyParticipant(Member member, League league) {
		return participantRepository.existsByMemberIdAndLeagueLeagueId(member.getId(), league.getLeagueId());
	}
}
