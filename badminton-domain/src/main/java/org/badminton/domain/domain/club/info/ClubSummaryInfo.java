package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;

public record ClubSummaryInfo(
	Long clubId,
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	List<ClubMember> clubMembers
) {

	public static ClubSummaryInfo toClubSummaryInfo(Club club) {
		return new ClubSummaryInfo(
			club.getClubId(),
			club.getClubToken(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getClubMembers()
		);
	}

	public static Map<Member.MemberTier, Long> getMemberTierLongMap(List<ClubMember> clubMembers) {
		List<Member.MemberTier> tierListForInit = Arrays.asList(Member.MemberTier.BRONZE, Member.MemberTier.SILVER,
			Member.MemberTier.GOLD);
		Map<Member.MemberTier, Long> tierCounts = new LinkedHashMap<>();

		for (Member.MemberTier tier : tierListForInit) {
			tierCounts.put(tier, 0L);
		}

		Map<Member.MemberTier, Long> actualCounts = clubMembers.stream()
			.filter(clubMember -> clubMember.getMember().getLeagueRecord() != null)
			.filter(clubMember -> !clubMember.isDeleted())
			.collect(Collectors.groupingBy(
				clubMember -> clubMember.getMember().getTier(),
				Collectors.counting()
			));

		tierCounts.putAll(actualCounts);

		return tierCounts;
	}

	public Map<Member.MemberTier, Long> getClubMemberCountByTier() {

		return getMemberTierLongMap(clubMembers);
	}
}
