package org.badminton.domain.domain.clubmember.service;

import org.badminton.domain.common.exception.clubmember.ClubMemberAlreadyBannedException;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractClubMemberBanStrategy implements ClubMemberPenaltyStrategy {

	protected final ClubMemberStore clubMemberStore;

	@Override
	public ClubMemberBanRecordInfo execute(ClubMember clubMember, Object request) {
		validateMemberNotBanned(clubMember);
		ClubMember.BannedType bannedType = getBannedType(request);
		String reason = getReason(request);
		ClubMemberBanRecord newBanRecord = createBanRecord(clubMember, bannedType, reason);
		clubMember.addBanRecord(newBanRecord);
		applyExpel(clubMember, bannedType);
		clubMemberStore.store(clubMember);
		return ClubMemberBanRecordInfo.fromClubMember(newBanRecord);
	}

	protected abstract ClubMember.BannedType getBannedType(Object request);

	protected abstract String getReason(Object request);

	protected abstract ClubMemberBanRecord createBanRecord(ClubMember clubMember, ClubMember.BannedType bannedType,
		String reason);

	protected abstract void applyExpel(ClubMember clubMember, ClubMember.BannedType bannedType);

	private void validateMemberNotBanned(ClubMember clubMember) {
		clubMember.getBanHistory()
			.stream()
			.filter(ClubMemberBanRecord::isActive)
			.findFirst()
			.ifPresent(ban -> {
				throw new ClubMemberAlreadyBannedException(clubMember.getClubMemberId());
			});
	}

}
