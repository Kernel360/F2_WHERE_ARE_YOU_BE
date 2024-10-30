package org.badminton.domain.domain.clubmember.info;

import java.util.List;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.domain.domain.member.entity.Member;

public record ClubMemberDetailInfo(
	Long clubMemberId,

	boolean deleted,

	boolean banned,

	ClubMember.ClubMemberRole role,

	Club club,

	Member member,

	List<ClubMemberBanRecord> banHistory,

	ClubMember.ClubMemberStatus status
) {
	public static ClubMemberDetailInfo from(ClubMember clubMember) {
		return new ClubMemberDetailInfo(
			clubMember.getClubMemberId(),
			clubMember.isDeleted(),
			clubMember.isBanned(),
			clubMember.getRole(),
			clubMember.getClub(),
			clubMember.getMember(),
			clubMember.getBanHistory(),
			clubMember.getStatus()
		);
	}
}
