package org.badminton.domain.domain.clubmember.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.entity.Member;

public record ClubMemberInfo(
	Long clubMemberId,
	String image,
	String name,
	ClubMember.ClubMemberRole role,
	LeagueRecordInfo leagueRecord,
	Member.MemberTier tier,
	boolean isBanned,
	LocalDateTime BannedEndDate
) {
	public static ClubMemberInfo valueOf(ClubMember clubMember) {
		return new ClubMemberInfo(
			clubMember.getClubMemberId(),
			clubMember.getMember().getProfileImage(),
			clubMember.getMember().getName(),
			clubMember.getRole(),
			LeagueRecordInfo.from(clubMember.getMember().getLeagueRecord()),
			clubMember.getMember().getTier(),
			clubMember.isBanned(),
			getBanEndDate(clubMember)

		);
	}

	private static LocalDateTime getBanEndDate(ClubMember clubMember) {
		if (clubMember.isBanned() && !clubMember.getBanHistory().isEmpty()) {
			ClubMemberBanRecord latestBanRecord = clubMember.getBanHistory().stream()
				.filter(ClubMemberBanRecord::isActive)
				.findFirst()
				.orElse(null);

			if (latestBanRecord != null && latestBanRecord.getEndDate() != null) {
				return latestBanRecord.getEndDate();
			}
		}
		return null;
	}
}




