package org.badminton.domain.domain.clubmember.info;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;

public record ClubMemberBanRecordInfo(
	ClubMember.BannedType bannedType,
	String bannedReason,
	Long clubMemberId,
	boolean isActive,
	LocalDateTime endDate
) {
	public static ClubMemberBanRecordInfo fromClubMember(ClubMemberBanRecord clubMemberBanRecord) {
		return new ClubMemberBanRecordInfo(clubMemberBanRecord.getBannedType(),clubMemberBanRecord.getBannedReason(),clubMemberBanRecord.getClubMember().getClubMemberId(),clubMemberBanRecord.isActive(),clubMemberBanRecord.getEndDate());
	}
}
