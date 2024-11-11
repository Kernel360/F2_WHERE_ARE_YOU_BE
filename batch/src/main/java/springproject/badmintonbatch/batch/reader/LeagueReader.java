package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.vo.Address;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.infrastructure.league.LeagueRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueReader implements ItemReader<League> {
	private Iterator<Club> clubIterator;
	private Random random;
	private ClubReader clubReader;
	private LeagueRepository leagueRepository;

	@Autowired
	public LeagueReader(ClubReader clubReader, LeagueRepository leagueRepository) {
		this.leagueRepository = leagueRepository;
		this.clubReader = clubReader;
		List<Club> clubs = clubReader.readAllClubs();
		this.clubIterator = clubs.iterator();
		this.random = new Random();
	}

	@Override
	public League read() {

		if (clubIterator == null || !clubIterator.hasNext()) {
			List<Club> clubs = clubReader.readAllClubs();
			this.clubIterator = clubs.iterator();
		}

		while (clubIterator.hasNext()) {
			Club club = clubIterator.next();
			if (!leagueRepository.existsByClubIdAndCreatedAtToday(club.getClubId())) {
				League newLeague = createLeagueForClub(club); // 리그 생성
				log.info("Creating a new league for club: {}", club.getClubName());
				return newLeague;
			}
		}
		return null;
	}

	private static final String[][] SAMPLE_ADDRESSES = {
		{"서울 동작구 남천로 10 남천종합운동장"},
		{"서울 강남구 테헤란로 429, 삼성타워운동장"},
		{"서울 종로구 세종대로 175, 광화문광장 종합운동장"},
		{"서울 마포구 월드컵북로 400, 상암월드컵경기장"},
		{"서울 송파구 올림픽로 424, 올림픽공원"},
		{"서울 강서구 공항대로 529, 서울식물원"},
		{"서울 서초구 반포대로 201, 서울교대"},
		{"서울 금천구 가산디지털1로 70, 가산디지털단지 공터"},
		{"서울 용산구 한남대로 98, 한남동 가족공원"},
		{"서울 노원구 동일로 1348, 노원역 철도 위"}
	};

	private League createLeagueForClub(Club club) {
		String leagueOwnerMemberToken = "ownerToken_" + club.getClubId();
		String leagueName = club.getClubName() + " League";
		String description = "Sample description for league of " + club.getClubName();
		Address address = getRandomAddress();
		LocalDateTime leagueAt = LocalDateTime.now();
		LocalDateTime recruitingClosedAt = leagueAt.plusDays(7);
		int playerLimitCount = getRandomPlayerLimit();
		MatchType matchType = getRandomType();
		Member.MemberTier memberTier = getRandomMemberTier();
		MatchGenerationType matchGenerationType = getRandomGenerationType();

		return new League(
			leagueOwnerMemberToken,
			leagueName,
			description,
			address,
			leagueAt,
			memberTier,
			recruitingClosedAt,
			playerLimitCount,
			matchType,
			matchGenerationType,
			club
		);
	}

	private int getRandomPlayerLimit() {
		int[] samplePlayerLimit = {4, 8, 16};
		int index = random.nextInt(samplePlayerLimit.length);
		return samplePlayerLimit[index];
	}

	private Address getRandomAddress() {
		int index = random.nextInt(SAMPLE_ADDRESSES.length);
		String[] selectedAddress = SAMPLE_ADDRESSES[index];
		return new Address(selectedAddress[0]);
	}

	private Member.MemberTier getRandomMemberTier() {
		Member.MemberTier[] tiers = Member.MemberTier.values();
		int index = random.nextInt(tiers.length);
		return tiers[index];
	}

	private MatchGenerationType getRandomGenerationType() {
		MatchGenerationType[] types = MatchGenerationType.values();
		int index = random.nextInt(types.length);
		return types[index];
	}

	private MatchType getRandomType() {
		MatchType[] types = MatchType.values();
		int index = random.nextInt(types.length);
		return types[index];
	}
}
