package org.badminton.domain.domain.clubmember.service;

import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.springframework.stereotype.Component;

@Component
public class ExpelStrategy extends AbstractClubMemberBanStrategy {

	public ExpelStrategy(ClubMemberStore clubMemberStore) {
		super(clubMemberStore);
	}

	@Override
	protected ClubMember.BannedType getBannedType(Object request) {
		return ClubMember.BannedType.PERMANENT;
	}

	@Override
	protected String getReason(Object request) {
		return ((ClubMemberExpelCommand)request).expelReason();

	}

	@Override
	protected ClubMemberBanRecord createBanRecord(ClubMember clubMember, ClubMember.BannedType bannedType,
		String reason) {
		return new ClubMemberBanRecord(clubMember, ClubMember.BannedType.PERMANENT, reason);
	}

	@Override
	protected void applyExpel(ClubMember clubMember, ClubMember.BannedType bannedType) {
		clubMember.expel();
	}
}
