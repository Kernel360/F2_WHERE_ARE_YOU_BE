package org.badminton.domain.domain.clubmember.service;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;

public interface ClubMemberPenaltyStrategy {
	ClubMemberBanRecordInfo execute(ClubMember clubMember, Object request);

}
