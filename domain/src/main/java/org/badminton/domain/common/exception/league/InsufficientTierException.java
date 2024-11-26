package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.member.entity.Member;

public class InsufficientTierException extends BadmintonException {
	public InsufficientTierException(Member.MemberTier requiredTier, Long leagueId, Long clubId) {
		super(ErrorCode.INSUFFICIENT_TIER,
			"[기준 티어 : " + requiredTier + ", 리그 아이디 : " + leagueId + ", 회원 아이디 : " + clubId + "]");
	}

	public InsufficientTierException(Member.MemberTier requiredTier, Long leagueId, Long clubId, Exception e) {
		super(ErrorCode.INSUFFICIENT_TIER,
			"[기준 티어 : " + requiredTier + ", 리그 아이디 : " + leagueId + ", 회원 아이디 : " + clubId + "]");
	}
}
