// package org.badminton.api.service.league;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.Month;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.badminton.api.interfaces.league.dto.LeagueByDateResponse;
// import org.badminton.api.interfaces.league.dto.LeagueCancelResponse;
// import org.badminton.api.interfaces.league.dto.LeagueCreateRequest;
// import org.badminton.api.interfaces.league.dto.LeagueCreateResponse;
// import org.badminton.api.interfaces.league.dto.LeagueDetailsResponse;
// import org.badminton.api.interfaces.league.dto.LeagueReadResponse;
// import org.badminton.api.interfaces.league.dto.LeagueUpdateRequest;
// import org.badminton.api.interfaces.league.dto.LeagueUpdateResponse;
// import org.badminton.api.interfaces.league.enums.EndDateType;
// import org.badminton.api.interfaces.league.enums.StartDateType;
// import org.badminton.domain.common.enums.MatchType;
// import org.badminton.domain.common.exception.club.ClubNotExistException;
// import org.badminton.domain.common.exception.league.InvalidDateTimeException;
// import org.badminton.domain.common.exception.league.LeagueNotExistException;
// import org.badminton.domain.domain.club.Club;
// import org.badminton.domain.domain.club.entity.ClubEntity;
// import org.badminton.domain.domain.league.entity.League;
// import org.badminton.domain.infrastructures.club.ClubRepository;
// import org.badminton.domain.infrastructures.league.LeagueParticipantRepository;
// import org.badminton.domain.infrastructures.league.LeagueRepository;
// import org.badminton.domain.infrastructures.match.repository.DoublesMatchRepository;
// import org.badminton.domain.infrastructures.match.repository.SinglesMatchRepository;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.validation.annotation.Validated;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @Validated
// @RequiredArgsConstructor
// public class LeagueService {
// 	private static final Integer YEAR_TO_FROM = 20;
//
// 	private final LeagueRepository leagueRepository;
// 	private final ClubRepository clubRepository;
// 	private final LeagueParticipantRepository leagueParticipantRepository;
// 	// TODO: 중간발표 이후 삭제 예정
// 	private final SinglesMatchRepository singlesMatchRepository;
// 	private final DoublesMatchRepository doublesMatchRepository;
//
// 	public LeagueCreateResponse createLeague(Long clubId, LeagueCreateRequest leagueCreateRequest) {
//
// 		ClubEntity club = provideClub(clubId);
//
// 		// TODO: 개선 방법
// 		League league = new League(leagueCreateRequest.leagueName(),
// 			leagueCreateRequest.description(), leagueCreateRequest.leagueLocation(), leagueCreateRequest.leagueAt(),
// 			leagueCreateRequest.tierLimit(),
// 			leagueCreateRequest.closedAt(), leagueCreateRequest.playerCount(),
// 			leagueCreateRequest.matchType(), leagueCreateRequest.matchGenerationType(), club);
//
// 		return LeagueCreateResponse.leagueCreateEntityToResponse(leagueRepository.save(league));
// 	}
//
// 	public LeagueDetailsResponse getLeague(Long clubId, Long leagueId, String memberToken) {
// 		boolean isParticipatedInLeague = !leagueParticipantRepository
// 			.findByMemberMemberTokenAndLeagueLeagueIdAndCanceledFalse(memberToken, leagueId)
// 			.isEmpty();
// 		Club club = provideClub(clubId);
// 		League league = provideLeagueIfLeagueInClub(club.getClubId(), leagueId);
// 		// TODO: 중간발표 이후 삭제 예정
// 		MatchType matchType = league.getMatchType();
// 		boolean isMatchCreated = false;
// 		if (matchType == MatchType.SINGLES) {
// 			if (!singlesMatchRepository.findAllByLeague_LeagueId(leagueId).isEmpty()) {
// 				isMatchCreated = true;
// 			}
// 		} else if (matchType == MatchType.DOUBLES) {
// 			if (!doublesMatchRepository.findAllByLeague_LeagueId(leagueId).isEmpty()) {
// 				isMatchCreated = true;
// 			}
// 		}
//
// 		int recruitedMemberCount = leagueParticipantRepository.countByLeagueLeagueIdAndCanceledFalse(leagueId);
// 		return LeagueDetailsResponse.fromLeagueEntityAndRecruitedMemberCountAndIsParticipated(league,
// 			recruitedMemberCount, isParticipatedInLeague, isMatchCreated);
// 	}
//
// 	public List<LeagueReadResponse> getLeaguesByMonth(Long clubId, String date) {
// 		if (!validateDate(date)) {
// 			throw new InvalidDateTimeException(date);
// 		}
//
// 		LocalDate parsedDate = parseDateByMonth(date);
// 		LocalDateTime startOfMonth = getStartOfMonth(parsedDate);
// 		LocalDateTime endOfMonth = getEndOfMonth(parsedDate);
//
// 		List<League> result = leagueRepository.findAllByClubClubIdAndLeagueAtBetween(clubId, startOfMonth,
// 			endOfMonth);
//
// 		return result.stream()
// 			.map(LeagueReadResponse::leagueReadEntityToResponse)
// 			.collect(
// 				Collectors.toList());
// 	}
//
// 	public List<LeagueByDateResponse> getLeaguesByDate(Long clubId, String date) {
// 		if (!validateDate(date)) {
// 			throw new InvalidDateTimeException(date);
// 		}
//
// 		LocalDate parsedDate = parseDateByDate(date);
// 		LocalDateTime startOfDay = getStartOfDay(parsedDate);
// 		LocalDateTime endOfDay = getEndOfDay(parsedDate);
//
// 		List<League> leaguesByDate = leagueRepository.findAllByClubClubIdAndLeagueAtBetween(clubId, startOfDay,
// 			endOfDay);
//
// 		return leaguesByDate.stream()
// 			.map(league -> LeagueByDateResponse.fromLeagueEntity(league,
// 				leagueParticipantRepository.findAllByLeagueLeagueIdAndCanceledFalse(league.getLeagueId())
// 					.size()))
// 			.collect(Collectors.toList());
// 	}
//
// 	public LeagueUpdateResponse updateLeague(Long clubId, Long leagueId, LeagueUpdateRequest leagueUpdateRequest
// 	) {
// 		League league = provideLeagueIfLeagueInClub(clubId, leagueId);
// 		// TODO: 개선 방법, 변경할 수 있는 필드에 제한
// 		league.updateLeague(leagueUpdateRequest.leagueName(),
// 			leagueUpdateRequest.description(), leagueUpdateRequest.leagueLocation(),
// 			leagueUpdateRequest.tierLimit(),
// 			leagueUpdateRequest.leagueAt(), leagueUpdateRequest.closedAt(),
// 			leagueUpdateRequest.playerCount(),
// 			leagueUpdateRequest.matchType(), leagueUpdateRequest.matchGenerationType());
//
// 		int recruitedMemberCount = leagueParticipantRepository.countByLeagueLeagueIdAndCanceledFalse(leagueId);
// 		leagueRepository.save(league);
// 		return LeagueUpdateResponse.fromLeagueEntityAndRecruitedMemberCountAndIsParticipated(league,
// 			recruitedMemberCount);
// 	}
//
// 	@Transactional
// 	public LeagueCancelResponse cancelLeague(Long clubId, Long leagueId) {
// 		League league = provideLeagueIfLeagueInClub(clubId, leagueId);
// 		league.cancelLeague();
// 		leagueRepository.save(league);
// 		return new LeagueCancelResponse(leagueId, league.getLeagueStatus());
// 	}
//
// 	private League provideLeagueIfLeagueInClub(Long clubId, Long leagueId) {
// 		return leagueRepository.findByClubClubIdAndLeagueId(clubId, leagueId).orElseThrow(
// 			() -> new LeagueNotExistException(clubId, leagueId));
// 	}
//
// 	private ClubEntity provideClub(Long clubId) {
// 		return clubRepository.findByClubIdAndIsClubDeletedFalse(clubId).orElseThrow(
// 			() -> new ClubNotExistException(clubId));
// 	}
//
// 	private boolean validateDate(String date) {
// 		String[] dateForm = date.split("-");
// 		int year = Integer.parseInt(dateForm[0]);
// 		int month = Integer.parseInt(dateForm[1]);
//
// 		int yearsPrevCompare = LocalDate.now().minusYears(YEAR_TO_FROM).getYear();
// 		int yearsNextCompare = LocalDate.now().plusYears(YEAR_TO_FROM).getYear();
// 		if (yearsPrevCompare > year || yearsNextCompare < year) {
// 			return false;
// 		}
// 		return month >= Month.JANUARY.getValue() && month <= Month.DECEMBER.getValue();
// 	}
//
// 	private LocalDate parseDateByMonth(String date) {
// 		String[] parts = date.split("-");
// 		int year = Integer.parseInt(parts[0]);
// 		int month = Integer.parseInt(parts[1]);
// 		return LocalDate.of(year, month, StartDateType.START_DAY.getDescription()); // 첫 번째 날로 초기화
// 	}
//
// 	private LocalDateTime getStartOfMonth(LocalDate date) {
// 		return LocalDateTime.of(date.getYear(), date.getMonthValue(), StartDateType.START_DAY.getDescription(),
// 			StartDateType.START_HOUR.getDescription(), StartDateType.START_MINUTE.getDescription());
// 	}
//
// 	private LocalDateTime getEndOfMonth(LocalDate date) {
// 		return LocalDateTime.of(date.getYear(), date.getMonthValue(),
// 			date.lengthOfMonth(), EndDateType.END_HOUR.getDescription(), EndDateType.END_MINUTE.getDescription());
// 	}
//
// 	private LocalDate parseDateByDate(String date) {
// 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
// 		return LocalDate.parse(date, formatter);
// 	}
//
// 	private LocalDateTime getStartOfDay(LocalDate date) {
// 		return date.atStartOfDay();
// 	}
//
// 	private LocalDateTime getEndOfDay(LocalDate date) {
// 		return date.atTime(23, 59, 59);
// 	}
//
// }
