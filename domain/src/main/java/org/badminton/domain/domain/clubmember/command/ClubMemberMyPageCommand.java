package org.badminton.domain.domain.clubmember.command;

import java.util.List;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.domain.domain.clubmember.info.ClubMemberDetailInfo;
import org.badminton.domain.domain.member.entity.Member;

public record ClubMemberMyPageCommand(
	Long clubMemberId,

	boolean deleted,

	boolean banned,

	ClubMember.ClubMemberRole role,

	Club club,

	Member member,

	List<ClubMemberBanRecord> banHistory,

	ClubMember.ClubMemberStatus status
) {
	public static ClubMemberMyPageCommand toCommand(ClubMemberDetailInfo clubMember) {
		return new ClubMemberMyPageCommand(
			clubMember.clubMemberId(),
			clubMember.deleted(),
			clubMember.banned(),
			clubMember.role(),
			clubMember.club(),
			clubMember.member(),
			clubMember.banHistory(),
			clubMember.status()
		);
	}

	public ClubMember toEntity() {
		return new ClubMember(
			this.clubMemberId,
			this.deleted,
			this.banned,
			this.status,
			this.role,
			this.club,
			this.member,
			this.banHistory
		);
	}
}
